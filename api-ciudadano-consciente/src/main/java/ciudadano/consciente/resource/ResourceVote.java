package ciudadano.consciente.resource;

import ciudadano.consciente.dto.DTOTagged;
import ciudadano.consciente.dto.DTOVote;
import ciudadano.consciente.service.ServiceVote;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

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
  @APIResponse(responseCode = "200", description = "Votes successfully retrieved.")
  @APIResponse(responseCode = "204", description = "Failed to retrieve all Votes. Verify 'Warning' Header.")
  public Response getAll() {

    audit.debug("Getting all Votes...");
    return Response.ok(serviceVote.getAll()).build();

  }

  @GET
  @Path("{id}")
  @Operation(summary = "Retrieve a  Vote by its ID.")
  @APIResponse(responseCode = "200", description = "Vote successfully retrieved.")
  @APIResponse(responseCode = "204", description = "Failed to retrieve Vote. Verify 'Warning' Header.")
  public Response get(@PathParam("id") Integer id) {

    audit.debug("Getting Vote " + id + "...");
    return Response.ok(serviceVote.get(id)).build();

  }

  @POST
  @Path("{userId}/{entityTypeId}/{entityId}")
  @Operation(summary = "Vote an Entity.")
  @APIResponse(responseCode = "201", description = "Vote successfully performed.")
  @APIResponse(responseCode = "204", description = "Failed to Vote. Verify 'Warning' Header.")
  @APIResponse(responseCode = "400", description = "Failed to Vote. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to Vote. Verify 'Warning' Header.")
  public Response tagEntity(@PathParam("userId") Integer userId,
      @PathParam("entityTypeId") Integer entityTypeId,
      @PathParam("entityId") Integer entityId) {

    audit.debug("Votting Entity...");
    DTOVote dtoVote = serviceVote.voteEntity(userId, entityTypeId, entityId);

    audit.debug("Creating URI...");
    URI uri = URI.create(PATH_BASE_RESOURCE + dtoVote.getVoteId());

    return Response.created(uri).entity(dtoVote).build();

  }

  @PATCH
  @Path("{id}/status")
  @Operation(summary = "Update Status of Vote.")
  @APIResponse(responseCode = "200", description = "Vote Status successfully updated.")
  @APIResponse(responseCode = "400", description = "Failed to update Vote Status. Verify 'Warning' Header.")
  @APIResponse(responseCode = "204", description = "Failed to update Vote Status. Verify 'Warning' Header.")
  public Response updateStatus(@PathParam("id") Integer id) {

    /*
     * Quizás este endpoint podría ser cacheado y solo usarse una vez que se
     * desloguea el user.
     */
    audit.debug("Updating Vote Status" + id + "...");
    return Response.ok(serviceVote.updateStatus(id)).build();

  }

}
