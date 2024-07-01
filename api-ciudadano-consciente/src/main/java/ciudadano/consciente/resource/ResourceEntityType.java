package ciudadano.consciente.resource;

import ciudadano.consciente.dto.DTOCreateEntityType;
import ciudadano.consciente.dto.DTOEntityType;
import ciudadano.consciente.dto.DTOUpdateEntityType;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceEntityType;
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

@Tag(name = "Entity Types Resource")
@Path("entity-types")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ResourceEntityType {

    final String PATH_BASE_RESOURCE = "/entities/";

    @Inject
    Logger audit;

    @Inject
    ServiceEntityType serviceEntityType;

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    @GET
    @Operation(summary = "Retrieve all categories of Entities")
    @APIResponse(
            responseCode = "200",
            description = "Categories of Entities successfully retrieved"
    )
    public Response getAll() {

        audit.debug("Retrieving all categories of Entities...");
        return Response.ok(serviceEntityType.getAll()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Retrieve a category of EntityType.")
    @APIResponse(
            responseCode = "200",
            description = "Category of EntityType successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve EntityType. Verify 'Warning' Header."
    )
    public Response get(@PathParam("id") Integer id) {

        audit.debug("Getting Level " + id + "...");
        return Response.ok(serviceEntityType.get(id)).build();

    }

    @POST
    @Operation(summary = "Create a category of EntityType.")
    @APIResponse(
            responseCode = "201",
            description = "Category of EntityType successfully created."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to create category of EntityType. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to create category of EntityType. Verify 'Warning' Header."
    )
    public Response create(DTOCreateEntityType dtoCreateEntityType) {

        if(dtoCreateEntityType == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        String title = dtoCreateEntityType.getTitle();
        if(!utilityVerifyRequestField.isValidField(title)) {
            throw new HttpBadRequestException("Title field required.");
        }

        audit.debug("Creating category of EntityType...");
        DTOEntityType dtoEntityType = serviceEntityType.create(dtoCreateEntityType);

        audit.debug("Creating URI...");
        URI uri = URI.create(PATH_BASE_RESOURCE + dtoEntityType.getEntityTypeId());

        return Response.created(uri)
                .entity(dtoEntityType)
                .build();

    }

    @PATCH
    @Path("{id}")
    @Operation(summary = "Update a category of EntityType.")
    @APIResponse(
            responseCode = "200",
            description = "Category of EntityType successfully updated."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to update category of Entities. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to update category of Entities. Verify 'Warning' Header."
    )
    public Response update(@PathParam("id") Integer id,
                           DTOUpdateEntityType dtoUpdateEntityType) {

        if(dtoUpdateEntityType == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(id.compareTo(dtoUpdateEntityType.getEntityTypeId()) != 0) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        String title = dtoUpdateEntityType.getTitle();
        if(!utilityVerifyRequestField.isValidField(title)) {
            throw new HttpBadRequestException("No updates to make.");
        }

        audit.debug("Updating category of EntityType" + id + "...");
        return Response.ok(serviceEntityType.update(id, dtoUpdateEntityType)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Delete a category of Entities.")
    @APIResponse(
            responseCode = "200",
            description = "Category of Entities successfully deleted."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to delete category of Entities. Verify 'Warning' Header."
    )
    public Response delete(@PathParam("id") Integer id) {

        audit.debug("Deleting Level " + id + "...");
        return Response.ok(serviceEntityType.delete(id)).build();

    }
    
}
