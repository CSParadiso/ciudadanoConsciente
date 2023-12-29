
package ciudadano.consciente.resource;

import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceOrganization;
import ciudadano.consciente.dto.*;
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

@Tag(name = "Organization Resource")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("organizations")
public class ResourceOrganization {

    final String PATH_BASE_RESOURCE = "/organizations/";

    @Inject
    ServiceOrganization serviceOrganization;

    @Inject
    Logger audit;

    @GET
    @Operation( summary = "Retrieve all Organizations.")
    @APIResponse(
            responseCode = "200",
            description = "Organizations retrieved successfully."
    )
    public Response getAll() {

        audit.debug("Getting all Organizations...");
        return Response.ok(serviceOrganization.getAll()).build();

    }

    @GET
    @Path("{id}/")
    @Operation( summary = "Retrieve an specific Organization by its ID.")
    @APIResponse(
            responseCode = "200",
            description = "Organization successfully retrieved."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to retrieve Organization. Verify 'Warning' Header."
    )
    public Response get(@PathParam("id") Integer id) {

        audit.debug("Getting Organization " + id + "...");
        return Response.ok(serviceOrganization.get(id)).build();

    }

    @POST
    @Operation( summary = "Create a new Organization.")
    @APIResponse(
            responseCode = "201",
            description = "Organization successfully created."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to create new Organization. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to create new Organization. Verify 'Warning' Header."
    )
    public Response create(DTOCreateOrganization dtoCreateOrganization) {

        audit.debug("Creating Organization...");
        DTOOrganization organization = serviceOrganization.create(dtoCreateOrganization);

        audit.debug("Creating URI...");
        URI uri = URI.create(PATH_BASE_RESOURCE + organization.getOrganizationId());

        return Response.created(uri).entity(organization).build();

    }

    @PATCH
    @Path("{id}")
    @Operation( summary = "Update Organization.")
    @APIResponse(
            responseCode = "200",
            description = "Organization successfully updated."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to update new Organization. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to update new Organization. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to update new Organization. Verify 'Warning' Header."
    )
    public Response update(@PathParam("id") Integer id,
                           DTOUpdateOrganization dtoUpdateOrganization) {

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(id != dtoUpdateOrganization.getOrganizationId()) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        audit.debug("Updating Organization" + id + "...");
        return Response.ok(serviceOrganization.update(id, dtoUpdateOrganization)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation( summary = "Delete an Organization.")
    @APIResponse(
            responseCode = "200",
            description = "Organization successfully deleted."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to delete new Organization. Verify 'Warning' Header."
    )
    public Response delete(@PathParam("id") Integer id) {

        audit.debug("Deleting Organization " + id + "...");
        serviceOrganization.delete(id);

        return Response.ok().build();

    }

    // ROLE HANDLING IN ORGANIZATIONS

    @POST
    @Path("{id}/roles")
    @Operation(summary = "Assign Role to User in Organization.")
    @APIResponse(
            responseCode = "201",
            description = "Role successfully assign to User in Organization."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to Assign Role to User in Organization. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to Assign Role to User in Organization. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to Assign Role to User in Organization. Verify 'Warning' Header."
    )
    public Response assignRole(@PathParam("id") Integer id,
                               DTOAssingRoleToUserOrganization dtoAssingRoleToUserOrganization) {

        final String PATH_BASE_USER_ROL_ORGANIZATION = "/user-rol-organization/";

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(id != dtoAssingRoleToUserOrganization.getOrganization()) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        audit.debug("Assigning Role" + dtoAssingRoleToUserOrganization.getRole()
                + " to User " + dtoAssingRoleToUserOrganization.getUser()
                + " in Organization " + dtoAssingRoleToUserOrganization.getOrganization() + "...");
        DTOUserRoleOrganization dtoUserRoleOrganization = serviceOrganization.assignRole(dtoAssingRoleToUserOrganization);

        audit.debug("Creating URI...");
        URI uri = URI.create(PATH_BASE_USER_ROL_ORGANIZATION + dtoUserRoleOrganization.getOrganization());

        return Response.created(uri).entity(dtoUserRoleOrganization).build();

    }

    @GET
    @Path("{id}/roles")
    @Operation(summary = "Retrieve all the UserRole in Organization.")
    @APIResponse(
            responseCode = "200",
            description = "Roles of User in Organization successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve Roles of User in Organization. Verify 'Warning' Header."
    )
    public Response getAll(@PathParam("id") Integer id) {

        audit.debug("Getting all the UserRole of Organization " + id + "...");
        return Response.ok(serviceOrganization.getUserRoleOrganization(id)).build();

    }

}

