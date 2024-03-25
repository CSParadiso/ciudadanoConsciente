package ciudadano.consciente.resource;

import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceActivity;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.mapstruct.Mapper;

import java.net.URI;

@RequestScoped
@Tag(name = "Activity Resource")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("activities")
public class ResourceActivity {

    final String BASE_PATH_RESOURCE = "/activities/";

    @Inject
    Logger audit;

    @Inject
    ServiceActivity serviceActivity;

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    @GET
    @Operation(summary = "Retrieve all Activities.")
    @APIResponse(
            responseCode = "200",
            description = "All Activities successfully retrieved."
    )
    public Response getAll() {

        audit.debug("Getting all Activities...");
        return Response.ok(serviceActivity.getAll()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Retrieve a specific Activity by its ID.")
    @APIResponse(
            responseCode = "200",
            description = "Activities successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve Activity. Verify 'Warning' Header."
    )
    public Response get(@PathParam("id") Integer id) {

        audit.debug("Getting Activity " + id + "...");
        return Response.ok(serviceActivity.get(id)).build();

    }

    @GET
    @Path("{id}/template")
    @Produces(MediaType.TEXT_PLAIN)
    @Operation(summary = "Retrieve the template of a specific Activity.")
    @APIResponse(
            responseCode = "200",
            description = "Template successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve template. Verify 'Warning' Header."
    )
    public Response getTemplate(@PathParam("id") Integer id) {

        audit.debug("Getting Activity " + id + "...");
        return Response.ok(serviceActivity.getTemplate(id)).build();

    }

    @GET
    @Path("level/{levelId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Retrieve the Activity of a Level.")
    @APIResponse(
            responseCode = "200",
            description = "Activity successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve Activity. Verify 'Warning' Header."
    )
    public Response getByLevel(@PathParam("levelId") Integer levelId) {

        audit.debug("Getting Activity by Level " + levelId + "...");
        return Response.ok(serviceActivity.getByLevel(levelId)).build();

    }

    @POST
    @Operation(summary = "Create an Activity.")
    @APIResponse(
            responseCode = "201",
            description = "Activity successfully created."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to create Activity. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to create Activity. Verify 'Warning' Header."
    )
    public Response create(DTOCreateActivity dtoCreateActivity) {

        if(dtoCreateActivity == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        String description = dtoCreateActivity.getDescription();
        Integer level = dtoCreateActivity.getLevel();
        Integer content = dtoCreateActivity.getContent();
        if(!utilityVerifyRequestField.isValidField(description) ||
                !utilityVerifyRequestField.isValidField(level) ||
                !utilityVerifyRequestField.isValidField(content)) {
            throw new HttpBadRequestException("All fields required.");
        }

        audit.debug("Creating Activity...");
        DTOActivity activity = serviceActivity.create(dtoCreateActivity);

        audit.debug("Creating URI...");
        URI uri = URI.create(BASE_PATH_RESOURCE + activity.getActivityId());

        return Response.created(uri)
                .entity(activity)
                .build();

    }

    @PATCH
    @Path("{id}")
    @Operation(summary = "Update an Activity.")
    @APIResponse(
            responseCode = "200",
            description = "Activities successfully updated."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to update Activity. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to delete Activity. Verify 'Warning' Header."
    )
    public Response update(@PathParam("id") Integer id,
                           DTOUpdateActivity dtoUpdateActivity) {

        if(dtoUpdateActivity == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        Integer activityDTO = dtoUpdateActivity.getActivityId();
        Integer levelDTO = dtoUpdateActivity.getLevel();
        String description = dtoUpdateActivity.getDescription();
        Integer contentDTO = dtoUpdateActivity.getContent();
        if(!utilityVerifyRequestField.isValidField(activityDTO) &&
                !utilityVerifyRequestField.isValidField(levelDTO) &&
                !utilityVerifyRequestField.isValidField(description) &&
                !utilityVerifyRequestField.isValidField(contentDTO)) {
            throw new HttpBadRequestException("No updates to make.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(activityDTO == null ) {
            throw new HttpBadRequestException("Required Id field of Activity to update.");
        }
        if(id.compareTo(dtoUpdateActivity.getActivityId()) != 0) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        audit.debug("Updating Activity " + id + "...");
        return Response.ok(serviceActivity.update(id, dtoUpdateActivity)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Delete a specific Activity by its ID.")
    @APIResponse(
            responseCode = "200",
            description = "Activities successfully deleted."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to delete Activity. Verify 'Warning' Header."
    )
    public Response delete(@PathParam("id") Integer id) {

        audit.debug("Deleting Activity " + id + "...");
        return Response.ok(serviceActivity.delete(id)).build();

    }

}
