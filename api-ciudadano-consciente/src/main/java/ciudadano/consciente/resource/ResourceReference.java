package ciudadano.consciente.resource;

import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceReference;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.ejb.Schedule;
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

@Tag(name = "Reference Resource")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("references")
public class ResourceReference {

  static final String PATH_BASE_RESOURCE = "/references/";
  static final String PATH_BASE_RESOURCE_VOTES = "/votes/";

  @Inject
  ServiceReference serviceReference;

  @Inject
  Logger audit;

  @Inject
  UtilityVerifyRequestField utilityVerifyRequestField;

  @GET
  @Operation(summary = "Retrieve all References.")
  @APIResponse(responseCode = "200", description = "References successfully retrieved.", content = @Content(schema = @org.eclipse.microprofile.openapi.annotations.media.Schema(implementation = DTOReference.class)))
  public RestResponse<List<DTOReference>> getAll() {

    audit.debug("Getting all References...");
    return RestResponse.ResponseBuilder.ok(serviceReference.getAll()).build();

  }

  @GET
  @Path("{id}")
  @Operation(summary = "Retrieve a specific Reference by its ID.")
  @APIResponse(responseCode = "200", description = "Reference successfully retrieved.", content = @Content(schema = @org.eclipse.microprofile.openapi.annotations.media.Schema(implementation = DTOReference.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Reference. Verify 'Warning' Header.")
  public RestResponse<DTOReference> get(@PathParam("id") Integer id) {

    audit.debug("Retrieving Reference " + id + ".");
    return RestResponse.ResponseBuilder.ok(serviceReference.get(id)).build();

  }

  @POST
  @Operation(summary = "Create a new Reference.")
  @APIResponse(responseCode = "201", description = "Reference successfully created.", content = @Content(schema = @org.eclipse.microprofile.openapi.annotations.media.Schema(implementation = DTOReference.class)))
  @APIResponse(responseCode = "204", description = "Failed to create Reference. Verify 'Warning' Header.")
  @APIResponse(responseCode = "400", description = "Failed to create Reference. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to create Reference. Verify 'Warning' Header.")
  public RestResponse<DTOReference> create(DTOCreateReference dtoCreateReference) {

    if (dtoCreateReference == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    String title = dtoCreateReference.getTitle();
    String url = dtoCreateReference.getUrl();
    Integer levelDto = dtoCreateReference.getLevel();
    if (!utilityVerifyRequestField.isValidField(title) ||
        !utilityVerifyRequestField.isValidField(url) ||
        !utilityVerifyRequestField.isValidField(levelDto)) {
      throw new HttpBadRequestException("Title, URL and Level required.");
    }

    audit.debug("Creating Reference...");
    DTOReference reference = serviceReference.create(dtoCreateReference);

    audit.debug("Creating URI...");
    URI uri = URI.create(PATH_BASE_RESOURCE + reference.getReferenceId());

    return RestResponse.ResponseBuilder
        .create(RestResponse.Status.CREATED, reference)
        .location(uri)
        .build();

  }

  @PATCH
  @Path("{id}")
  @Operation(summary = "Update a Reference.")
  @APIResponse(responseCode = "200", description = "Reference successfully updated.", content = @Content(schema = @org.eclipse.microprofile.openapi.annotations.media.Schema(implementation = DTOReference.class)))
  @APIResponse(responseCode = "204", description = "Failed to update Reference. Verify 'Warning' Header.")
  @APIResponse(responseCode = "400", description = "Failed to update Reference. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to update Reference. Verify 'Warning' Header.")
  public RestResponse<DTOReference> update(@PathParam("id") Integer id,
      DTOUpdateReference dtoUpdateReference) {

    if (dtoUpdateReference == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    Integer level = dtoUpdateReference.getLevel();
    String title = dtoUpdateReference.getTitle();
    String url = dtoUpdateReference.getUrl();
    String description = dtoUpdateReference.getDescription();
    if (!utilityVerifyRequestField.isValidField(level) &&
        !utilityVerifyRequestField.isValidField(title) &&
        !utilityVerifyRequestField.isValidField(url) &&
        !utilityVerifyRequestField.isValidField(description)) {
      throw new HttpBadRequestException("No updates to make.");
    }

    audit.debug("Verifying if the ID of the Body and the Path are the same...");
    if (id.compareTo(dtoUpdateReference.getReferenceId()) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same.");
    }

    audit.debug("Updating Reference " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceReference.update(id, dtoUpdateReference)).build();

  }

  @DELETE
  @Path("{id}")
  @Operation(summary = "Delete a Reference.")
  @APIResponse(responseCode = "200", description = "Reference successfully deleted.", content = @Content(schema = @org.eclipse.microprofile.openapi.annotations.media.Schema(implementation = DTOReference.class)))
  @APIResponse(responseCode = "204", description = "Failed to delete Reference. Verify 'Warning' Header.")
  public RestResponse<DTOReference> delete(@PathParam("id") Integer id) {

    audit.debug("Deleting Reference " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceReference.delete(id)).build();

  }

  // VOTES HANDLING IN CONCERN
  @Deprecated(since = "1.0.1")
  @POST
  @Path("{id}/votes")
  @Operation(summary = "Vote Reference.")
  @APIResponse(responseCode = "201", description = "Reference successfully voted.", content = @Content(schema = @org.eclipse.microprofile.openapi.annotations.media.Schema(implementation = DTOVote.class)))
  @APIResponse(responseCode = "400", description = "Failed to Vote Reference. Verify 'Warning' Header.")
  @APIResponse(responseCode = "204", description = "Failed to Vote Reference. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to Vote Reference. Verify 'Warning' Header.")
  public RestResponse<DTOVote> vote(@PathParam("id") Integer idReference,
      DTOCreateVote dtoCreateVote) {

    if (dtoCreateVote == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    Integer user = dtoCreateVote.getUser();
    Integer reference = dtoCreateVote.getEntity();
    if (!utilityVerifyRequestField.isValidField(user) ||
        !utilityVerifyRequestField.isValidField(reference)) {
      throw new HttpBadRequestException("All fields required.");
    }

    audit.debug("Verifying if the ID of the Body and the Path are the same...");
    if (idReference.compareTo(dtoCreateVote.getEntity()) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same for Reference.");
    }
    audit.debug("Vote of User " + user
        + " in Reference " + idReference + "...");
    DTOVote dtoVote = serviceReference.vote(idReference, user);

    audit.debug("Creating URI...");
    URI uri = URI.create(PATH_BASE_RESOURCE_VOTES + dtoVote.getVoteId());

    return RestResponse.ResponseBuilder
        .create(RestResponse.Status.CREATED, dtoVote)
        .location(uri)
        .build();

  }

  @GET
  @Path("/votes")
  @Operation(summary = "Retrieve votes of References.")
  @APIResponse(responseCode = "200", description = "Votes of References successfully retrieved.", content = @Content(schema = @org.eclipse.microprofile.openapi.annotations.media.Schema(implementation = DTOVotedEntity.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Votes of References. Verify 'Warning' Header.")
  public RestResponse<List<DTOVotedEntity>> getAllVotes() {

    audit.debug("Getting References Votes...");
    return RestResponse.ResponseBuilder.ok(serviceReference.getAllVotes()).build();

  }

  @GET
  @Path("{id}/votes")
  @Operation(summary = "Retrieve votes of a Reference.")
  @APIResponse(responseCode = "200", description = "Votes of Reference successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOVotedEntity.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Votes of Reference. Verify 'Warning' Header.")
  public RestResponse<List<DTOVotedEntity>> getVotes(@PathParam("id") Integer id) {

    audit.debug("Getting Reference " + id + " Votes...");
    return RestResponse.ResponseBuilder.ok(serviceReference.getVotes(id)).build();

  }

  @GET
  @Path("/tags")
  @Operation(summary = "Retrieve tags of References.")
  @APIResponse(responseCode = "200", description = "Tags of References successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOTaggedEntity.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Tags of References. Verify 'Warning' Header.")
  public RestResponse<List<DTOTaggedEntity>> getAllTags() {

    audit.debug("Getting References Tags...");
    return RestResponse.ResponseBuilder.ok(serviceReference.getAllTags()).build();

  }

  @GET
  @Path("{id}/tags")
  @Operation(summary = "Retrieve tags of a Reference.")
  @APIResponse(responseCode = "200", description = "Tags of Reference successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOTaggedEntity.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Tags of Reference. Verify 'Warning' Header.")
  public RestResponse<List<DTOTaggedEntity>> getTags(@PathParam("id") Integer id) {

    audit.debug("Getting Reference " + id + " Tags...");
    return RestResponse.ResponseBuilder.ok(serviceReference.getTags(id)).build();

  }

}
