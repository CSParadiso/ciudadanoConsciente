package ciudadano.consciente.client.keycloak.interfaces;

import io.quarkus.oidc.client.filter.OidcClientFilter;
import jakarta.resource.spi.ConfigProperty;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

//@IfBuildProfile("dev")
//@RegisterRestClient(configKey = "api-keycloak")
@RegisterRestClient(configKey="desa-api-keycloak")
@OidcClientFilter
public interface APIKeycloak {

  @GET
  @Path("/users/{idUser}/groups")
  @Produces(MediaType.APPLICATION_JSON)
  Response getGroups(@PathParam("idUser") String authServerId);

  // ROLE SYNCHRONIZATION WITH KEYCLOAK (name, description)
  @POST
  @Path("{realm}/roles")
  boolean createRole(@PathParam("realm") String realm,
      @QueryParam("role") String role,
      @QueryParam("description") String description);

  // ROLE SYNCHRONIZATION WITH KEYCLOAK (name, description)
  @POST
  @Path("{realm}/roles/organization/assign")
  boolean assignRoleInOrganization(@PathParam("realm") String realm,
                     @QueryParam("role") String role,
                     @QueryParam("user") String user,
                     @QueryParam("organization") Integer organizationId);

  @POST
  @Path("{realm}/roles/level/assign")
  boolean assignRoleInLevel(@PathParam("realm") String realm,
                                   @QueryParam("role") String role,
                                   @QueryParam("user") String user,
                                   @QueryParam("level") Integer levelId);

  @DELETE
  @Path("{realm}/roles/organization/remove")
  boolean removeRoleFromOrganization(@PathParam("realm") String realm,
                     @QueryParam("role") String role,
                     @QueryParam("user") String user,
                     @QueryParam("organization") Integer organizationId);

  @DELETE
  @Path("{realm}/roles/level/remove")
  boolean removeRoleFromLevel(@PathParam("realm") String realm,
                                     @QueryParam("role") String role,
                                     @QueryParam("user") String user,
                                     @QueryParam("level") Integer levelId);

  @PUT
  @Path("{realm}/roles/{role}")
  boolean updateRole(@PathParam("realm") String realm,
      @PathParam("role") String role,
      @QueryParam("description") String description);

  @DELETE
  @Path("{realm}/roles/{role}")
  boolean deleteRole(@PathParam("realm") String realm,
      @PathParam("role") String role);

  // USER SYNCHRONIZATION WITH KEYCLOAK (id)
  @DELETE
  @Path("{realm}/users/{authServerId}")
  boolean deleteUser(@PathParam("realm") String realm,
      @PathParam("authServerId") String authServerId);

}