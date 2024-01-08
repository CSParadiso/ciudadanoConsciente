package ciudadano.consciente.resource;

import ciudadano.consciente.dto.DTOUser;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceUser;
import ciudadano.consciente.dto.DTOUpdateUser;
import ciudadano.consciente.dto.DTOCreateUser;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import java.net.URI;

@Tag(name = "User Resource")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/users")
public class ResourceUser {

    final String BASE_PATH_RESOURCE = "/users/";

    @Inject
    ServiceUser serviceUser;

    @Inject
    Logger audit;

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    @GET
    @Operation( summary = "Retrieve all users.")
    @APIResponse(
            responseCode = "200",
            description = "Users successfully retrieved."
            )
    public Response getAll() {

        audit.debug("Getting all Users...");
        return Response.ok(serviceUser.getAll()).build();

    }

    @GET
    @Path("/{id}/")
    @Operation( summary = "Retrieve a specific User by its ID.")
    @APIResponse(
            responseCode = "200",
            description = "User successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve User. Verify 'Warning' Header."
    )
    public Response get(@PathParam("id") Integer id) {

        audit.debug("Getting User " + id + "...");
        return Response.ok(serviceUser.get(id)).build();

    }

    @POST
    @Operation( summary = "Create a new User.")
    @APIResponse(
            responseCode = "201",
            description = "User successfully created."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to create User. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to create User. Verify 'Warning' Header."
    )
    public Response create(DTOCreateUser dtoCreateUser) {

        if(dtoCreateUser == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        String email = dtoCreateUser.getEmail();
        String username = dtoCreateUser.getUsername();
        String password = dtoCreateUser.getPassword();
        if(!utilityVerifyRequestField.isValidField(email) ||
                !utilityVerifyRequestField.isValidField(username) ||
                !utilityVerifyRequestField.isValidField(password)) {
            throw new HttpBadRequestException("All fields required.");
        }

        audit.debug("Creating User...");
        DTOUser user = serviceUser.create(dtoCreateUser);

        audit.debug("Creating URI...");
        URI uri = URI.create(BASE_PATH_RESOURCE + user.getUserId());

        return Response.created(uri).entity(user).build();

    }

    @PATCH
    @Path("{id}")
    @Operation( summary = "Update User.")
    @APIResponse(
            responseCode = "200",
            description = "User successfully updated."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to update User. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to update User. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to update User. Verify 'Warning' Header."
    )
    public Response update(@PathParam("id") Integer id, DTOUpdateUser dtoUpdateUser) {

        if(dtoUpdateUser == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        String email = dtoUpdateUser.getEmail();
        String username = dtoUpdateUser.getUsername();
        String password = dtoUpdateUser.getPassword();
        if(!utilityVerifyRequestField.isValidField(email) &&
                !utilityVerifyRequestField.isValidField(username) &&
                !utilityVerifyRequestField.isValidField(password)) {
            throw new HttpBadRequestException("No updates to make.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(id.compareTo(dtoUpdateUser.getUserId()) != 0 )  {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        audit.debug("Updating User... " + id + "...");
        return Response.ok(serviceUser.update(id, dtoUpdateUser)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation( summary = "Delete a User.")
    @APIResponse(
            responseCode = "200",
            description = "User successfully deleted."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to create User. Verify 'Warning' Header."
    )
    public Response delete(@PathParam("id") Integer id) {

        audit.debug("Deleting User " + id + "...");
        return Response.ok(serviceUser.delete(id)).build();

    }

}
