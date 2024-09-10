package keycloak.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.NoContentException;
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

import java.util.List;

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
