package ciudadano.consciente.resource;

import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceConcern;
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

import java.net.URI;

@RequestScoped
@Tag(name = "Concern Resource")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("concerns")
public class ResourceConcern {

    final String PATH_BASE_RESOURCE = "/concerns/";
    final String PATH_BASE_RESOURCE_VOTE = "/votes/";

    @Inject
    Logger audit;

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    @Inject
    ServiceConcern serviceConcern;

    @GET
    @Operation(summary = "Retrieve all Concerns")
    @APIResponse(
            responseCode = "200",
            description = "All Concerns successfully retrieved."
    )
    public Response getAll() {

        audit.debug("Getting all Concerns...");
        return Response.ok(serviceConcern.getAll()).build();


    }

    @GET
    @Path("{id}")
    @Operation(summary = "Retrieve a  Concern.")
    @APIResponse(
            responseCode = "200",
            description = "Concern successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve Concern. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to retrieve Concern. Verify 'Warning' Header."
    )
    public Response get(@PathParam("id") Integer id) {

        audit.debug("Getting Concern " + id + "...");
        return Response.ok(serviceConcern.get(id)).build();

    }

    @POST
    @Operation(summary = "Create a Concern.")
    @APIResponse(
            responseCode = "201",
            description = "Concern successfully created."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to create Concern. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to create Concern. Verify 'Warning' Header."
    )
    public Response create(DTOCreateConcern dtoCreateConcern) {

        if(dtoCreateConcern == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        String description = dtoCreateConcern.getDescription();
        Integer user  = dtoCreateConcern.getUser();
        if(!utilityVerifyRequestField.isValidField(description) &&
                !utilityVerifyRequestField.isValidField(user)) {
            throw new HttpBadRequestException("Description and user fields required.");
        }

        audit.debug("Creating Concern...");
        DTOConcern concern = serviceConcern.create(dtoCreateConcern);

        audit.debug("Creating URI...");
        URI uri = URI.create(PATH_BASE_RESOURCE + concern.getConcernId());

        return Response.created(uri)
                .entity(concern)
                .build();

    }


    @PATCH
    @Path("{id}")
    @Operation(summary = "Update a Concern.")
    @APIResponse(
            responseCode = "200",
            description = "Concern successfully updated."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to update Concern. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to update Concern. Verify 'Warning' Header."
    )
    public Response update(@PathParam("id") Integer id,
                           DTOUpdateConcern dtoUpdateConcern) {

        if(dtoUpdateConcern == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(id.compareTo(dtoUpdateConcern.getConcernId()) != 0) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        String description = dtoUpdateConcern.getDescription();
        String explanation = dtoUpdateConcern.getExplanation();
        if(!utilityVerifyRequestField.isValidField(description) &&
                !utilityVerifyRequestField.isValidField(explanation)) {
            throw new HttpBadRequestException("No updates to make.");
        }

        audit.debug("Updating Concern" + id + "...");
        return Response.ok(serviceConcern.update(id, dtoUpdateConcern)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Delete a  Concern by its ID.")
    @APIResponse(
            responseCode = "200",
            description = "Concern successfully deleted."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to delete Concern. Verify 'Warning' Header."
    )
    public Response delete(@PathParam("id") Integer id) {

        audit.debug("Deleting Concern " + id + "...");
        return Response.ok(serviceConcern.delete(id)).build();

    }

    // VOTES HANDLING IN CONCERN
    @POST
    @Path("{id}/votes")
    @Operation(summary = "Vote Concern.")
    @APIResponse(
            responseCode = "201",
            description = "Concern successfully voted."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to Vote Concern. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to Vote Concern. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to Vote Concern. Verify 'Warning' Header."
    )
    public Response vote(@PathParam("id") Integer idConcern,
                         DTOCreateVote dtoCreateVote) {

        if(dtoCreateVote == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        Integer user = dtoCreateVote.getUser();
        Integer concern = dtoCreateVote.getEntity();
        if(!utilityVerifyRequestField.isValidField(user) ||
                !utilityVerifyRequestField.isValidField(concern)) {
            throw new HttpBadRequestException("All fields required.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(idConcern.compareTo(dtoCreateVote.getEntity()) != 0) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same for Concern.");
        }
        audit.debug("Vote of User " + user
                + " in Concern " + idConcern + "...");
        DTOVote dtoVote = serviceConcern.vote(idConcern, user);

        audit.debug("Creating URI...");
        URI uri = URI.create(PATH_BASE_RESOURCE_VOTE + dtoVote.getVoteId());

        return Response.created(uri).entity(dtoVote).build();

    }
    
}
