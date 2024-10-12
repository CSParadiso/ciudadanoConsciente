package keycloak.resource;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import keycloak.service.ServiceKeycloak25;

@RolesAllowed("Keycloak-Admin")
@RequestScoped
@Path("{realm}/roles")
public class ResourceRole {

  @Inject
  Logger audit;

  @Inject
  ServiceKeycloak25 serviceKeycloak25;

  @POST
  @APIResponse(responseCode = "201", description = "Succed to create Role.")
  @Operation(summary = "Create a Role.")
  public boolean createRole(@PathParam("realm") String realm,
      @QueryParam("role") String role,
      @QueryParam("description") String description) {

    if (role.isBlank() || description.isBlank()) {
      audit.error("ROLE NAME AND DESCRIPTION ARE REQUIRED.");
      return false;
    }

    audit.debug("Creating Role.");
    return serviceKeycloak25.createRole(realm, role, description);

  }

  @POST
  @Path("assign")
  @APIResponse(responseCode = "201", description = "Success to assign Role.")
  @Operation(summary = "Assign a Role.")
  public boolean assignRole(@PathParam("realm") String realm,
                            @QueryParam("role") String role,
                            @QueryParam("user") String user) {

    if (role.isBlank() || user.isBlank()) {
      audit.error("ROLE NAME AND USER ARE REQUIRED.");
      return false;
    }

    audit.debug("Assigning Role.");
    return serviceKeycloak25.assignRole(realm, role, user);

  }

  @DELETE
  @Path("remove")
  @APIResponse(responseCode = "201", description = "Success to remove Role from User.")
  @Operation(summary = "Remove a Role from User.")
  public boolean removeRole(@PathParam("realm") String realm,
                            @QueryParam("role") String role,
                            @QueryParam("user") String user) {

    if (role.isBlank() || user.isBlank()) {
      audit.error("ROLE NAME AND USER ARE REQUIRED.");
      return false;
    }

    audit.debug("Remove Role from User.");
    return serviceKeycloak25.removeRole(realm, role, user);

  }

  @PUT
  @Path("{role}")
  @APIResponse(responseCode = "200", description = "Succed to update Role.")
  @Operation(summary = "Update a Role.")
  public boolean updateRole(@PathParam("realm") String realm,
      @PathParam("role") String role,
      @QueryParam("description") String description) {

    if (description.isBlank()) {
      audit.error("DESCRIPTION REQUIRED.");
      return false;
    }

    audit.debug("Updating Role.");
    return serviceKeycloak25.updateRole(realm, role, description);

  }

  @DELETE
  @Path("{role}")
  @APIResponse(responseCode = "200", description = "Succed to delete Role.")
  @Operation(summary = "Delete a Role.")
  public boolean deleteRole(@PathParam("realm") String realm,
      @PathParam("role") String role) {

    audit.debug("Deleting Role.");
    return serviceKeycloak25.deleteRole(realm, role);

  }

}
