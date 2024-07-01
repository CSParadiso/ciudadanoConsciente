package ciudadano.consciente.resource;

import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.model.Organization;
import ciudadano.consciente.service.ServiceReference;
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
  @APIResponse(responseCode = "200", description = "References successfully retrieved.")
  public Response getAll() {

    audit.debug("Getting all References...");
    return Response.ok(serviceReference.getAll()).build();

  }

  @GET
  @Path("{id}")
  @Operation(summary = "Retrieve a specific Reference by its ID.")
  @APIResponse(responseCode = "200", description = "Reference successfully retrieved.")
  @APIResponse(responseCode = "204", description = "Failed to retrieve Reference. Verify 'Warning' Header.")
  public Response get(@PathParam("id") Integer id) {

    audit.debug("Retrieving Reference " + id + ".");
    return Response.ok(serviceReference.get(id)).build();

  }

  @POST
  @Operation(summary = "Create a new Reference.")
  @APIResponse(responseCode = "201", description = "Reference successfully created.")
  @APIResponse(responseCode = "204", description = "Failed to create Reference. Verify 'Warning' Header.")
  @APIResponse(responseCode = "400", description = "Failed to create Reference. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to create Reference. Verify 'Warning' Header.")
  public Response create(DTOCreateReference dtoCreateReference) {

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

    return Response.created(uri).entity(reference).build();

  }

  @PATCH
  @Path("{id}")
  @Operation(summary = "Update a Reference.")
  @APIResponse(responseCode = "200", description = "Reference successfully updated.")
  @APIResponse(responseCode = "204", description = "Failed to update Reference. Verify 'Warning' Header.")
  @APIResponse(responseCode = "400", description = "Failed to update Reference. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to update Reference. Verify 'Warning' Header.")
  public Response update(@PathParam("id") Integer id,
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
    return Response.ok(serviceReference.update(id, dtoUpdateReference)).build();

  }

  @DELETE
  @Path("{id}")
  @Operation(summary = "Delete a Reference.")
  @APIResponse(responseCode = "200", description = "Reference successfully deleted.")
  @APIResponse(responseCode = "204", description = "Failed to delete Reference. Verify 'Warning' Header.")
  public Response delete(@PathParam("id") Integer id) {

    audit.debug("Deleting Reference " + id + "...");
    return Response.ok(serviceReference.delete(id)).build();

  }

  // VOTES HANDLING IN CONCERN
  @Deprecated(since = "1.0.1")
  @POST
  @Path("{id}/votes")
  @Operation(summary = "Vote Reference.")
  @APIResponse(responseCode = "201", description = "Reference successfully voted.")
  @APIResponse(responseCode = "400", description = "Failed to Vote Reference. Verify 'Warning' Header.")
  @APIResponse(responseCode = "204", description = "Failed to Vote Reference. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to Vote Reference. Verify 'Warning' Header.")
  public Response vote(@PathParam("id") Integer idReference,
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

    return Response.created(uri).entity(dtoVote).build();

  }

}
