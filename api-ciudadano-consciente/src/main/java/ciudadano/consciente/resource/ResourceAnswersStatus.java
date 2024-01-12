package ciudadano.consciente.resource;

import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceAnswersStatus;
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

@Tag(name = "Answers Status Resource")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("answers-status")
public class ResourceAnswersStatus {

    final String PATH_BASE_RESOURCE = "/answers-status/";

    @Inject
    Logger audit;

    @Inject
    ServiceAnswersStatus serviceAnswersStatus;

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    @GET
    @Operation(summary = "Retrieve all categories of Answers Status")
    @APIResponse(
            responseCode = "200",
            description = "Categories of Answers Status successfully retrieved"
    )
    public Response getAll() {

        audit.debug("Retrieving all categories of Answers Status...");
        return Response.ok(serviceAnswersStatus.getAll()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Retrieve a category of Answers Status.")
    @APIResponse(
            responseCode = "200",
            description = "Category of Answers Status successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve Level. Verify 'Warning' Header."
    )
    public Response get(@PathParam("id") Integer id) {

        audit.debug("Getting Level " + id + "...");
        return Response.ok(serviceAnswersStatus.get(id)).build();

    }

    @POST
    @Operation(summary = "Create a category of Answer Status.")
    @APIResponse(
            responseCode = "201",
            description = "Category of Answer Status successfully created."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to create category of Answer Status. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to create category of Answer Status. Verify 'Warning' Header."
    )
    public Response create(DTOCreateAnswersStatus dtoCreateAnswersStatus) {

        if(dtoCreateAnswersStatus == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        String title = dtoCreateAnswersStatus.getTitle();
        String description = dtoCreateAnswersStatus.getDescription();
        if(!utilityVerifyRequestField.isValidField(title) &&
            !utilityVerifyRequestField.isValidField(description)) {
            throw new HttpBadRequestException("All fields required.");
        }

        audit.debug("Creating category of Answers Status...");
        DTOAnswersStatus dtoAnswersStatus = serviceAnswersStatus.create(dtoCreateAnswersStatus);

        audit.debug("Creating URI...");
        URI uri = URI.create(PATH_BASE_RESOURCE + dtoAnswersStatus.getAnswersStatusId());

        return Response.created(uri)
                .entity(dtoAnswersStatus)
                .build();

    }

    @PATCH
    @Path("{id}")
    @Operation(summary = "Update a category of Answer Status.")
    @APIResponse(
            responseCode = "200",
            description = "Category of Answer Status successfully updated."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to update category of Answers Status. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to update category of Answers Status. Verify 'Warning' Header."
    )
    public Response update(@PathParam("id") Integer id,
                           DTOUpdateAnswersStatus dtoUpdateAnswersStatus) {

        if(dtoUpdateAnswersStatus == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(id != dtoUpdateAnswersStatus.getAnswersStatusId()) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        String title = dtoUpdateAnswersStatus.getTitle();
        String description = dtoUpdateAnswersStatus.getDescription();
        if(!utilityVerifyRequestField.isValidField(title) &&
                !utilityVerifyRequestField.isValidField(description)) {
            throw new HttpBadRequestException("No updates to make.");
        }

        audit.debug("Updating category of Answers Status" + id + "...");
        return Response.ok(serviceAnswersStatus.update(id, dtoUpdateAnswersStatus)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Delete a category of Answers Status.")
    @APIResponse(
            responseCode = "200",
            description = "Category of Answers Status successfully deleted."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to delete category of Answers Status. Verify 'Warning' Header."
    )
    public Response delete(@PathParam("id") Integer id) {

        audit.debug("Deleting Level " + id + "...");
        return Response.ok(serviceAnswersStatus.delete(id)).build();

    }

}
