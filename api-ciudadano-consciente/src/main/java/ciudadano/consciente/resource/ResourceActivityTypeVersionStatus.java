package ciudadano.consciente.resource;

import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.model.Organization;
import ciudadano.consciente.service.ServiceActivityTypeVersionStatus;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
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
@Tag(name = "Activity Type Version Status Resource")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("activity-type-version-status")
public class ResourceActivityTypeVersionStatus {

    final String PATH_BASE_RESOURCE = "/activity-type-version-status/";

    @Inject
    Logger audit;

    @Inject
    ServiceActivityTypeVersionStatus serviceActivityTypeVersionStatus;

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    @GET
    @Operation(summary = "Retrieve all categories of Activity Type Version Status")
    @APIResponse(
            responseCode = "200",
            description = "Categories of Activity Type Version Status successfully retrieved",
            content = @Content( schema = @Schema(implementation = DTOActivityTypeVersionStatus.class))
    )
    public RestResponse<List<DTOActivityTypeVersionStatus>> getAll() {

        audit.debug("Retrieving all categories of Activity Type Version Status...");
        return RestResponse.ResponseBuilder.ok(serviceActivityTypeVersionStatus.getAll()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Retrieve a category of Activity Type Version Status.")
    @APIResponse(
            responseCode = "200",
            description = "Category of Activity Type Version Status successfully retrieved.",
            content = @Content( schema = @Schema(implementation = DTOActivityTypeVersionStatus.class))
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve Activity Type Version Status. Verify 'Warning' Header."
    )
    public RestResponse<DTOActivityTypeVersionStatus> get(@PathParam("id") Integer id) {

        audit.debug("Getting Level " + id + "...");
        return RestResponse.ResponseBuilder.ok(serviceActivityTypeVersionStatus.get(id)).build();

    }

    @POST
    @Operation(summary = "Create a category of Activity Type Version Status.")
    @APIResponse(
            responseCode = "201",
            description = "Category of Activity Type Version Status successfully created.",
            content = @Content( schema = @Schema(implementation = DTOActivityTypeVersionStatus.class))
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to create category of Activity Type Version Status. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to create category of Activity Type Version Status. Verify 'Warning' Header."
    )
    public RestResponse<DTOActivityTypeVersionStatus> create(DTOCreateActivityTypeVersionStatus dtoCreateActivityTypeVersionStatus) {

        if(dtoCreateActivityTypeVersionStatus == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        String title = dtoCreateActivityTypeVersionStatus.getTitle();
        String description = dtoCreateActivityTypeVersionStatus.getDescription();
        if(!utilityVerifyRequestField.isValidField(title) &&
            !utilityVerifyRequestField.isValidField(description)) {
            throw new HttpBadRequestException("All fields required.");
        }

        audit.debug("Creating category of Activity Type Version Status...");
        DTOActivityTypeVersionStatus dtoActivityTypeVersionStatus = serviceActivityTypeVersionStatus.create(dtoCreateActivityTypeVersionStatus);

        audit.debug("Creating URI...");
        URI uri = URI.create(PATH_BASE_RESOURCE + dtoActivityTypeVersionStatus.getAnswersStatusId());

        return RestResponse.ResponseBuilder
                .create(RestResponse.Status.CREATED, dtoActivityTypeVersionStatus)
                .location(uri)
                .build();

    }

    @PATCH
    @Path("{id}")
    @Operation(summary = "Update a category of Activity Type Version Status.")
    @APIResponse(
            responseCode = "200",
            description = "Category of Activity Type Version Status successfully updated.",
            content = @Content( schema = @Schema(implementation = DTOActivityTypeVersionStatus.class))
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to update category of Activity Type Version Status. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to update category of Activity Type Version Status. Verify 'Warning' Header."
    )
    public RestResponse<DTOActivityTypeVersionStatus> update(@PathParam("id") Integer id,
                           DTOUpdateActivityTypeVersionStatus dtoUpdateActivityTypeVersionStatus) {

        if(dtoUpdateActivityTypeVersionStatus == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(id.compareTo(dtoUpdateActivityTypeVersionStatus.getAnswersStatusId()) != 0) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        String title = dtoUpdateActivityTypeVersionStatus.getTitle();
        String description = dtoUpdateActivityTypeVersionStatus.getDescription();
        if(!utilityVerifyRequestField.isValidField(title) &&
                !utilityVerifyRequestField.isValidField(description)) {
            throw new HttpBadRequestException("No updates to make.");
        }

        audit.debug("Updating category of Activity Type Version Status " + id + "...");
        return RestResponse.ResponseBuilder.ok(serviceActivityTypeVersionStatus.update(id, dtoUpdateActivityTypeVersionStatus)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Delete a category of Activity Type Version Status.")
    @APIResponse(
            responseCode = "200",
            description = "Category of Activity Type Version Status successfully deleted.",
            content = @Content( schema = @Schema(implementation = DTOActivityTypeVersionStatus.class))
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to delete category of Activity Type Version Status. Verify 'Warning' Header."
    )
    public RestResponse<DTOActivityTypeVersionStatus> delete(@PathParam("id") Integer id) {

        audit.debug("Deleting Level " + id + "...");
        return RestResponse.ResponseBuilder.ok(serviceActivityTypeVersionStatus.delete(id)).build();

    }

}
