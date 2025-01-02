package ciudadano.consciente.resource;

import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceRole;
import ciudadano.consciente.dto.DTOUpdateRole;
import ciudadano.consciente.dto.DTOCreateRole;
import ciudadano.consciente.dto.DTORole;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;

import java.net.URI;
import java.util.List;

@Tag(name = "Role Resource")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("roles/")
public class ResourceRole {

  final String BASE_PATH_RESOURCE = "/roles/";

  @Inject
  Logger audit;

  @Inject
  ServiceRole serviceRole;

  @Inject
  UtilityVerifyRequestField utilityVerifyRequestField;

  /*
   * Only Ciuco-Admin and O-Divulgator could get and assing Roles.
   * TODO O-Divulgator cannot assign Ciuco-Admin, Button or Tester Roles, and ONLY
   * in the Organizations that represents
   */
  @RolesAllowed({ "Ciuco-Admin", "O-Divulgator" })
  @GET
  @Operation(summary = "Retrieve all Roles.")
  @APIResponse(responseCode = "200", description = "Roles successfully retrieved.", content = @Content(schema = @Schema(implementation = DTORole.class)))
  public RestResponse<List<DTORole>> getAll() {

    audit.debug("Retrieving all Roles...");
    return RestResponse.ResponseBuilder.ok(serviceRole.getAll()).build();

  }

  /*
   * Only Ciuco-Admin and O-Divulgator could get and assing Roles.
   * TODO O-Divulgator cannot assign Ciuco-Admin, Button or Tester Roles, and ONLY
   * in the Organizations that represents
   */
  @RolesAllowed({ "Ciuco-Admin", "O-Divulgator" })
  @GET
  @Path("{id}")
  @Operation(summary = "Retrieve a specific Role by its ID.")
  @APIResponse(responseCode = "200", description = "Role successfully retrieved.", content = @Content(schema = @Schema(implementation = DTORole.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Role. Verify 'Warning' Header.")
  public RestResponse<DTORole> get(@PathParam("id") Integer id) {

    audit.debug("Retrieving Role " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceRole.get(id)).build();

  }

  @RolesAllowed("Ciuco-Admin")
  @POST
  @Operation(summary = "Create a new Role.")
  @APIResponse(responseCode = "201", description = "Role successfully created.", content = @Content(schema = @Schema(implementation = DTORole.class)))
  @APIResponse(responseCode = "400", description = "Failed to create Role.")
  @APIResponse(responseCode = "403", description = "Failed to create Role. User doesn't have enough permissions.")
  @APIResponse(responseCode = "500", description = "Failed to create Role. Verify 'Warning' Header.")
  // REQUIRES UPDATE KEYCLOAK SERVER
  public RestResponse<DTORole> create(DTOCreateRole dtoCreateRole) {

    if (dtoCreateRole == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    String name = dtoCreateRole.getName();
    if (!utilityVerifyRequestField.isValidField(name)) {
      throw new HttpBadRequestException("Name field required.");
    }

    audit.debug("Creating Role...");
    DTORole rol = serviceRole.create(dtoCreateRole);

    audit.debug("Creating URI...");
    URI uri = URI.create(BASE_PATH_RESOURCE + rol.getRoleId());

    return RestResponse.ResponseBuilder
        .create(RestResponse.Status.CREATED, rol)
        .location(uri)
        .build();

  }

  @RolesAllowed("Ciuco-Admin")
  @PATCH
  @Path("{id}")
  @Operation(summary = "Update a Role.")
  @APIResponse(responseCode = "200", description = "Role successfully updated.", content = @Content(schema = @Schema(implementation = DTORole.class)))
  @APIResponse(responseCode = "204", description = "Failed to update Role. Verify 'Warning' Header.")
  @APIResponse(responseCode = "400", description = "Failed to update Role. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to update Role. Verify 'Warning' Header.")
  @APIResponse(responseCode = "502", description = "Failed to update Role. Verify 'Warning' Header.")
  public RestResponse<DTORole> update(@PathParam("id") Integer id, DTOUpdateRole dtoUpdateRole) {

    if (dtoUpdateRole == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    String description = dtoUpdateRole.getDescription();
    if (!utilityVerifyRequestField.isValidField(description)) {
      throw new HttpBadRequestException("No updates to make.");
    }

    audit.debug("Verifying if the ID of the Body and the Path are the same...");
    if (id.compareTo(dtoUpdateRole.getRoleId()) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same.");
    }

    audit.debug("Updating Role... " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceRole.update(id, dtoUpdateRole)).build();

  }

  @RolesAllowed("Ciuco-Admin")
  @DELETE
  @Path("{id}")
  @Operation(summary = "Delete a Role.")
  @APIResponse(responseCode = "200", description = "Role successfully deleted.", content = @Content(schema = @Schema(implementation = DTORole.class)))
  @APIResponse(responseCode = "204", description = "Failed to delete Role. Verify 'Warning' Header.")
  public RestResponse<DTORole> delete(@PathParam("id") Integer id) {

    audit.debug("Deleting Role " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceRole.delete(id)).build();

  }

}
