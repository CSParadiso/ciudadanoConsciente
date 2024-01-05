package ciudadano.consciente.resource;

import ciudadano.consciente.dto.DTOUpdateRoleUserLevel;
import ciudadano.consciente.dto.DTOUserRoleLevel;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceUserRoleLevel;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

@RequestScoped
@Path("user-role-level")
@Tag(name = "User Role Level Resource")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResourceUserRoleLevel {

    final String BASE_PATH_RESOURCE = "/activities/";

    @Inject
    Logger audit;

    @Inject
    ServiceUserRoleLevel serviceUserRoleLevel;

    @Deprecated
    @GET
    @Operation(summary = "Retrieve all UserRoleLevel.")
    @APIResponse(
            responseCode = "200",
            description = "All UserRoleLevel successfully retrieved."
    )
    public Response getAll() {

        audit.debug("Getting all UserRoleLevel...");
        return Response.ok(serviceUserRoleLevel.getAll()).build();

    }

    @Deprecated
    @GET
    @Path("/{id}/")
    @Operation( summary = "Retrieve a specific UserRoleLevel by its ID.")
    @APIResponse(
            responseCode = "200",
            description = "UserRoleLevel successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve UserRoleLevel. Verify 'Warning' Header."
    )
    public Response get(@PathParam("id") Integer id) {

        audit.debug("Getting User " + id + "...");
        return Response.ok(serviceUserRoleLevel.get(id)).build();

    }

    @Deprecated
    @PATCH
    @Path("{id}/")
    @Operation(summary = "Update RoleUserLevel.")
    @APIResponse(
            responseCode = "200",
            description = "Role successfully updated to User in Level."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to update Role to User in Level. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to update Role to User in Level. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to update Role to User in Level. Verify 'Warning' Header."
    )
    public Response updateRole(@PathParam("id") Integer id,
                               DTOUpdateRoleUserLevel dtoUpdateRoleUserLevel) {

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(id != dtoUpdateRoleUserLevel.getUrlId()) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        audit.debug("Update Role" + dtoUpdateRoleUserLevel.getRole()
                + " to User " + dtoUpdateRoleUserLevel.getUser()
                + " in Level " + dtoUpdateRoleUserLevel.getLevel() + "...");
        DTOUserRoleLevel dtoUserRoleLevel = serviceUserRoleLevel.updateRol(id, dtoUpdateRoleUserLevel);

        return Response.ok(dtoUserRoleLevel).build();

    }

}
