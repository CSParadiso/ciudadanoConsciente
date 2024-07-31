package ciudadano.consciente.resource;

import ciudadano.consciente.dto.DTOUser;
import ciudadano.consciente.dto.DTOUserRoleLevel;
import ciudadano.consciente.service.ServiceUserRoleLevel;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;

@Authenticated
@Deprecated(since = "1.0.0")
@RequestScoped
@Path("user-role-level")
@Tag(name = "User Role Level Resource")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResourceUserRoleLevel {

  @Inject
  Logger audit;

  @Inject
  ServiceUserRoleLevel serviceUserRoleLevel;

  @Deprecated
  @GET
  @Operation(summary = "Retrieve all UserRoleLevel.")
  @APIResponse(responseCode = "200", description = "All UserRoleLevel successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOUserRoleLevel.class)))
  public RestResponse<List<DTOUserRoleLevel>> getAll() {

    audit.debug("Getting all UserRoleLevel...");
    return RestResponse.ResponseBuilder.ok(serviceUserRoleLevel.getAll()).build();

  }

  @Deprecated
  @GET
  @Path("/{id}/")
  @Operation(summary = "Retrieve a specific UserRoleLevel by its ID.")
  @APIResponse(responseCode = "200", description = "UserRoleLevel successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOUserRoleLevel.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve UserRoleLevel. Verify 'Warning' Header.")
  public RestResponse<DTOUserRoleLevel> get(@PathParam("id") Integer id) {

    audit.debug("Getting User " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceUserRoleLevel.get(id)).build();

  }

}
