package ciudadano.consciente.resource;

import ciudadano.consciente.dto.DTOTagged;
import ciudadano.consciente.service.ServiceTagged;
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

@Tag(name = "Tagged Entities Resource")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
@Path("tagged")
public class ResourceTagged {

  static final String PATH_BASE_RESOURCE = "/tagged/";

  @Inject
  Logger audit;

  @Inject
  ServiceTagged serviceTagged;

  @Inject
  UtilityVerifyRequestField utilityVerifyRequestField;

  @GET
  @Operation(summary = "Retrieve all Tagged")
  @APIResponse(responseCode = "200", description = "All Tagged successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOTagged.class)))
  public RestResponse<List<DTOTagged>> getAll() {

    audit.debug("Getting all Concerns...");
    return RestResponse.ResponseBuilder.ok(serviceTagged.getAll()).build();

  }

  @GET
  @Path("{id}")
  @Operation(summary = "Retrieve a  Tagged.")
  @APIResponse(responseCode = "200", description = "Tagged successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOTagged.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Tagged. Verify 'Warning' Header.")
  public RestResponse<DTOTagged> get(@PathParam("id") Integer id) {

    audit.debug("Getting Tagged " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceTagged.get(id)).build();

  }

  @POST
  @Path("{tagId}/{entityTypeId}/{entityId}")
  @Operation(summary = "Tag an Entity.")
  @APIResponse(responseCode = "201", description = "Tag successfully performed.", content = @Content(schema = @Schema(implementation = DTOTagged.class)))
  @APIResponse(responseCode = "204", description = "Failed to Tag. Verify 'Warning' Header.")
  @APIResponse(responseCode = "400", description = "Failed to Tag. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to Tag. Verify 'Warning' Header.")
  public RestResponse<DTOTagged> tagEntity(@PathParam("tagId") Integer tagId,
      @PathParam("entityTypeId") Integer entityTypeId,
      @PathParam("entityId") Integer entityId) {

    audit.debug("Tagging Entity...");
    DTOTagged dtoTagged = serviceTagged.tagEntity(tagId, entityTypeId, entityId);

    audit.debug("Creating URI...");
    URI uri = URI.create(PATH_BASE_RESOURCE + dtoTagged.getTaggedId());

    return RestResponse.ResponseBuilder
        .create(RestResponse.Status.CREATED, dtoTagged)
        .location(uri)
        .build();

  }

  @DELETE
  @Path("{id}")
  @Operation(summary = "Delete a  Tagged by its ID.")
  @APIResponse(responseCode = "200", description = "Tagged successfully deleted.", content = @Content(schema = @Schema(implementation = DTOTagged.class)))
  @APIResponse(responseCode = "204", description = "Failed to delete Tagged. Verify 'Warning' Header.")
  public RestResponse<DTOTagged> delete(@PathParam("id") Integer id) {

    audit.debug("Deleting Tagged " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceTagged.delete(id)).build();

  }

}
