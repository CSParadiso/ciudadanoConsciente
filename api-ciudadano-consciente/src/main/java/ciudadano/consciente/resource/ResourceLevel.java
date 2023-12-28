package ciudadano.consciente.resource;

import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceLevel;
import ciudadano.consciente.dto.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import java.net.URI;

@Tag(name = "Level Resource")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("levels/")
public class ResourceLevel {

    final String PATH_BASE_RESOURCE = "/levels/";

    @Inject
    ServiceLevel serviceLevel;

    @Inject
    Logger audit;

    @GET
    @Operation(summary = "Retrieve all Levels.")
    @APIResponse(
            responseCode = "200",
            description = "Levels successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve all Levels. Verify 'Warning' Header."
    )
    public Response getAll() {

        audit.debug("Getting all Levels...");
        return Response.ok(serviceLevel.getAll()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Retrieve a specific Level by its ID.")
    @APIResponse(
            responseCode = "200",
            description = "Level successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve Level. Verify 'Warning' Header."
    )
    public Response get(@PathParam("id") Integer id) {

        audit.debug("Getting Level " + id + "...");
        return Response.ok(serviceLevel.get(id)).build();

    }

    @POST
    @Operation(summary = "Create a Level.")
    @APIResponse(
            responseCode = "201",
            description = "Level successfully created."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to create Level. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to create Level. Verify 'Warning' Header."
    )
    public Response create(DTOCreateLevel dtoCreateLevel) {

        audit.debug("Creating Level...");
        DTOLevel level = serviceLevel.create(dtoCreateLevel);

        audit.debug("Creating URI...");
        URI uri = URI.create(PATH_BASE_RESOURCE + level.getLevelId());

        return Response.created(uri)
                .entity(level)
                .build();

    }

    @PATCH
    @Path("{id}")
    @Operation(summary = "Update a Level.")
    @APIResponse(
            responseCode = "200",
            description = "Level successfully updated."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to update Level. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to update Level. Verify 'Warning' Header."
    )
    public Response update(@PathParam("id") Integer id,
                               DTOUpdateLevel dtoUpdateLevel) {

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(id != dtoUpdateLevel.getLevelId()) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        audit.debug("Updating Level" + id + "...");
        return Response.ok(serviceLevel.update(id, dtoUpdateLevel)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Delete a specific Level by its ID.")
    @APIResponse(
            responseCode = "200",
            description = "Level successfully deleted."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to delete Level. Verify 'Warning' Header."
    )
    public Response delete(@PathParam("id") Integer id) {

        audit.debug("Deleting Level " + id + "...");
        serviceLevel.delete(id);

        return Response.ok().build();

    }

    @POST
    @Path("{id}/roles")
    @Operation(summary = "Assign Role to User in Level.")
    @APIResponse(
            responseCode = "201",
            description = "Role successfully assign to User in Level."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to Assign Role to User in Level. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to Assign Role to User in Level. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to Assign Role to User in Level. Verify 'Warning' Header."
    )
    public Response assignRole(@PathParam("id") Integer id,
                               DTOAssignRoleToUserLevel dtoAssignRoleToUserLevel) {

        final String PATH_BASE_USER_ROL_LEVEL = "/user-rol-level/";

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(id != dtoAssignRoleToUserLevel.getLevel()) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        audit.debug("Assigning Role" + dtoAssignRoleToUserLevel.getRole()
                + " to User " + dtoAssignRoleToUserLevel.getUser()
                + " in Level " + dtoAssignRoleToUserLevel.getLevel() + "...");
        DTOUserRoleLevel dtoUserRoleLevel = serviceLevel.assignRole(dtoAssignRoleToUserLevel);

        audit.debug("Creating URI...");
        URI uri = URI.create(PATH_BASE_USER_ROL_LEVEL + dtoUserRoleLevel.getUrlId());

        return Response.created(uri).entity(dtoUserRoleLevel).build();

    }

}
