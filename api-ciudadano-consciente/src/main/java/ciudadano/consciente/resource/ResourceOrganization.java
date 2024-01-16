
package ciudadano.consciente.resource;

import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceOrganization;
import ciudadano.consciente.dto.*;
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

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

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

        if(dtoCreateOrganization == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        String email = dtoCreateOrganization.getEmail();
        String name = dtoCreateOrganization.getName();
        if(!utilityVerifyRequestField.isValidField(email) ||
                !utilityVerifyRequestField.isValidField(name)) {
            throw new HttpBadRequestException("Email and name required.");
        }

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

        if(dtoUpdateOrganization == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        String email = dtoUpdateOrganization.getEmail();
        String name = dtoUpdateOrganization.getName();
        String description = dtoUpdateOrganization.getDescription();
        if(!utilityVerifyRequestField.isValidField(email) &&
                !utilityVerifyRequestField.isValidField(name) &&
                !utilityVerifyRequestField.isValidField(description)) {
            throw new HttpBadRequestException("No updates to make.");
        }

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
        return Response.ok(serviceOrganization.delete(id)).build();

    }

    // ROLE HANDLING IN ORGANIZATIONS

    @Deprecated
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
    public Response assignRoleToUserInLevel(@PathParam("id") Integer id,
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
    @Path("{id}/users/roles")
    @Operation(summary = "Retrieve Users with Role in Organization.")
    @APIResponse(
            responseCode = "200",
            description = "Users in Organization with Role successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve User with Role in Organization. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to retrieve User with Role in Organization. Verify 'Warning' Header."
    )
    public Response getUsersWithRole(@PathParam("id") Integer idOrganization,
                                     @QueryParam("role") Integer idRole,
                                     @QueryParam("user") Integer idUser) {

        if(idRole == null && idUser == null) {
            audit.debug("Getting all the Users with Roles in Organization " + idOrganization + "...");
            return Response.ok(serviceOrganization.getAllUsersWithRoleByOrganization(idOrganization)).build();
        }

        if(idRole == null) {
            audit.debug("Getting all Roles of User(" + idUser + ") in Organization " + idOrganization + "...");
            return Response.ok(serviceOrganization.getAllRolesInOrganizationByUser(idOrganization, idUser)).build();
        }

        if(idUser == null) {
            audit.debug("Getting all the Users with Role(" + idRole + ") in Organization " + idOrganization + "...");
            return Response.ok(serviceOrganization.getAllUsersWithRoleInOrganization(idOrganization, idRole)).build();
        }

        audit.debug("Getting User(" + idUser + ") with Role (" + idRole + ") in Organization " + idOrganization + "...");
        return Response.ok(serviceOrganization.getUserRoleOrganization(idOrganization, idUser, idRole)).build();

    }

    @POST
    @Path("{id}/users/roles") // /{user}/roles/{role}")
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
    public Response assignRole(@PathParam("id") Integer idOrganization,
                               //@PathParam("user") Integer idUser,
                               //@PathParam("role") Integer idRole,
                               DTOAssingRoleToUserOrganization dtoAssignRoleToUserOrganization) {

        if(dtoAssignRoleToUserOrganization == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        Integer user = dtoAssignRoleToUserOrganization.getUser();
        Integer organization = dtoAssignRoleToUserOrganization.getOrganization();
        Integer role = dtoAssignRoleToUserOrganization.getRole();
        if(!utilityVerifyRequestField.isValidField(user) ||
                !utilityVerifyRequestField.isValidField(organization) ||
                !utilityVerifyRequestField.isValidField(role)) {
            throw new HttpBadRequestException("All fields required.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(idOrganization != dtoAssignRoleToUserOrganization.getOrganization()) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same for Organization.");
        }
        /*if(idUser != dtoAssignRoleToUserOrganization.getUser()) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same for User.");
        }
        if(idRole != dtoAssignRoleToUserOrganization.getRole()) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same for Role.");
        }*/

        audit.debug("Assigning Role" + role
                + " to User " + user
                + " in Organization " + idOrganization + "...");
        DTOUserRoleOrganization dtoUserRoleOrganization = serviceOrganization.assignRoleToUserInOrganization(idOrganization, user, role);

        audit.debug("Creating URI...");
        URI uri = URI.create(PATH_BASE_RESOURCE + dtoUserRoleOrganization.getOrganization() +
                "?users=" + dtoUserRoleOrganization.getUser() +
                "&roles=" + dtoUserRoleOrganization.getRole());

        return Response.created(uri).entity(dtoUserRoleOrganization).build();

    }

    @PATCH
    @Path("{id}/users/roles") // {user}/roles/{role}")
    @Operation(summary = "Update Role of User in Organization.")
    @APIResponse(
            responseCode = "200",
            description = "Role successfully updated to User in Organization."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to update Role to User in Organization. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to update Role to User in Organization. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to update Role to User in Organization. Verify 'Warning' Header."
    )
    public Response updateRoleOfUserInOrganization(@PathParam("id") Integer idOrganization,
                                            //@PathParam("user") Integer idUser,
                                            //@PathParam("role") Integer idRole,
                                            DTOUpdateRoleUserOrganization dtoUpdateRoleUserOrganization) {

        if(dtoUpdateRoleUserOrganization == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        Integer user = dtoUpdateRoleUserOrganization.getUser();
        Integer Organization = dtoUpdateRoleUserOrganization.getOrganization();
        Integer role = dtoUpdateRoleUserOrganization.getRole();
        Integer newRole = dtoUpdateRoleUserOrganization.getRole();
        if(!utilityVerifyRequestField.isValidField(user) ||
                !utilityVerifyRequestField.isValidField(Organization) ||
                !utilityVerifyRequestField.isValidField(newRole)) {
            throw new HttpBadRequestException("All fields required.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(idOrganization != Organization) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same for Organization.");
        }
        /*if(idUser != user) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same for User.");
        }
        if(idRole == newRole) {
            throw new HttpBadRequestException("Body ID and Path ID must reflect reflect the change for Role.");
        }*/

        audit.debug("Updating Role" + newRole
                + " of User " + user
                + " in Organization " + idOrganization + "...");
        DTOUserRoleOrganization dtoUserRoleOrganization = serviceOrganization.updateRoleOfUserInOrganization(idOrganization, user, role, newRole);

        return Response.ok(dtoUserRoleOrganization).build();

    }

    @DELETE
    @Path("{id}/users/{user}")
    @Operation( summary = "Delete all Roles of a User in a Organization.")
    @APIResponse(
            responseCode = "200",
            description = "Roles of User successfully deleted in Organization."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to delete Roles of User in Organization. Verify 'Warning' Header."
    )
    public Response deleteAllRolesOfUserInOrganization(@PathParam("id") Integer idOrganization,
                                                       @PathParam("user") Integer idUser) {

        audit.debug("Deleting all Roles of User(" + idUser + ") in Organization (" + idOrganization + ")...");
        return Response.ok(serviceOrganization.deleteAllRolesOfUserInOrganization(idOrganization, idUser)).build();

    }

    @DELETE
    @Path("{id}/users/{user}/roles/{role}")
    @Operation( summary = "Delete a Role of a User in a Organization.")
    @APIResponse(
            responseCode = "200",
            description = "Role of User successfully deleted in Organization."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to delete Role of User in Organization. Verify 'Warning' Header."
    )
    public Response deleteUserRoleOrganization(@PathParam("id") Integer idOrganization,
                                        @PathParam("user") Integer idUser,
                                        @PathParam("role") Integer idRole) {

        audit.debug("Deleting User(" + idUser + ")Role(" + idRole + ")Organization(" + idUser + ") " + idOrganization + "...");
        return Response.ok(serviceOrganization.deleteUserRoleOrganization(idOrganization, idUser, idRole)).build();

    }

    // VOTES HANDLING IN ORGANIZATIONS
    @POST
    @Path("{id}/votes") // /{user}/roles/{role}")
    @Operation(summary = "Vote Organization.")
    @APIResponse(
            responseCode = "201",
            description = "Organization successfully voted."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed vote Organization. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to vote Organization. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to vote Organization. Verify 'Warning' Header."
    )
    public Response vote(@PathParam("id") Integer idOrganization,
                               DTOCreateVote dtoCreateVote) {

        if(dtoCreateVote == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        Integer user = dtoCreateVote.getUser();
        Integer organization = dtoCreateVote.getEntity();
        if(!utilityVerifyRequestField.isValidField(user) ||
                !utilityVerifyRequestField.isValidField(organization)) {
            throw new HttpBadRequestException("All fields required.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(idOrganization != dtoCreateVote.getEntity()) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same for Organization.");
        }
        audit.debug("Vote of User " + user
                + " in Organization " + idOrganization + "...");
        DTOVote dtoVote = serviceOrganization.vote(idOrganization, user);

        audit.debug("Creating URI...");
        URI uri = URI.create(PATH_BASE_RESOURCE + dtoVote.getVoteId() +
                "?votes=" + dtoVote.getVoteId());

        return Response.created(uri).entity(dtoVote).build();

    }

}

