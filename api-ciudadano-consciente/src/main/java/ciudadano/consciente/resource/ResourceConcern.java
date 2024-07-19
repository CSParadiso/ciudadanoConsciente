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
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;

import java.net.URI;
import java.util.List;

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
          description = "All Concerns successfully retrieved.",
          content = @Content (schema = @Schema (implementation = DTOConcern.class))
  )
  public RestResponse<List<DTOConcern>> getAll() {

    audit.debug("Getting all Concerns...");
    return RestResponse.ResponseBuilder.ok(serviceConcern.getAll()).build();

  }

  @GET
  @Path("{id}")
  @Operation(summary = "Retrieve a  Concern.")
  @APIResponse(
          responseCode = "200",
          description = "Concern successfully retrieved.",
          content = @Content (schema = @Schema (implementation = DTOConcern.class))
  )
  @APIResponse(responseCode = "204", description = "Failed to retrieve Concern. Verify 'Warning' Header.")
  public RestResponse<DTOConcern> get(@PathParam("id") Integer id) {

    audit.debug("Getting Concern " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceConcern.get(id)).build();

  }

  @POST
  @Operation(summary = "Create a Concern.")
  @APIResponse(
          responseCode = "201",
          description = "Concern successfully created.",
          content = @Content (schema = @Schema (implementation = DTOConcern.class))
  )
  @APIResponse(responseCode = "204", description = "Failed to create Concern. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to create Concern. Verify 'Warning' Header.")
  public RestResponse<DTOConcern> create(DTOCreateConcern dtoCreateConcern) {

    if (dtoCreateConcern == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    String description = dtoCreateConcern.getDescription();
    Integer user = dtoCreateConcern.getUser();
    if (!utilityVerifyRequestField.isValidField(description) &&
        !utilityVerifyRequestField.isValidField(user)) {
      throw new HttpBadRequestException("Description and user fields required.");
    }

    audit.debug("Creating Concern...");
    DTOConcern concern = serviceConcern.create(dtoCreateConcern);

    audit.debug("Creating URI...");
    URI uri = URI.create(PATH_BASE_RESOURCE + concern.getConcernId());

    return RestResponse.ResponseBuilder
            .create(RestResponse.Status.CREATED, concern)
            .location(uri)
            .build();

  }

  @PATCH
  @Path("{id}")
  @Operation(summary = "Update a Concern.")
  @APIResponse(
          responseCode = "200",
          description = "Concern successfully updated.",
          content = @Content (schema = @Schema (implementation = DTOConcern.class))
  )
  @APIResponse(responseCode = "400", description = "Failed to update Concern. Verify 'Warning' Header.")
  @APIResponse(responseCode = "204", description = "Failed to update Concern. Verify 'Warning' Header.")
  public RestResponse<DTOConcern> update(@PathParam("id") Integer id,
      DTOUpdateConcern dtoUpdateConcern) {

    if (dtoUpdateConcern == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    audit.debug("Verifying if the ID of the Body and the Path are the same...");
    if (id.compareTo(dtoUpdateConcern.getConcernId()) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same.");
    }

    String description = dtoUpdateConcern.getDescription();
    String explanation = dtoUpdateConcern.getExplanation();
    if (!utilityVerifyRequestField.isValidField(description) &&
        !utilityVerifyRequestField.isValidField(explanation)) {
      throw new HttpBadRequestException("No updates to make.");
    }

    audit.debug("Updating Concern" + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceConcern.update(id, dtoUpdateConcern)).build();

  }

  @DELETE
  @Path("{id}")
  @Operation(summary = "Delete a  Concern by its ID.")
  @APIResponse(
          responseCode = "200",
          description = "Concern successfully deleted.",
          content = @Content (schema = @Schema (implementation = DTOConcern.class))
  )
  @APIResponse(responseCode = "204", description = "Failed to delete Concern. Verify 'Warning' Header.")
  public RestResponse<DTOConcern> delete(@PathParam("id") Integer id) {

    audit.debug("Deleting Concern " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceConcern.delete(id)).build();

  }

  // VOTES HANDLING IN CONCERN
  @Deprecated(since = "1.0.1")
  @POST
  @Path("{id}/votes")
  @Operation(summary = "Vote Concern.")
  @APIResponse(
          responseCode = "201",
          description = "Concern successfully voted.",
          content = @Content (schema = @Schema (implementation = DTOVote.class))
  )
  @APIResponse(responseCode = "400", description = "Failed to Vote Concern. Verify 'Warning' Header.")
  @APIResponse(responseCode = "204", description = "Failed to Vote Concern. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to Vote Concern. Verify 'Warning' Header.")
  public RestResponse<DTOVote> vote(@PathParam("id") Integer idConcern,
      DTOCreateVote dtoCreateVote) {

    if (dtoCreateVote == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    Integer user = dtoCreateVote.getUser();
    Integer concern = dtoCreateVote.getEntity();
    if (!utilityVerifyRequestField.isValidField(user) ||
        !utilityVerifyRequestField.isValidField(concern)) {
      throw new HttpBadRequestException("All fields required.");
    }

    audit.debug("Verifying if the ID of the Body and the Path are the same...");
    if (idConcern.compareTo(dtoCreateVote.getEntity()) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same for Concern.");
    }
    audit.debug("Vote of User " + user
        + " in Concern " + idConcern + "...");
    DTOVote dtoVote = serviceConcern.vote(idConcern, user);

    audit.debug("Creating URI...");
    URI uri = URI.create(PATH_BASE_RESOURCE_VOTE + dtoVote.getVoteId());

    return RestResponse.ResponseBuilder
            .create(RestResponse.Status.CREATED, dtoVote)
            .location(uri)
            .build();

  }

  @GET
  @Path("/votes")
  @Operation(summary = "Retrieve votes of Concerns.")
  @APIResponse(responseCode = "200", description = "Votes of Concerns successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOVotedEntity.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Votes of Concerns. Verify 'Warning' Header.")
  public RestResponse<List<DTOVotedEntity>> getAllVotes() {

    audit.debug("Getting Concerns Votes...");
    return RestResponse.ResponseBuilder.ok(serviceConcern.getAllVotes()).build();

  }

  @GET
  @Path("{id}/votes")
  @Operation(summary = "Retrieve votes of a Concern.")
  @APIResponse(responseCode = "200", description = "Votes of Concern successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOVotedEntity.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Votes of Concern. Verify 'Warning' Header.")
  public RestResponse<List<DTOVotedEntity>> getVotes(@PathParam("id") Integer id) {

    audit.debug("Getting Concern " + id + " Votes...");
    return RestResponse.ResponseBuilder.ok(serviceConcern.getVotes(id)).build();

  }

}
