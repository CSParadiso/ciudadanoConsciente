package ciudadano.consciente.resource;

import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceTag;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import io.quarkus.runtime.annotations.ConvertWith;
import io.quarkus.security.Authenticated;
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

@Authenticated
@Tag(name = "Tags Resource")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
@Path("tags")
public class ResourceTag {

  static final String PATH_BASE_RESOURCE = "/tags/";

  @Inject
  Logger audit;

  @Inject
  ServiceTag serviceTag;

  @Inject
  UtilityVerifyRequestField utilityVerifyRequestField;

  @GET
  @Operation(summary = "Retrieve all Tags.")
  @APIResponse(responseCode = "200", description = "Tags successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOTag.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Tags. Verify 'Warning' Header.")
  public RestResponse<List<DTOTag>> getAll() {

    audit.debug("Getting all Tags...");
    return RestResponse.ResponseBuilder.ok(serviceTag.getAll()).build();

  }

  @GET
  @Path("{id}")
  @Operation(summary = "Retrieve a specific Tag by its ID.")
  @APIResponse(responseCode = "200", description = "Tag successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOTag.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Tag. Verify 'Warning' Header.")
  public RestResponse<DTOTag> get(@PathParam("id") Integer id) {

    audit.debug("Retrieving Tag " + id + ".");
    return RestResponse.ResponseBuilder.ok(serviceTag.get(id)).build();

  }

  @POST
  @Operation(summary = "Create a new Tag.")
  @APIResponse(responseCode = "201", description = "Tag successfully created.", content = @Content(schema = @Schema(implementation = DTOTag.class)))
  @APIResponse(responseCode = "204", description = "Failed to create Tag. Verify 'Warning' Header.")
  @APIResponse(responseCode = "400", description = "Failed to create Tag. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to create Tag. Verify 'Warning' Header.")
  public RestResponse<DTOTag> create(DTOCreateTag dtoCreateTag) {

    if (dtoCreateTag == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    String name = dtoCreateTag.getName();
    if (!utilityVerifyRequestField.isValidField(name)) {
      throw new HttpBadRequestException("Name required.");
    }

    audit.debug("Creating Tag...");
    DTOTag dtoTag = serviceTag.create(dtoCreateTag);

    audit.debug("Creating URI...");
    URI uri = URI.create(PATH_BASE_RESOURCE + dtoTag.getTagId());

    return RestResponse.ResponseBuilder
        .create(RestResponse.Status.CREATED, dtoTag)
        .location(uri)
        .build();

  }

  @PATCH
  @Path("{id}")
  @Operation(summary = "Update a Tag.")
  @APIResponse(responseCode = "200", description = "Tag successfully updated.", content = @Content(schema = @Schema(implementation = DTOTag.class)))
  @APIResponse(responseCode = "400", description = "Failed to update Tag. Verify 'Warning' Header.")
  @APIResponse(responseCode = "204", description = "Failed to update Tag. Verify 'Warning' Header.")
  public RestResponse<DTOTag> update(@PathParam("id") Integer id,
      DTOUpdateTag dtoUpdateTag) {

    if (dtoUpdateTag == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    audit.debug("Verifying if the ID of the Body and the Path are the same...");
    if (id.compareTo(dtoUpdateTag.getTagId()) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same.");
    }

    String name = dtoUpdateTag.getName();
    Integer idTag = dtoUpdateTag.getTagId();
    if (!utilityVerifyRequestField.isValidField(name) &&
        !utilityVerifyRequestField.isValidField(idTag)) {
      throw new HttpBadRequestException("No updates to make.");
    }

    audit.debug("Updating Tag" + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceTag.update(id, dtoUpdateTag)).build();

  }

  @DELETE
  @Path("{id}")
  @Operation(summary = "Delete a  Tag by its ID.")
  @APIResponse(responseCode = "200", description = "Tag successfully deleted.", content = @Content(schema = @Schema(implementation = DTOTag.class)))
  @APIResponse(responseCode = "204", description = "Failed to delete Tag. Verify 'Warning' Header.")
  public RestResponse<DTOTag> delete(@PathParam("id") Integer id) {

    audit.debug("Deleting Tag " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceTag.delete(id)).build();

  }

}
