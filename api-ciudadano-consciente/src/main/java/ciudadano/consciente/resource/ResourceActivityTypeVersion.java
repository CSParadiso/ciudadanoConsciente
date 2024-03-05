package ciudadano.consciente.resource;

import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceActivityTypeVersion;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.hibernate.dialect.PostgreSQLJsonPGObjectJsonbType;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;
import org.jose4j.json.internal.json_simple.JSONObject;

import java.net.URI;
import java.util.Base64;

@Tag(name = "Activity Type Version Resource")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
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

    @GET // This should be in Activity Type
    @Path("activity-type/{activity-type}")
    @Operation(summary = "Retrieve all Versions of a Activity Type.")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponse(
            responseCode = "200",
            description = "Versions of Activity Type successfully retrieved."
    )
    public Response getAllByActivityType(@PathParam("activity-type") Integer activityType,
                                         @QueryParam("status") Integer status) {

        audit.debug("Getting all the Versions of a Activity Type...");

        if(utilityVerifyRequestField.isValidField(status)) {
            return Response.ok(serviceActivityTypeVersion.getAllByActivityTypeAndStatus(activityType, status)).build();
        }

        return Response.ok(serviceActivityTypeVersion.getAllByActivityType(activityType)).build();

    }

    @GET
    @Operation(summary = "Retrieve all Versions (optional: status)")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponse(
            responseCode = "200",
            description = "Status of Versions successfully retrieved."
    )
    public Response getAllByStatus(@QueryParam("status") Integer status) {

        audit.debug("Getting all the Versions...");
        if(utilityVerifyRequestField.isValidField(status)) {
            return Response.ok(serviceActivityTypeVersion.getAllByStatus(status)).build();
        }

        return Response.ok(serviceActivityTypeVersion.getAll()).build();


    }

    @GET
    @Path("{id}")
    @Operation(summary = "Retrieve a specific Version of an Activity Type by its ID.")
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponse(
            responseCode = "200",
            description = "Activity Types Version successfully retrieved."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to retrieve Version of an Activity Type. Verify 'Warning' Header."
    )
    public Response get(@PathParam("id") Integer id) {

        audit.debug("Getting Activity Type Version " + id + "...");
        return Response.ok(serviceActivityTypeVersion.get(id)).build();

    }

    @GET
    @Path("{id}/{filename}")
    @Produces({"application/json", "text/javascript",  "text/markdown", "image/png"})
    @Operation(summary = "Retrieve the content of a specific Activity Type Version. THIS WILL BE MADE IN THE FRONT END, IN THE APP.")
    @APIResponse(
            responseCode = "200",
            description = "Content of Activity Types Version successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve Content of Activity Type Version. Verify 'Warning' Header."
    )
    public Response getContent(@PathParam("id") Integer id,
                               @PathParam("filename") @DefaultValue("template") String filename) {

        audit.debug("Getting Content of Activity Type Version " + id + "...");
        Object content = serviceActivityTypeVersion.getContent(id, filename);
        if (content instanceof byte[]) {
            // If the content is a byte array (image), set the appropriate content type
            return Response.ok(content)
                    .type("image/*") // TODO Quizás: clases por cada archivo soportado y así poder hacer content.getMimeType
                    .build();
        }
        // If the content is a string, return it as plain text
        return Response.ok(content)
                .type(MediaType.TEXT_PLAIN) // TODO Quizás: clases por cada archivo soportado y así poder hacer content.getMimeType
                .build();

    }

    @POST
    @Operation(summary = "Create Activity Type Version. Upload local files.")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @APIResponse(
            responseCode = "201",
            description = "Activity Type Version successfully created."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to create Activity Type Version. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to create Activity Type Version. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to create new Activity Type Version. Verify 'Warning' Header."
    )
    public Response create(DTOCreateActivityTypeVersion dtoCreateActivityTypeVersion) {

        if(dtoCreateActivityTypeVersion == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        Integer activityType = dtoCreateActivityTypeVersion.getActivityTypeId();
        byte[] model = dtoCreateActivityTypeVersion.getModel();
        byte[] template = dtoCreateActivityTypeVersion.getTemplate();
        byte[] readme = dtoCreateActivityTypeVersion.getReadme();
        byte[] thumbnail = dtoCreateActivityTypeVersion.getThumbnail();
        if(!utilityVerifyRequestField.isValidField(activityType) ||
                !utilityVerifyRequestField.isValidField(model) ||
                !utilityVerifyRequestField.isValidField(template) ||
                !utilityVerifyRequestField.isValidField(readme) ||
                !utilityVerifyRequestField.isValidField(thumbnail)) {
            throw new HttpBadRequestException("All fields required. (No empty files allowed.)");
        }

        audit.debug("Creating new version...");
        DTOActivityTypeVersion activityTypeVersion = serviceActivityTypeVersion.create(dtoCreateActivityTypeVersion);

        audit.debug("Creating URI for new Activity Type Version");
        URI uri = URI.create(BASE_PATH_RESOURCE + activityTypeVersion.getActivityTypeVersionId());

        return Response.created(uri)
                .entity(activityTypeVersion)
                .build();

    }

    /**
     *
     * THIS IS DEPRECATED BECAUSE THE FRONT END SHOULD VALIDATE THE FILES
     * TO MAKE SURE THEY HAVE THE RIGHT CONTENT. THIS WILL BE MADE IN A SANDBOX AREA
     * FOR THE DEVELOPERS TO TEST THEIR VERSION BEFORE THE SUBMIT.
     *
     */
    @Deprecated
    @POST
    @Path("{server}")
    @Operation(summary = "Create Activity Type Version. Require a version server provider (only github support initially).")
    @APIResponse(
            responseCode = "201",
            description = "Activity Type Version successfully created."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to create Activity Type Version. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to create Activity Type Version. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "502",
            description = "Failed to retrieve files from version server. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to create new Activity Type Version. Verify 'Warning' Header."
    )
    public Response createVersion(@PathParam("server") @DefaultValue("github") String versionServerProvider,
                           DTOCreateActivityTypeVersionFromServer dtoCreateActivityTypeVersionFromServer) {

        if(dtoCreateActivityTypeVersionFromServer == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        Integer activityType = dtoCreateActivityTypeVersionFromServer.getActivityTypeId();
        String user = dtoCreateActivityTypeVersionFromServer.getUser();
        String repo = dtoCreateActivityTypeVersionFromServer.getRepo();
        String path = dtoCreateActivityTypeVersionFromServer.getPath();
        String commit = dtoCreateActivityTypeVersionFromServer.getCommit();
        if(!utilityVerifyRequestField.isValidField(activityType) ||
                !utilityVerifyRequestField.isValidField(user) ||
                !utilityVerifyRequestField.isValidField(repo) ||
                !utilityVerifyRequestField.isValidField(path) ||
                !utilityVerifyRequestField.isValidField(commit)) {
            throw new HttpBadRequestException("All fields required.");
        }

        audit.debug("Verifying files for new version...");
        DTOActivityTypeVersion activityTypeVersion = serviceActivityTypeVersion.create(versionServerProvider, dtoCreateActivityTypeVersionFromServer);

        audit.debug("Creating URI for new Activity Type Version");
        URI uri = URI.create(BASE_PATH_RESOURCE + activityTypeVersion.getActivityTypeVersionId());

        return Response.created(uri)
                .entity(activityTypeVersion)
                .build();

    }

    @PATCH
    @Path("{id}")
    @Operation(summary = "Update the Status of an Activity Type Version.")
    @APIResponse(
            responseCode = "200",
            description = "Status of Activity Types Version successfully updated."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to update Status of Activity Type Version. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to update Status Activity Type Version. Verify 'Warning' Header."
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

        audit.debug("Updating Status of Activity Type Version " + id + "...");
        return Response.ok(serviceActivityTypeVersion.update(id, dtoUpdateActivityTypeVersion)).build();

    }

    /**
     *
     * The status of the version turns into 'DELETED'. The 'Content' that already uses
     * the version can keep making use of it.
     * To revert a status after being deleted, the user must contact the developer team
     * or pay a small fee.
     * */
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
