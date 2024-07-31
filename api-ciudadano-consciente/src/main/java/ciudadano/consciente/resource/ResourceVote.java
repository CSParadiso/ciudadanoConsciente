package ciudadano.consciente.resource;

import ciudadano.consciente.dto.DTOVote;
import ciudadano.consciente.service.ServiceVote;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;

@Authenticated
@Tag(name = "Vote Resource")
@Path("votes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ResourceVote {

  final String PATH_BASE_RESOURCE = "/votes/";

  @Inject
  Logger audit;

  @Inject
  UtilityVerifyRequestField utilityVerifyRequestField;

  @Inject
  ServiceVote serviceVote;

  @GET
  @Operation(summary = "Retrieve all Votes.")
  @APIResponse(responseCode = "200", description = "Votes successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOVote.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve all Votes. Verify 'Warning' Header.")
  public RestResponse<List<DTOVote>> getAll() {

    audit.debug("Getting all Votes...");
    return RestResponse.ResponseBuilder.ok(serviceVote.getAll()).build();

  }

  @GET
  @Path("{id}")
  @Operation(summary = "Retrieve a  Vote by its ID.")
  @APIResponse(responseCode = "200", description = "Vote successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOVote.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Vote. Verify 'Warning' Header.")
  public RestResponse<DTOVote> get(@PathParam("id") Integer id) {

    audit.debug("Getting Vote " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceVote.get(id)).build();

  }

  @POST
  @Path("{userId}/{entityTypeId}/{entityId}")
  @Operation(summary = "Vote an Entity.")
  @APIResponse(responseCode = "201", description = "Vote successfully performed.", content = @Content(schema = @Schema(implementation = DTOVote.class)))
  @APIResponse(responseCode = "204", description = "Failed to Vote. Verify 'Warning' Header.")
  @APIResponse(responseCode = "400", description = "Failed to Vote. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to Vote. Verify 'Warning' Header.")
  public RestResponse<DTOVote> tagEntity(@PathParam("userId") Integer userId,
      @PathParam("entityTypeId") Integer entityTypeId,
      @PathParam("entityId") Integer entityId) {

    audit.debug("Votting Entity...");
    DTOVote dtoVote = serviceVote.voteEntity(userId, entityTypeId, entityId);

    audit.debug("Creating URI...");
    URI uri = URI.create(PATH_BASE_RESOURCE + dtoVote.getVoteId());

    return RestResponse.ResponseBuilder
        .create(RestResponse.Status.CREATED, dtoVote)
        .location(uri)
        .build();

  }

  @PATCH
  @Path("{id}/status")
  @Operation(summary = "Update Status of Vote.")
  @APIResponse(responseCode = "200", description = "Vote Status successfully updated.", content = @Content(schema = @Schema(implementation = DTOVote.class)))
  @APIResponse(responseCode = "400", description = "Failed to update Vote Status. Verify 'Warning' Header.")
  @APIResponse(responseCode = "204", description = "Failed to update Vote Status. Verify 'Warning' Header.")
  public RestResponse<DTOVote> updateStatus(@PathParam("id") Integer id) {

    /*
     * Quizás este endpoint podría ser cacheado y solo usarse una vez que se
     * desloguea el user.
     */
    audit.debug("Updating Vote Status" + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceVote.updateStatus(id)).build();

  }

}
