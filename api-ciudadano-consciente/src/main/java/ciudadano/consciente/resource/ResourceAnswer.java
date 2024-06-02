package ciudadano.consciente.resource;

import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.model.Organization;
import ciudadano.consciente.service.ServiceAnswer;
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
import java.util.List;

@RequestScoped
@Tag(name = "Answer Resource")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("answers")
public class ResourceAnswer {

    final String PATH_BASE_RESOURCE = "/answers/";
    
    @Inject
    Logger audit;
    
    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;
    
    @Inject
    ServiceAnswer serviceAnswer;

    @GET
    @Operation(summary = "Retrieve all Answers.")
    @APIResponse(
            responseCode = "200",
            description = "Answers successfully retrieved.",
            content = @Content(schema = @Schema(implementation = DTOAnswer.class))
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve all Answers. Verify 'Warning' Header."
    )
    public Response getAll() {

        audit.debug("Getting all Answers...");
        return Response.ok(serviceAnswer.getAll()).build();

    }

    @GET
    @Path("levels/{levelId}/childrens")
    @Operation(summary = "Retrieve all Answers from a Level and his childrens.")
    @APIResponse(
            responseCode = "200",
            description = "Answers successfully retrieved.",
            content = @Content(schema = @Schema(implementation = DTOAnswerOfChildrens.class))
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve Answers. Verify 'Warning' header."
    )
    public Response getAllChildrenLevelsAnswers(@PathParam("levelId") Integer levelId) {

        audit.debug("Retrieving all Children Levels Answers.");
        return Response.ok(serviceAnswer.getAllChildrenLevelsAnswers(levelId)).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Retrieve a  Answer by its ID.")
    @APIResponse(
            responseCode = "200",
            description = "Answer successfully retrieved.",
            content = @Content(schema = @Schema(implementation = DTOAnswer.class))
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve Answer. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to retrieve Answer. Verify 'Warning' Header."
    )
    public Response get(@PathParam("id") Integer id) {

        audit.debug("Getting Answer " + id + "...");
        return Response.ok(serviceAnswer.get(id)).build();

    }

    @POST
    @Operation(summary = "Create a Answer.")
    @APIResponse(
            responseCode = "201",
            description = "Answer successfully created.",
            content = @Content(schema = @Schema(implementation = DTOAnswer.class))
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to create Answer. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to create Answer. Verify 'Warning' Header."
    )
    public Response create(DTOCreateAnswer dtoCreateAnswer) {

        if(dtoCreateAnswer == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        Integer activity = dtoCreateAnswer.getActivity();
        Integer user = dtoCreateAnswer.getUserId();
        Boolean status = dtoCreateAnswer.getStatus();
        if(!utilityVerifyRequestField.isValidField(activity) &&
            !utilityVerifyRequestField.isValidField(user) &&
            !utilityVerifyRequestField.isValidField(status)) {
            throw new HttpBadRequestException("All fields required.");
        }

        audit.debug("Creating Answer...");
        DTOAnswer answer = serviceAnswer.create(dtoCreateAnswer);

        audit.debug("Creating URI...");
        URI uri = URI.create(PATH_BASE_RESOURCE + answer.getAnswerId());

        return Response.created(uri)
                .entity(answer)
                .build();

    }

    @Deprecated(since = "The request should be atomic, not for a Collection.")
    @POST
    @Path("batch")
    @Operation(summary = "Create Answers in Batch Mode.")
    @APIResponse(
            responseCode = "200",
            description = "Answers successfully created.",
            content = @Content(schema = @Schema(implementation = DTOAnswer.class))
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to create Answer. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to create Answer. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to create Answer. Verify 'Warning' Header."
    )
    public Response createBatchAnswers(DTOCreateBatchAnswer dtoCreateBatchAnswers) {

        if(dtoCreateBatchAnswers == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        Integer user = dtoCreateBatchAnswers.getUserId();
        if(!utilityVerifyRequestField.isValidField(user)) {
            throw new HttpBadRequestException("User Id field required.");
        }

        audit.debug("Creating Answers...");
        List<DTOAnswer> answers = serviceAnswer.createBatchAnswers(dtoCreateBatchAnswers);

        return Response.ok(answers).build();

    }

    @PATCH
    @Path("{id}/status")
    @Operation(summary = "Update Status of Answer.")
    @APIResponse(
            responseCode = "200",
            description = "Answer Status successfully updated.",
            content = @Content(schema = @Schema(implementation = DTOAnswer.class))
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to update Answer Status. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to update Answer Status. Verify 'Warning' Header."
    )
    public Response updateStatus(@PathParam("id") Integer id,
                           DTOUpdateAnswerStatus dtoUpdateAnswerStatus) {

        if(dtoUpdateAnswerStatus == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(id.compareTo(dtoUpdateAnswerStatus.getAnswerStatusId()) != 0) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        Boolean status = dtoUpdateAnswerStatus.getStatus();
        if(!utilityVerifyRequestField.isValidField(status)) {
            throw new HttpBadRequestException("Status required.");
        }
        // TODO Podría simplemente negar lo que ya estaba
        audit.debug("Updating Answer Status" + id + "...");
        return Response.ok(serviceAnswer.updateStatus(id, dtoUpdateAnswerStatus)).build();

    }
    
}
