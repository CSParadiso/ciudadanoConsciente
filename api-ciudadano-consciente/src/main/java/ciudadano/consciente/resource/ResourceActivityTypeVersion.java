package ciudadano.consciente.resource;

import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceActivityTypeVersion;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;

import java.net.URI;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("activity-type-version")
public class ResourceActivityTypeVersion {

    final String BASE_PATH_RESOURCE = "/activity-type-version/";
    final String BASE_PATH_RESOURCE_VOTE = "/votes/";

    @Inject
    ServiceActivityTypeVersion serviceActivityTypeVersion;

    @Inject
    Logger audit;

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    @GET
    @Path("{activity-type}")
    @Operation(summary = "Retrieve all Versions of a Activity Type.")
    @APIResponse(
            responseCode = "200",
            description = "Versions of Activity Type successfully retrieved."
    )
    public Response getAllByActivityType(@PathParam("activity-type") Integer activityType) {

        audit.debug("Getting all the Versions of a Activity Type...");
        return Response.ok(serviceActivityTypeVersion.getAllByActivityType(activityType)).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Retrieve a specific Activity Type by its ID.")
    @APIResponse(
            responseCode = "200",
            description = "Activity Types Version successfully retrieved."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to retrieve Activity Type. Verify 'Warning' Header."
    )
    public Response get(@PathParam("id") Integer id) {

        audit.debug("Getting Activity Type " + id + "...");
        return Response.ok(serviceActivityTypeVersion.get(id)).build();

    }

    @POST
    @Operation(summary = "Create an Activity Type Version.")
    @APIResponse(
            responseCode = "201",
            description = "Activity Type Version successfully created."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to create Activity Type Version. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to create Activity Type Version. Verify 'Warning' Header."
    )
    public Response create(DTOCreateActivityTypeVersion dtoCreateActivityTypeVersion) {

        if(dtoCreateActivityTypeVersion == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        Integer activityTypeId = dtoCreateActivityTypeVersion.getActivityTypeId();
        Integer activityTypeVersionStatusId = dtoCreateActivityTypeVersion.getActivityTypeVersionStatusId();
        String githubUser = dtoCreateActivityTypeVersion.getGithubUser();
        String githubRepo = dtoCreateActivityTypeVersion.getGithubRepo();
        String githubPath = dtoCreateActivityTypeVersion.getGithubPath();
        String githubShaModel = dtoCreateActivityTypeVersion.getGithubShaModel();
        String githubShaTemplate = dtoCreateActivityTypeVersion.getGithubShaTemplate();
        String githubShaReadme = dtoCreateActivityTypeVersion.getGithubShaReadme();
        String githubShaThumbnail = dtoCreateActivityTypeVersion.getGithubShaThumbnail();
        if(!utilityVerifyRequestField.isValidField(activityTypeId) ||
                !utilityVerifyRequestField.isValidField(activityTypeVersionStatusId) ||
                !utilityVerifyRequestField.isValidField(githubUser) ||
                !utilityVerifyRequestField.isValidField(githubRepo) ||
                !utilityVerifyRequestField.isValidField(githubPath) ||
                !utilityVerifyRequestField.isValidField(githubShaModel) ||
                !utilityVerifyRequestField.isValidField(githubShaTemplate) ||
                !utilityVerifyRequestField.isValidField(githubShaReadme) ||
                !utilityVerifyRequestField.isValidField(githubShaThumbnail)) {
            throw new HttpBadRequestException("All fields required.");
        }

        audit.debug("Creating Activity Type Version...");
        DTOActivityTypeVersion activityTypeVersion = serviceActivityTypeVersion.create(dtoCreateActivityTypeVersion);

        audit.debug("Creating URI...");
        URI uri = URI.create(BASE_PATH_RESOURCE + activityTypeVersion.getActivityTypeVersionId());

        return Response.created(uri)
                .entity(activityTypeVersion)
                .build();

    }

    @PATCH
    @Path("{id}")
    @Operation(summary = "Update an Activity Type Version.")
    @APIResponse(
            responseCode = "200",
            description = "Activity Types Version successfully updated."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to update Activity Type Version. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to delete Activity Type Version. Verify 'Warning' Header."
    )
    public Response update(@PathParam("id") Integer id,
                           DTOUpdateActivityTypeVersion dtoUpdateActivityTypeVersion) {

        if(dtoUpdateActivityTypeVersion == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        Integer activityTypeVersionId = dtoUpdateActivityTypeVersion.getActivityTypeVersionId();
        Integer activityTypeVersionStatusId = dtoUpdateActivityTypeVersion.getActivityTypeVersionStatusId();
        if(!utilityVerifyRequestField.isValidField(activityTypeVersionId) &&
                !utilityVerifyRequestField.isValidField(activityTypeVersionStatusId)) {
            throw new HttpBadRequestException("No updates to make.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(id.compareTo(dtoUpdateActivityTypeVersion.getActivityTypeVersionId()) != 0) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        audit.debug("Updating Activity Type Version " + id + "...");
        return Response.ok(serviceActivityTypeVersion.update(id, dtoUpdateActivityTypeVersion)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Delete a specific Activity Type Version by its ID.")
    @APIResponse(
            responseCode = "200",
            description = "Activity Types Version successfully deleted."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to delete Activity Type Version. Verify 'Warning' Header."
    )
    public Response delete(@PathParam("id") Integer id) {

        audit.debug("Deleting Activity Type Version" + id + "...");
        return Response.ok(serviceActivityTypeVersion.delete(id)).build();

    }

    // VOTES HANDLING IN ACTIVITY TYPE
    @POST
    @Path("{id}/votes")
    @Operation(summary = "Vote Activity Type Version.")
    @APIResponse(
            responseCode = "201",
            description = "Activity Type Version successfully voted."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to Vote Activity Type Version. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to Vote Activity Type Version. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to Vote Activity Type Version. Verify 'Warning' Header."
    )
    public Response vote(@PathParam("id") Integer idActivityTypeVersion,
                         DTOCreateVote dtoCreateVote) {

        if(dtoCreateVote == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        Integer user = dtoCreateVote.getUser();
        Integer activityTypeVersion = dtoCreateVote.getEntity();
        if(!utilityVerifyRequestField.isValidField(user) ||
                !utilityVerifyRequestField.isValidField(activityTypeVersion)) {
            throw new HttpBadRequestException("All fields required.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(idActivityTypeVersion.compareTo(dtoCreateVote.getEntity()) != 0) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same for ActivityTypeVersion.");
        }
        audit.debug("Vote of User " + user
                + " in ActivityTypeVersion " + idActivityTypeVersion + "...");
        DTOVote dtoVote = serviceActivityTypeVersion.vote(idActivityTypeVersion, user);

        audit.debug("Creating URI...");
        URI uri = URI.create(BASE_PATH_RESOURCE_VOTE + dtoVote.getVoteId());

        return Response.created(uri).entity(dtoVote).build();

    }
    
}
