package ciudadano.consciente.resource;

import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceLevel;
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

@Tag(name = "Level Resource")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("levels/")
public class ResourceLevel {

    final String PATH_BASE_RESOURCE = "/levels/";

    @Inject
    ServiceLevel serviceLevel;

    @Inject
    Logger audit;

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    @GET
    @Operation(summary = "Retrieve all Levels.")
    @APIResponse(
            responseCode = "200",
            description = "Levels successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve all Levels. Verify 'Warning' Header."
    )
    public Response getAll() {

        audit.debug("Getting all Levels...");
        return Response.ok(serviceLevel.getAll()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Retrieve a  Level by its ID.")
    @APIResponse(
            responseCode = "200",
            description = "Level successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve Level. Verify 'Warning' Header."
    )
    public Response get(@PathParam("id") Integer id) {

        audit.debug("Getting Level " + id + "...");
        return Response.ok(serviceLevel.get(id)).build();

    }

    @POST
    @Operation(summary = "Create a Level.")
    @APIResponse(
            responseCode = "201",
            description = "Level successfully created."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to create Level. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to create Level. Verify 'Warning' Header."
    )
    public Response create(DTOCreateLevel dtoCreateLevel) {

        if(dtoCreateLevel == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        String name = dtoCreateLevel.getName();
        if(!utilityVerifyRequestField.isValidField(name)) {
            throw new HttpBadRequestException("The name is required.");
        }

        audit.debug("Creating Level...");
        DTOLevel level = serviceLevel.create(dtoCreateLevel);

        audit.debug("Creating URI...");
        URI uri = URI.create(PATH_BASE_RESOURCE + level.getLevelId());

        return Response.created(uri)
                .entity(level)
                .build();

    }

    @PATCH
    @Path("{id}")
    @Operation(summary = "Update a Level.")
    @APIResponse(
            responseCode = "200",
            description = "Level successfully updated."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to update Level. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to update Level. Verify 'Warning' Header."
    )
    public Response update(@PathParam("id") Integer id,
                               DTOUpdateLevel dtoUpdateLevel) {

        if(dtoUpdateLevel == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(id != dtoUpdateLevel.getLevelId()) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        String name = dtoUpdateLevel.getName();
        Integer organization = dtoUpdateLevel.getOrganization();
        Integer parent = dtoUpdateLevel.getParent();
        String description = dtoUpdateLevel.getDescription();
        if(!utilityVerifyRequestField.isValidField(name) &&
                !utilityVerifyRequestField.isValidField(parent) &&
                !utilityVerifyRequestField.isValidField(organization) &&
                !utilityVerifyRequestField.isValidField(description)) {
            throw new HttpBadRequestException("No updates to make.");
        }

        audit.debug("Updating Level" + id + "...");
        return Response.ok(serviceLevel.update(id, dtoUpdateLevel)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Delete a  Level by its ID.")
    @APIResponse(
            responseCode = "200",
            description = "Level successfully deleted."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to delete Level. Verify 'Warning' Header."
    )
    public Response delete(@PathParam("id") Integer id) {

        audit.debug("Deleting Level " + id + "...");
        return Response.ok(serviceLevel.delete(id)).build();

    }

    // USER-ROLE HANDLING IN LEVEL

    @Deprecated
    @GET
    @Path("{id}/users")
    @Operation(summary = "Retrieve all the Users and Roles in a  Level.")
    @APIResponse(
            responseCode = "200",
            description = "Roles of User in Level successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve Roles of User in Level. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to retrieve Roles of User in Level. Verify 'Warning' Header."
    )
    public Response getAll(@PathParam("id") Integer id) {

        audit.debug("Getting all the UserRole of Level " + id + "...");
        return Response.ok(serviceLevel.getUserRoleLevel(id)).build();

    }

    @Deprecated
    @GET
    @Path("{id}/users/{user}")
    @Operation(summary = "Retrieve all the Roles of User in a  Level.")
    @APIResponse(
            responseCode = "200",
            description = "Roles of User in Level successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve Roles of User in Level. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to retrieve Roles of User in Level. Verify 'Warning' Header."
    )
    public Response getAllRolesOfUserInLevel(@PathParam("id") Integer idLevel,
                                             @PathParam("user") Integer idUser) {

        audit.debug("Getting all the Roles of User (" + idUser + ") in Level " + idLevel + "...");
        return Response.ok(serviceLevel.getAllRolesInLevelByUser(idLevel, idUser)).build();

    }

    @GET
    @Path("{id}/users/roles")
    @Operation(summary = "Retrieve Users with Role in Level.")
    @APIResponse(
            responseCode = "200",
            description = "Users in Level with Role successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve User with Role in Level. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to retrieve User with Role in Level. Verify 'Warning' Header."
    )
    public Response getUsersWithRole(@PathParam("id") Integer idLevel,
                                        @QueryParam("role") Integer idRole,
                                        @QueryParam("user") Integer idUser) {

        if(idRole == null && idUser == null) {
            audit.debug("Getting all the Users with Roles in Level " + idLevel + "...");
            return Response.ok(serviceLevel.getAllUsersWithRoleByLevel(idLevel)).build();
        }

        if(idRole == null) {
            audit.debug("Getting all Roles of User(" + idUser + ") in Level " + idLevel + "...");
            return Response.ok(serviceLevel.getAllRolesInLevelByUser(idLevel, idUser)).build();
        }

        if(idUser == null) {
            audit.debug("Getting all the Users with Role(" + idRole + ") in Level " + idLevel + "...");
            return Response.ok(serviceLevel.getAllUsersWithRoleInLevel(idLevel, idRole)).build();
        }

        audit.debug("Getting User(" + idUser + ") with Role (" + idRole + ") in Level " + idLevel + "...");
        return Response.ok(serviceLevel.getUserRoleLevel(idLevel, idUser, idRole)).build();

    }

    @Deprecated
    @GET
    @Path("/{id}/users/{user}/roles/{role}")
    @Operation( summary = "Retrieve a User Role in Level.")
    @APIResponse(
            responseCode = "200",
            description = "UserRoleLevel successfully retrieved."
    )
    @APIResponse(
            responseCode = "404",
            description = "User doesn't have that Role in Level."
    )
    public Response getUserRoleLevel(@PathParam("id") Integer idLevel,
                                     @PathParam("user") Integer idUser,
                                     @PathParam("role") Integer idRole) {

        audit.debug("Getting User(" + idUser + ")Role(" + idRole + ")Level(" + idUser + ") " + idLevel + "...");
        return Response.ok(serviceLevel.getUserRoleLevel(idLevel, idUser, idRole)).build();

    }

    @POST
    @Path("{id}/users/roles") // /{user}/roles/{role}")
    @Operation(summary = "Assign Role to User in Level.")
    @APIResponse(
            responseCode = "201",
            description = "Role successfully assign to User in Level."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to Assign Role to User in Level. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to Assign Role to User in Level. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to Assign Role to User in Level. Verify 'Warning' Header."
    )
    public Response assignRole(@PathParam("id") Integer idLevel,
                               //@PathParam("user") Integer idUser,
                               //@PathParam("role") Integer idRole,
                               DTOAssignRoleToUserLevel dtoAssignRoleToUserLevel) {

        if(dtoAssignRoleToUserLevel == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        Integer user = dtoAssignRoleToUserLevel.getUser();
        Integer level = dtoAssignRoleToUserLevel.getLevel();
        Integer role = dtoAssignRoleToUserLevel.getRole();
        if(!utilityVerifyRequestField.isValidField(user) ||
                !utilityVerifyRequestField.isValidField(level) ||
                !utilityVerifyRequestField.isValidField(role)) {
            throw new HttpBadRequestException("All fields required.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(idLevel != dtoAssignRoleToUserLevel.getLevel()) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same for Level.");
        }
        /*if(idUser != dtoAssignRoleToUserLevel.getUser()) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same for User.");
        }
        if(idRole != dtoAssignRoleToUserLevel.getRole()) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same for Role.");
        }*/

        audit.debug("Assigning Role" + role
                + " to User " + user
                + " in Level " + idLevel + "...");
        DTOUserRoleLevel dtoUserRoleLevel = serviceLevel.assignRoleToUserInLevel(idLevel, user, role);

        audit.debug("Creating URI...");
        URI uri = URI.create(PATH_BASE_RESOURCE + dtoUserRoleLevel.getLevel() +
                "/users/" + dtoUserRoleLevel.getUser() +
                "/roles/" + dtoUserRoleLevel.getRole());

        return Response.created(uri).entity(dtoUserRoleLevel).build();

    }

    @PATCH
    @Path("{id}/users/roles") // {user}/roles/{role}")
    @Operation(summary = "Update Role of User in Level.")
    @APIResponse(
            responseCode = "200",
            description = "Role successfully updated to User in Level."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to update Role to User in Level. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to update Role to User in Level. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to update Role to User in Level. Verify 'Warning' Header."
    )
    public Response updateRoleOfUserInLevel(@PathParam("id") Integer idLevel,
                               //@PathParam("user") Integer idUser,
                               //@PathParam("role") Integer idRole,
                               DTOUpdateRoleUserLevel dtoUpdateRoleUserLevel) {

        if(dtoUpdateRoleUserLevel == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        Integer user = dtoUpdateRoleUserLevel.getUser();
        Integer level = dtoUpdateRoleUserLevel.getLevel();
        Integer role = dtoUpdateRoleUserLevel.getRole();
        Integer newRole = dtoUpdateRoleUserLevel.getRole();
        if(!utilityVerifyRequestField.isValidField(user) ||
                !utilityVerifyRequestField.isValidField(level) ||
                !utilityVerifyRequestField.isValidField(newRole)) {
            throw new HttpBadRequestException("All fields required.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(idLevel != level) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same for Level.");
        }
        /*if(idUser != user) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same for User.");
        }
        if(idRole == newRole) {
            throw new HttpBadRequestException("Body ID and Path ID must reflect reflect the change for Role.");
        }*/

        audit.debug("Updating Role" + newRole
                + " of User " + user
                + " in Level " + idLevel + "...");
        DTOUserRoleLevel dtoUserRoleLevel = serviceLevel.updateRoleOfUserInLevel(idLevel, user, role, newRole);

        return Response.ok(dtoUserRoleLevel).build();

    }

    @DELETE
    @Path("{id}/users/{user}")
    @Operation( summary = "Delete all Roles of a User in a Level.")
    @APIResponse(
            responseCode = "200",
            description = "Roles of User successfully deleted in Level."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to delete Roles of User in Level. Verify 'Warning' Header."
    )
    public Response deleteAllRolesOfUserInLevel(@PathParam("id") Integer idLevel,
                                        @PathParam("user") Integer idUser) {

        audit.debug("Deleting all Roles of User(" + idUser + ") in Level (" + idLevel + ")...");
        return Response.ok(serviceLevel.deleteAllRolesOfUserInLevel(idLevel, idUser)).build();

    }

    @DELETE
    @Path("{id}/users/{user}/roles/{role}")
    @Operation( summary = "Delete a Role of a User in a Level.")
    @APIResponse(
            responseCode = "200",
            description = "Role of User successfully deleted in Level."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to delete Role of User in Level. Verify 'Warning' Header."
    )
    public Response deleteUserRoleLevel(@PathParam("id") Integer idLevel,
                                        @PathParam("user") Integer idUser,
                                        @PathParam("role") Integer idRole) {

        audit.debug("Deleting User(" + idUser + ")Role(" + idRole + ")Level(" + idUser + ") " + idLevel + "...");
        return Response.ok(serviceLevel.deleteUserRoleLevel(idLevel, idUser, idRole)).build();

    }

}
