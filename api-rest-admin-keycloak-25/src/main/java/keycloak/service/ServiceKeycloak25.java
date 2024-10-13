package keycloak.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.NoContentException;
import jakarta.ws.rs.core.Response;
import keycloak.exception.HttpNoContentException;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.*;

@RequestScoped
public class ServiceKeycloak25 {

  @ConfigProperty(name = "quarkus.keycloak.admin-client.server-url")
  String serverUrl;

  @ConfigProperty(name = "quarkus.keycloak.admin-client.client-id")
  String clientId;

  @ConfigProperty(name = "quarkus.keycloak.admin-client.client-secret")
  String clientSecret;

  @Inject
  Logger audit;

  @Inject
  Keycloak keycloak;

  public List<UserRepresentation> getUsers(String realm) {

    return keycloak.realm(realm).users().list();

  }

  public List<GroupRepresentation> getUsersGroups(String realm, String id) {

    return keycloak.realm(realm).users().get(id).groups();

  }

  public List<RoleRepresentation> getUserRoles(String realm, String id) {

    return keycloak.realm(realm).users().get(id).roles().realmLevel().listEffective();

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public boolean deleteUser(String realm, String id) {

    try {
      keycloak.realm(realm).users().get(id).remove();
    } catch (ClientWebApplicationException e) {
      audit.error("EXPECTED CAUSE: REALM OR USER NOT FOUND.\n" +
          "Response Status Family: " + e.getResponse().getStatusInfo().getFamily() + "\n" +
          "Response Status Info: " + e.getResponse().getStatusInfo().getReasonPhrase() + "\n" +
          "Response Status Code: " + e.getResponse().getStatus() + "\n" +
          "Exception cause: " + e.getCause().getMessage() + "\n" +
          "Localized Message: " + e.getLocalizedMessage() + "\n");
      return false;
    } catch (WebApplicationException ex) {
      audit.error("EXCLUDED CAUSE: REALM OR USER NOT FOUND.\n" +
          "Response Status Family: " + ex.getResponse().getStatusInfo().getFamily() + "\n" +
          "Response Status Info: " + ex.getResponse().getStatusInfo().getReasonPhrase() + "\n" +
          "Response Status Code: " + ex.getResponse().getStatus() + "\n" +
          "Exception cause: " + ex.getCause().getMessage() + "\n" +
          "Localized Message: " + ex.getLocalizedMessage() + "\n");
      return false;
    }

    return true;

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public boolean createRole(String realm, String role, String description) {

    RoleRepresentation roleRepresentation = new RoleRepresentation(role, description, false);
    try {
      keycloak.realm(realm).roles().create(roleRepresentation);
      audit.debug("Role succesfully created.");
    } catch (Exception e) {
      audit.error("WHO KNOWS WHY");
      return false;
    }

    return true;

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public boolean assignRoleInOrganization(String realm, String role, String user, Integer organization) {
    try {
      RoleRepresentation roleRepresentation = keycloak.realm(realm).roles().get(role).toRepresentation();

      // Retrieve the user representation
      UserRepresentation userRepresentation = keycloak.realm(realm).users().get(user).toRepresentation();

      // Check if the role is already assigned
      List<RoleRepresentation> currentRoles = keycloak.realm(realm).users().get(user).roles().realmLevel().listAll();
      boolean roleExists = currentRoles.stream()
              .anyMatch(r -> r.getName().equals(roleRepresentation.getName()));

      if (!roleExists) {
        // Assign the role to the user
        keycloak.realm(realm).users().get(user).roles().realmLevel().add(Arrays.asList(roleRepresentation));
        audit.debugv("Role {0} assigned to user {1}.", roleRepresentation.getName(), user);
      } else {
        audit.debugv("Role {0} already exists for user {1}.", roleRepresentation.getName(), user);
      }

      // Determine the attribute type
      String type = roleRepresentation.getName().equals("O-Moderator") ? "moderatorAtOrganization" : "divulgatorAtOrganization";

      // Fetch the current attributes
      Map<String, List<String>> attributes = userRepresentation.getAttributes();
      if (attributes == null) {
        attributes = new HashMap<>();
      }

      // Initialize attributes list if it doesn't exist
        List<String> organizationList = attributes.computeIfAbsent(type, k -> new ArrayList<>());

        // Add the organization if it doesn't already exist
      String organizationString = String.valueOf(organization);
      if (!organizationList.contains(organizationString)) {
        organizationList.add(organizationString);
        audit.debugv("Organization {0} added to attributes for user {1}.", organizationString, user);
      } else {
        audit.debugv("Organization {0} already exists in attributes for user {1}.", organizationString, user);
      }

      // Update the user representation with the new attributes
      userRepresentation.setAttributes(attributes);
      keycloak.realm(realm).users().get(user).update(userRepresentation);

      audit.debug("Role successfully assigned and attributes updated.");
    } catch (Exception e) {
      audit.errorv("Error occurred while assigning role: {0}", e.getMessage());
      return false;
    }

    return true;
  }


  @Transactional(Transactional.TxType.REQUIRED)
  public boolean removeRoleFromOrganization(String realm, String role, String user, Integer organization) {
    audit.debugv("Role {0} - User {1} - Organization {2}", role, user, organization);
    RoleRepresentation roleRepresentation = keycloak.realm(realm).roles().get(role).toRepresentation();

    // Check if the role is already assigned
    List<RoleRepresentation> currentRoles = keycloak.realm(realm).users().get(user).roles().realmLevel().listAll();
    boolean roleExists = currentRoles.stream()
            .anyMatch(r -> r.getName().equals(roleRepresentation.getName()));

    if (roleExists) {
      // Determine the attribute type
      String type = roleRepresentation.getName().equals("O-Moderator") ? "moderatorAtOrganization" : "divulgatorAtOrganization";

      // Fetch the current attributes
      UserRepresentation userRepresentation = keycloak.realm(realm).users().get(user).toRepresentation();
      List<String> attributes = userRepresentation.getAttributes().get(type);

      if (attributes != null) {
        // Remove the organization from attributes
        attributes.remove(String.valueOf(organization));
        audit.debugv("Updated attributes after removal attempt: {0}", attributes);

        // Check if attributes are empty
        if (attributes.isEmpty()) {
          try {
            // Remove the role from the user
            keycloak.realm(realm).users().get(user).roles().realmLevel().remove(Arrays.asList(roleRepresentation));
            audit.debug("Role successfully removed as no organizations remain.");
          } catch (Exception e) {
            audit.errorv("Error while removing role: {0}", e.getMessage());
            return false;
          }
        } else {
          audit.debug("Role retained as organizations remain in attributes.");
        }

        // Update the user's attributes in Keycloak
        userRepresentation.getAttributes().put(type, attributes);
        try {
          keycloak.realm(realm).users().get(user).update(userRepresentation);
          audit.debug("User attributes successfully updated.");
        } catch (Exception e) {
          audit.errorv("Error while updating user attributes: {0}", e.getMessage());
          return false;
        }
      } else {
        audit.debugv("User doesn't have any organizations in attributes, role will be removed.");
        try {
          keycloak.realm(realm).users().get(user).roles().realmLevel().remove(Arrays.asList(roleRepresentation));
          audit.debug("Role successfully removed due to absence of organizations.");
        } catch (Exception e) {
          audit.errorv("Error while removing role: {0}", e.getMessage());
          return false;
        }
      }
    } else {
      audit.debugv("Role {0} not assigned to user {1}, no action taken.", roleRepresentation.getName(), user);
    }

    return true;
  }

  @Transactional(Transactional.TxType.REQUIRED)
  public boolean assignRoleInLevel(String realm, String role, String user, Integer level) {
    try {
      RoleRepresentation roleRepresentation = keycloak.realm(realm).roles().get(role).toRepresentation();

      // Retrieve the user representation
      UserRepresentation userRepresentation = keycloak.realm(realm).users().get(user).toRepresentation();

      // Check if the role is already assigned
      List<RoleRepresentation> currentRoles = keycloak.realm(realm).users().get(user).roles().realmLevel().listAll();
      boolean roleExists = currentRoles.stream()
              .anyMatch(r -> r.getName().equals(roleRepresentation.getName()));

      if (!roleExists) {
        // Assign the role to the user
        keycloak.realm(realm).users().get(user).roles().realmLevel().add(Arrays.asList(roleRepresentation));
        audit.debugv("Role {0} assigned to user {1}.", roleRepresentation.getName(), user);
      } else {
        audit.debugv("Role {0} already exists for user {1}.", roleRepresentation.getName(), user);
      }

      // Determine the attribute type
      String type = roleRepresentation.getName().equals("L-Moderator") ? "moderatorAtLevel" : "divulgatorAtLevel";

      // Fetch the current attributes
      Map<String, List<String>> attributes = userRepresentation.getAttributes();
      if (attributes == null) {
        attributes = new HashMap<>();
      }

      // Initialize attributes list if it doesn't exist
      List<String> levelList = attributes.computeIfAbsent(type, k -> new ArrayList<>());

      // Add the level if it doesn't already exist
      String levelString = String.valueOf(level);
      if (!levelList.contains(levelString)) {
        levelList.add(levelString);
        audit.debugv("Level {0} added to attributes for user {1}.", levelString, user);
      } else {
        audit.debugv("Level {0} already exists in attributes for user {1}.", levelString, user);
      }

      // Update the user representation with the new attributes
      userRepresentation.setAttributes(attributes);
      keycloak.realm(realm).users().get(user).update(userRepresentation);

      audit.debug("Role successfully assigned and attributes updated.");
    } catch (Exception e) {
      audit.errorv("Error occurred while assigning role: {0}", e.getMessage());
      return false;
    }

    return true;
  }


  @Transactional(Transactional.TxType.REQUIRED)
  public boolean removeRoleFromLevel(String realm, String role, String user, Integer level) {
    audit.debugv("Role {0} - User {1} - Level {2}", role, user, level);
    RoleRepresentation roleRepresentation = keycloak.realm(realm).roles().get(role).toRepresentation();

    // Check if the role is already assigned
    List<RoleRepresentation> currentRoles = keycloak.realm(realm).users().get(user).roles().realmLevel().listAll();
    boolean roleExists = currentRoles.stream()
            .anyMatch(r -> r.getName().equals(roleRepresentation.getName()));

    if (roleExists) {
      // Determine the attribute type
      String type = roleRepresentation.getName().equals("L-Moderator") ? "moderatorAtLevel" : "divulgatorAtLevel";

      // Fetch the current attributes
      UserRepresentation userRepresentation = keycloak.realm(realm).users().get(user).toRepresentation();
      List<String> attributes = userRepresentation.getAttributes().get(type);

      if (attributes != null) {
        // Remove the level from attributes
        attributes.remove(String.valueOf(level));
        audit.debugv("Updated attributes after removal attempt: {0}", attributes);

        // Check if attributes are empty
        if (attributes.isEmpty()) {
          try {
            // Remove the role from the user
            keycloak.realm(realm).users().get(user).roles().realmLevel().remove(Arrays.asList(roleRepresentation));
            audit.debug("Role successfully removed as no levels remain.");
          } catch (Exception e) {
            audit.errorv("Error while removing role: {0}", e.getMessage());
            return false;
          }
        } else {
          audit.debug("Role retained as levels remain in attributes.");
        }

        // Update the user's attributes in Keycloak
        userRepresentation.getAttributes().put(type, attributes);
        try {
          keycloak.realm(realm).users().get(user).update(userRepresentation);
          audit.debug("User attributes successfully updated.");
        } catch (Exception e) {
          audit.errorv("Error while updating user attributes: {0}", e.getMessage());
          return false;
        }
      } else {
        audit.debugv("User doesn't have any levels in attributes, role will be removed.");
        try {
          keycloak.realm(realm).users().get(user).roles().realmLevel().remove(Arrays.asList(roleRepresentation));
          audit.debug("Role successfully removed due to absence of levels.");
        } catch (Exception e) {
          audit.errorv("Error while removing role: {0}", e.getMessage());
          return false;
        }
      }
    } else {
      audit.debugv("Role {0} not assigned to user {1}, no action taken.", roleRepresentation.getName(), user);
    }

    return true;
  }

  @Transactional(Transactional.TxType.REQUIRED)
  public boolean updateRole(String realm, String role, String description) {

    try {
      keycloak.realm(realm).roles().get(role).update(new RoleRepresentation(role, description, false));
      audit.debug("Role succesfully updated.");
      return true;
    } catch (Exception e) {
      audit.error("Failed to update Role: " + e.getCause().getMessage());
      return false;
    }

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public boolean deleteRole(String realm, String role) {

    try {
      keycloak.realm(realm).roles().deleteRole(role);
      audit.debug("Role succesfully deleted.");
      return true;
    } catch (Exception e) {
      audit.error("Failed to delete Role: " + e.getCause().getMessage());
      return false;
    }

  }

}
