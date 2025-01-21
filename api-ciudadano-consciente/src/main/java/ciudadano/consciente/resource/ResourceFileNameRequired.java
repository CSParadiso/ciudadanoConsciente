package ciudadano.consciente.resource;

import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceAnswer;
import ciudadano.consciente.service.ServiceFileNameRequired;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import io.quarkus.oidc.UserInfo;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
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

@Authenticated
@RequestScoped
@Tag(name = "File Name Required Resource")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("file-name-required")
public class ResourceFileNameRequired {

  final String PATH_BASE_RESOURCE = "/file-name-required/";

  @Inject
  Logger audit;

  @Inject
  ServiceFileNameRequired serviceFileNameRequired;

  @GET
  @Operation(summary = "Retrieve all File Name Required.")
  @APIResponse(responseCode = "200", description = "File Name Required successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOFileNameRequired.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve all File Name Required. Verify 'Warning' Header.")
  public RestResponse<List<DTOFileNameRequired>> getAll() {

    audit.debug("Getting all Answers...");
    return RestResponse.ResponseBuilder.ok(serviceFileNameRequired.getAll()).build();

  }

}
