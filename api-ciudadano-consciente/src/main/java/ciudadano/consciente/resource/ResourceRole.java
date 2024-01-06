package ciudadano.consciente.resource;

import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceRole;
import ciudadano.consciente.dto.DTOUpdateRole;
import ciudadano.consciente.dto.DTOCreateRole;
import ciudadano.consciente.dto.DTORole;
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

@Tag(name = "Role Resource")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("roles/")
public class ResourceRole {

    final String BASE_PATH_RESOURCE = "/roles/";

    @Inject
    Logger audit;

    @Inject
    ServiceRole serviceRole;

    @GET
    @Operation( summary = "Retrieve all Roles." )
    @APIResponse(
            responseCode = "200",
            description = "Roles successfully retrieved."
    )
    public Response getAll() {

        audit.debug("Retrieving all Roles...");
        return Response.ok(serviceRole.getAll()).build();

    }

    @GET
    @Path("{id}")
    @Operation( summary = "Retrieve a specific Role by its ID." )
    @APIResponse(
            responseCode = "200",
            description = "Role successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve Role. Verify 'Warning' Header."
    )
    public Response get(@PathParam("id") Integer id) {

        audit.debug("Retrieving Role " + id + "...");
        return Response.ok(serviceRole.get(id)).build();

    }

    @POST
    @Operation( summary = "Create a new Role.")
    @APIResponse(
            responseCode = "201",
            description = "Role successfully created."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to create Role."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to create Role. Verify 'Warning' Header."
    )
    public Response create(DTOCreateRole dtoCreateRole) {

        audit.debug("Creating Role...");
        DTORole rol = serviceRole.create(dtoCreateRole);

        audit.debug("Creating URI...");
        URI uri = URI.create(BASE_PATH_RESOURCE + rol.getRoleId());

        return Response.created(uri).entity(rol).build();

    }

    @PATCH
    @Path("{id}")
    @Operation(summary = "Update a Role.")
    @APIResponse(
            responseCode = "200",
            description = "Role successfully updated."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to update Role. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to update Role. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to update Role. Verify 'Warning' Header."
    )
    public Response update(@PathParam("id") Integer id, DTOUpdateRole dtoUpdateRole) {

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(id.compareTo(dtoUpdateRole.getRoleId()) != 0 )  {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        audit.debug("Updating Role... " + id + "...");
        return Response.ok(serviceRole.update(id, dtoUpdateRole)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation( summary = "Delete a Role.")
    @APIResponse(
            responseCode = "200",
            description = "Role successfully deleted."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to delete Role. Verify 'Warning' Header."
    )
    public Response delete(@PathParam("id") Integer id) {

        audit.debug("Deleting Role " + id + "...");
        return Response.ok(serviceRole.delete(id)).build();

    }

}
