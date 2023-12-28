package ciudadano.consciente.resource;

import ciudadano.consciente.dto.DTOActivityType;
import ciudadano.consciente.dto.DTOUpdateActivityType;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceActivityType;
import ciudadano.consciente.dto.DTOCreateActivityType;
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

@Tag(name = "Activity Type Resource")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("activity-type")
public class ResourceActivityType {

    final String BASE_PATH_RESOURCE = "/activity-type/";

    @Inject
    ServiceActivityType serviceActivityType;

    @Inject
    Logger audit;

    @POST
    @Operation(summary = "Create an Activity Type.")
    @APIResponse(
            responseCode = "201",
            description = "Activity Type successfully created."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to create Activity Type. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to create Activity Type. Verify 'Warning' Header."
    )
    public Response create(DTOCreateActivityType dtoCreateActivityType) {

        audit.debug("Creating Activity Type...");
        DTOActivityType activityType = serviceActivityType.create(dtoCreateActivityType);

        audit.debug("Creating URI...");
        URI uri = URI.create(BASE_PATH_RESOURCE + activityType.getActivityTypeId());

        return Response.created(uri)
                .entity(activityType)
                .build();

    }

    @GET
    @Operation(summary = "Retrieve all Activity Types.")
    @APIResponse(
            responseCode = "200",
            description = "Activity Types successfully retrieved."
    )
    public Response getAll() {

        audit.debug("Getting all the Activity Types...");
        return Response.ok(serviceActivityType.getAll()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Retrieve a specific Activity Type by its ID.")
    @APIResponse(
            responseCode = "200",
            description = "Activity Types successfully retrieved."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to retrieve Activity Type. Verify 'Warning' Header."
    )
    public Response get(@PathParam("id") Integer id) {

        audit.debug("Getting Activity Type " + id + "...");
        return Response.ok(serviceActivityType.get(id)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Delete a specific Activity Type by its ID.")
    @APIResponse(
            responseCode = "200",
            description = "Activity Types successfully deleted."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to delete Activity Type. Verify 'Warning' Header."
    )
    public Response delete(@PathParam("id") Integer id) {

        audit.debug("Deleting Activity Type " + id + "...");
        serviceActivityType.delete(id);
        return Response.ok().build();

    }

    @PATCH
    @Path("{id}")
    @Operation(summary = "Update an Activity Type.")
    @APIResponse(
            responseCode = "200",
            description = "Activity Types successfully updated."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to update Activity Type. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to delete Activity Type. Verify 'Warning' Header."
    )
    public Response update(@PathParam("id") Integer id,
                               DTOUpdateActivityType dTOUpdateActivityType) {

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(id != dTOUpdateActivityType.getActivityTypeId()) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        audit.debug("Updating Activity Type " + id + "...");
        return Response.ok(serviceActivityType.update(id, dTOUpdateActivityType)).build();

    }


}
