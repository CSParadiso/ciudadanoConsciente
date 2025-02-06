package ciudadano.consciente.resource;

import ciudadano.consciente.dto.DTOCreateNotificationTemplate;
import ciudadano.consciente.dto.DTONotificationTemplate;
import ciudadano.consciente.dto.DTOUpdateNotificationTemplate;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceNotificationTemplate;
import ciudadano.consciente.utility.UtilityAuthVerifier;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;

import java.net.URI;
import java.util.List;

//@RolesAllowed("Ciuco-Admin")
@Authenticated
@Tag(name = "Notification Template Resource")
@Path("notification-templates")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ResourceNotificationTemplate {

  final String PATH_BASE_RESOURCE = "/notification-templates/";

  @Inject
  Logger audit;

  @Inject
  ServiceNotificationTemplate serviceNotificationTemplate;

  @Inject
  UtilityVerifyRequestField utilityVerifyRequestField;

  @Inject
  SecurityIdentity securityIdentity;

  @Inject
  UtilityAuthVerifier utilityAuthVerifier;

  @GET
  @Operation(summary = "Retrieve all NotificationTemplates")
  @APIResponse(responseCode = "200", description = "NotificationTemplates successfully retrieved", content = @Content(schema = @Schema(implementation = DTONotificationTemplate.class)))
  public RestResponse<List<DTONotificationTemplate>> getAll() {

    utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});
    return RestResponse.ResponseBuilder.ok(serviceNotificationTemplate.getAll()).build();

  }

  @GET
  @Path("{id}")
  @Operation(summary = "Retrieve a NotificationTemplate.")
  @APIResponse(responseCode = "200", description = "NotificationTemplate successfully retrieved.", content = @Content(schema = @Schema(implementation = DTONotificationTemplate.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve NotificationTemplate. Verify 'Warning' Header.")
  public RestResponse<DTONotificationTemplate> get(@PathParam("id") Integer id) {

    utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});
    return RestResponse.ResponseBuilder.ok(serviceNotificationTemplate.get(id)).build();

  }

  @POST
  @Operation(summary = "Create a NotificationTemplate.")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @APIResponse(responseCode = "201", description = "NotificationTemplate successfully created.", content = @Content(schema = @Schema(implementation = DTONotificationTemplate.class)))
  @APIResponse(responseCode = "400", description = "Failed to create NotificationTemplate. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to create NotificationTemplate. Verify 'Warning' Header.")
  public RestResponse<DTONotificationTemplate> create(@RequestBody @Valid DTOCreateNotificationTemplate dtoCreateNotificationTemplate) {


    utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});

    DTONotificationTemplate dtoNotificationTemplate = serviceNotificationTemplate.create(dtoCreateNotificationTemplate);

    audit.debug("Creating URI...");
    URI uri = URI.create(PATH_BASE_RESOURCE + dtoNotificationTemplate.getNotificationTemplateId());

    return RestResponse.ResponseBuilder
        .create(RestResponse.Status.CREATED, dtoNotificationTemplate)
        .location(uri)
        .build();

  }

  @PATCH
  @Path("{id}")
  @Operation(summary = "Update a NotificationTemplate.")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @APIResponse(responseCode = "200", description = "NotificationTemplate successfully updated.", content = @Content(schema = @Schema(implementation = DTONotificationTemplate.class)))
  @APIResponse(responseCode = "400", description = "Failed to update NotificationTemplates. Verify 'Warning' Header.")
  @APIResponse(responseCode = "204", description = "Failed to update NotificationTemplates. Verify 'Warning' Header.")
  public RestResponse<DTONotificationTemplate> update(@PathParam("id") Integer id,
      @RequestBody @Valid DTOUpdateNotificationTemplate dtoUpdateNotificationTemplate) {

    if (dtoUpdateNotificationTemplate == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    audit.debug("Verifying if the ID of the Body and the Path are the same...");
    if (id.compareTo(dtoUpdateNotificationTemplate.getNotificationTemplateId()) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same.");
    }

    String title = dtoUpdateNotificationTemplate.getTitle();
    String template = new String(dtoUpdateNotificationTemplate.getTemplate());
    if (!utilityVerifyRequestField.isValidField(title) &&
    !utilityVerifyRequestField.isValidField(template)) {
      throw new HttpBadRequestException("No updates to make.");
    }

    utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});
    return RestResponse.ResponseBuilder.ok(serviceNotificationTemplate.update(id, dtoUpdateNotificationTemplate)).build();

  }

  @DELETE
  @Path("{id}")
  @Operation(summary = "Delete a NotificationTemplates.")
  @APIResponse(responseCode = "200", description = "NotificationTemplates successfully deleted.", content = @Content(schema = @Schema(implementation = DTONotificationTemplate.class)))
  @APIResponse(responseCode = "204", description = "Failed to delete NotificationTemplates. Verify 'Warning' Header.")
  public RestResponse<DTONotificationTemplate> delete(@PathParam("id") Integer id) {

    utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});
    return RestResponse.ResponseBuilder.ok(serviceNotificationTemplate.delete(id)).build();

  }

}
