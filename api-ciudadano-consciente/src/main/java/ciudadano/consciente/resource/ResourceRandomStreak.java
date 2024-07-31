package ciudadano.consciente.resource;

import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceStreak;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;

import java.net.URI;
import java.util.List;

@Authenticated
@RequestScoped
@Tag(name = "Streak Resource")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("streak")
public class ResourceRandomStreak {

    final String BASE_PATH_RESOURCE = "/streak/";

    @Inject
    Logger audit;

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    @Inject
    ServiceStreak serviceStreak;

    @GET
    @Path("random")
    @Operation(summary = "Retrieve all Random Streaks.")
    @APIResponse(
            responseCode = "200",
            description = "Random Streaks successfully retrieved.",
            content = @Content(schema = @Schema(implementation = DTORandomStreak.class))
    )
    @APIResponse(responseCode = "204", description = "Failed to retrieve all Random Streaks. Verify 'Warning' header.")
    public RestResponse<List<DTORandomStreak>> getAll() {

        audit.debug("Getting all Random Streaks.");
        return RestResponse.ResponseBuilder.ok(serviceStreak.getAllRandom()).build();

    }

    @GET
    @Path("random/{id}")
    @Operation(summary = "Retrieve Random Streak.")
    @APIResponse(
            responseCode = "200",
            description = "Random Streak successfully retrieved.",
            content = @Content(schema = @Schema(implementation = DTORandomStreak.class))
    )
    @APIResponse(responseCode = "204", description = "Failed to retrieve Random Streak. Verify 'Warning' header.")
    public RestResponse<DTORandomStreak> get(@PathParam("id") Integer id) {

        audit.debug("Getting Random Streak " + id);
        return RestResponse.ResponseBuilder.ok(serviceStreak.getRandomStreak(id)).build();

    }

    @GET
    @Path("random/users/{userId}")
    @Operation(summary = "Retrieve Random Streak of User.")
    @APIResponse(
            responseCode = "200",
            description = "Random Streak successfully retrieved.",
            content = @Content(schema = @Schema(implementation = DTORandomStreak.class))
    )
    @APIResponse(responseCode = "204", description = "Failed to retrieve Random Streak of User. Verify 'Warning' header.")
    public RestResponse<DTORandomStreak> getByUser(@PathParam("userId") Integer userId) {

        audit.debug("Getting Random Streak of user " + userId);
        return RestResponse.ResponseBuilder.ok(serviceStreak.getRandomStreakByUser(userId)).build();

    }

    @POST
    @Path("random/users/{userId}")
    @Operation(summary = "Create a Random Streak for a User.")
    @APIResponse(
            responseCode = "201",
            description = "Random Streak successfully created.",
            content = @Content (schema = @Schema (implementation = DTORandomStreak.class))
    )
    @APIResponse(responseCode = "204", description = "Failed to create Random Streak. Verify 'Warning' Header.")
    @APIResponse(responseCode = "500", description = "Failed to create Random Streak. Verify 'Warning' Header.")
    public RestResponse<DTORandomStreak> create(DTOCreateRandomStreak dtoCreateRandomStreak) {

        if (dtoCreateRandomStreak == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        Integer actualStreak = dtoCreateRandomStreak.getActualStreak();
        Integer user = dtoCreateRandomStreak.getUserId();
        if (!utilityVerifyRequestField.isValidField(actualStreak) &&
                !utilityVerifyRequestField.isValidField(user)) {
            throw new HttpBadRequestException("All fields required.");
        }

        audit.debug("Creating Random Streak for User...");
        DTORandomStreak dtoRandomStreak = serviceStreak.createRandomStreak(dtoCreateRandomStreak);

        audit.debug("Creating URI...");
        URI uri = URI.create(BASE_PATH_RESOURCE + dtoRandomStreak.getStreakId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.CREATED, dtoRandomStreak)
                .location(uri)
                .build();

    }

    @PATCH
    @Path("random/users/{userId}")
    @Operation(summary = "Update a Random Streak of User.")
    @APIResponse(responseCode = "200", description = "Random Streak successfully updated.", content = @Content(schema = @Schema(implementation = DTORandomStreak.class)))
    @APIResponse(responseCode = "400", description = "Failed to update Random Streak. Verify 'Warning' Header.")
    @APIResponse(responseCode = "204", description = "Failed to update Random Streak. Verify 'Warning' Header.")
    public RestResponse<DTORandomStreak> update(@PathParam("userId") Integer userId,
                                       DTOUpdateRandomStreak dtoUpdateRandomStreak) {

        if (dtoUpdateRandomStreak == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if (userId.compareTo(dtoUpdateRandomStreak.getUserId()) != 0) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        int actualStrake = dtoUpdateRandomStreak.getActualStreak();
        int user = dtoUpdateRandomStreak.getUserId();
        if (!utilityVerifyRequestField.isValidField(actualStrake) &&
                !utilityVerifyRequestField.isValidField(user)) {
            throw new HttpBadRequestException("No updates to make.");
        }

        audit.debug("Updating Random Streak for User " + userId + "...");
        return RestResponse.ResponseBuilder.ok(serviceStreak.updateRandomStreak(dtoUpdateRandomStreak)).build();

    }

}
