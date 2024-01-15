package ciudadano.consciente.resource;

import ciudadano.consciente.dto.DTOCreateEntity;
import ciudadano.consciente.dto.DTOEntity;
import ciudadano.consciente.dto.DTOUpdateEntity;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceEntity;
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

@Tag(name = "Entity Resource")
@Path("entities")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class ResourceEntity {

    final String PATH_BASE_RESOURCE = "/entities/";

    @Inject
    Logger audit;

    @Inject
    ServiceEntity serviceEntity;

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
        return Response.ok(serviceEntity.getAll()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Retrieve a category of Entity.")
    @APIResponse(
            responseCode = "200",
            description = "Category of Entity successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve Level. Verify 'Warning' Header."
    )
    public Response get(@PathParam("id") Integer id) {

        audit.debug("Getting Level " + id + "...");
        return Response.ok(serviceEntity.get(id)).build();

    }

    @POST
    @Operation(summary = "Create a category of Entity.")
    @APIResponse(
            responseCode = "201",
            description = "Category of Entity successfully created."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to create category of Entity. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to create category of Entity. Verify 'Warning' Header."
    )
    public Response create(DTOCreateEntity dtoCreateEntity) {

        if(dtoCreateEntity == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        String title = dtoCreateEntity.getTitle();
        if(!utilityVerifyRequestField.isValidField(title)) {
            throw new HttpBadRequestException("Title field required.");
        }

        audit.debug("Creating category of Entity...");
        DTOEntity dtoEntity = serviceEntity.create(dtoCreateEntity);

        audit.debug("Creating URI...");
        URI uri = URI.create(PATH_BASE_RESOURCE + dtoEntity.getEntityId());

        return Response.created(uri)
                .entity(dtoEntity)
                .build();

    }

    @PATCH
    @Path("{id}")
    @Operation(summary = "Update a category of Entity.")
    @APIResponse(
            responseCode = "200",
            description = "Category of Entity successfully updated."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to update category of Entities. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to update category of Entities. Verify 'Warning' Header."
    )
    public Response update(@PathParam("id") Integer id,
                           DTOUpdateEntity dtoUpdateEntity) {

        if(dtoUpdateEntity == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(id != dtoUpdateEntity.getEntityId()) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        String title = dtoUpdateEntity.getTitle();
        if(!utilityVerifyRequestField.isValidField(title)) {
            throw new HttpBadRequestException("No updates to make.");
        }

        audit.debug("Updating category of Entity" + id + "...");
        return Response.ok(serviceEntity.update(id, dtoUpdateEntity)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Delete a category of Entities.")
    @APIResponse(
            responseCode = "200",
            description = "Category of Entities successfully deleted."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to delete category of Entities. Verify 'Warning' Header."
    )
    public Response delete(@PathParam("id") Integer id) {

        audit.debug("Deleting Level " + id + "...");
        return Response.ok(serviceEntity.delete(id)).build();

    }
    
}
