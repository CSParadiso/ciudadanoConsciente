package keycloak.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import keycloak.service.ServiceKeycloak25;

import java.util.List;

@RequestScoped
@RolesAllowed("Keycloak-Admin")
@Path("{realm}/users")
public class ResourceUser {

  @Inject
  Logger audit;

  @Inject
  ServiceKeycloak25 serviceKeycloak25;

  @DELETE
  @Path("{id}")
  @APIResponse(responseCode = "204", description = "Failed to delete User. User not found.")
  @APIResponse(responseCode = "200", description = "Succed to delete User. User not found.")
  @Operation(summary = "Delete a User.")
  public boolean deleteUser(@PathParam("realm") String realm, @PathParam("id") String id) {

    audit.debug("Deleting a User.");
    return serviceKeycloak25.deleteUser(realm, id);

  }

  @Deprecated
  @GET
  @Operation(summary = "Retrieve all Users of Realm.")
  public List<UserRepresentation> getUsersOfRealm(@PathParam("realm") String realm) {

    audit.debug("Retrieving Users of Realm.");
    return serviceKeycloak25.getUsers(realm);

  }

  @Deprecated
  @GET
  @Path("{id}/groups")
  @Operation(summary = "Retrieve all Groups of User.")
  public List<GroupRepresentation> getGroupsOfUser(@PathParam("realm") String realm,
      @PathParam("id") String id) {

    audit.debug("Retrieving Groups of User.");
    return serviceKeycloak25.getUsersGroups(realm, id);

  }

  @Deprecated
  @GET
  @Path("{id}/roles")
  @Operation(summary = "Retrieve all Roles of User.")
  public List<RoleRepresentation> getRolesOfUser(@PathParam("realm") String realm,
      @PathParam("id") String id) {

    audit.debug("Retrieving Roles of User.");
    return serviceKeycloak25.getUserRoles(realm, id);

  }

}
