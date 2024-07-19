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
import jakarta.ws.rs.core.Response.ResponseBuilder;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;

import java.net.URI;
import java.util.List;

@Tag(name = "Level Resource")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("levels/")
public class ResourceLevel {

  final String PATH_BASE_RESOURCE = "/levels/";
  final String PATH_BASE_RESOURCE_VOTE = "/votes/";

  @Inject
  ServiceLevel serviceLevel;

  @Inject
  Logger audit;

  @Inject
  UtilityVerifyRequestField utilityVerifyRequestField;

  @GET
  @Operation(summary = "Retrieve all Levels.")
  @APIResponse(responseCode = "200", description = "Levels successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOLevel.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve all Levels. Verify 'Warning' Header.")
  public RestResponse<List<DTOLevel>> getAll() {

    audit.debug("Getting all Levels...");
    return RestResponse.ResponseBuilder.ok(serviceLevel.getAll()).build();

  }

  @GET
  @Path("paths")
  @Operation(summary = "Retrieve all Levels without parent.")
  @APIResponse(responseCode = "200", description = "Levels successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOLevelPathWithVotes.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve all Levels. Verify 'Warning' Header.")
  public RestResponse<List<DTOLevelPathWithVotes>> getAllPaths() {

    audit.debug("Getting all Levels without parent...");
    return RestResponse.ResponseBuilder.ok(serviceLevel.getAllPaths()).build();

  }

  @GET
  @Path("{id}")
  @Operation(summary = "Retrieve a  Level by its ID.")
  @APIResponse(responseCode = "200", description = "Level successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOLevel.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Level. Verify 'Warning' Header.")
  public RestResponse<DTOLevel> get(@PathParam("id") Integer id) {

    audit.debug("Getting Level " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceLevel.get(id)).build();

  }

  @GET
  @Path("{id}/childrens")
  @Operation(summary = "Retrieve all childrens of a Level by its ID.")
  @APIResponse(responseCode = "200", description = "Levels successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOLevelWithChildrens.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Level. Verify 'Warning' Header.")
  public RestResponse<List<DTOLevelWithChildrens>> getChildrens(@PathParam("id") Integer id) {

    audit.debug("Getting childrens of Level " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceLevel.getChildrens(id)).build();

  }

  @GET
  @Path("organizations/{organizationId}/paths")
  @Operation(summary = "Retrieve all Levels (without a parent) of an Organization by the Organization ID.")
  @APIResponse(responseCode = "200", description = "Levels successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOLevelPathWithVotes.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Levels. Verify 'Warning' Header.")
  public RestResponse<List<DTOLevelPathWithVotes>> getPathsByOrganization(@PathParam("organizationId") Integer id) {

    audit.debug("Getting paths of Organization " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceLevel.getPathsByOrganization(id)).build();

  }

  @GET
  @Path("organizations/{organizationId}/users/{userId}/roles/{roleId}")
  @Operation(summary = "Retrieve all Levels of an Organization where the user has a specific role.")
  @APIResponse(responseCode = "200", description = "Levels successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOLevel.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Levels. Verify 'Warning' Header.")
  public RestResponse<List<DTOLevel>> getLevelsByOrganizationUserAndRole(
      @PathParam("organizationId") Integer organizationId,
      @PathParam("userId") Integer userId,
      @PathParam("roleId") Integer roleId) {

    audit.debug("Getting Levels of and Organization by User and Role...");
    return RestResponse.ResponseBuilder
        .ok(serviceLevel.getLevelsByOrganizationUserAndRole(organizationId, userId, roleId)).build();

  }

  @GET
  @Path("/paths/favorites/users/{userId}")
  @Operation(summary = "Retrieve all Paths voted by a specific User.")
  @APIResponse(responseCode = "200", description = "Levels successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOLevelPath.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Levels. Verify 'Warning' Header.")
  public RestResponse<List<DTOLevelPath>> getPathsByUserFavorite(@PathParam("userId") Integer userId) {

    audit.debug("Getting favorite paths of User " + userId + "...");
    return RestResponse.ResponseBuilder.ok(serviceLevel.getPathsByUserFavorite(userId)).build();

  }

  @GET
  @Path("/paths/recently/users/{userId}")
  @Operation(summary = "Retrieve latest Paths used by a specific User.")
  @APIResponse(responseCode = "200", description = "Levels successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOLevelPathUsedRecentlyByUser.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Levels. Verify 'Warning' Header.")
  public RestResponse<List<DTOLevelPathUsedRecentlyByUser>> getPathsUsedByUserRecently(
      @PathParam("userId") Integer userId) {

    audit.debug("Getting favorite paths of User " + userId + "...");
    return RestResponse.ResponseBuilder.ok(serviceLevel.getPathsUsedByUserRecently(userId)).build();

  }

  @POST
  @Operation(summary = "Create a Level.")
  @APIResponse(responseCode = "201", description = "Level successfully created.", content = @Content(schema = @Schema(implementation = DTOLevel.class)))
  @APIResponse(responseCode = "400", description = "Failed to create Level. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to create Level. Verify 'Warning' Header.")
  public RestResponse<DTOLevel> create(DTOCreateLevel dtoCreateLevel) {

    if (dtoCreateLevel == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    String name = dtoCreateLevel.getName();
    Integer organization = dtoCreateLevel.getOrganization();
    if (!utilityVerifyRequestField.isValidField(name) ||
        !utilityVerifyRequestField.isValidField(organization)) {
      throw new HttpBadRequestException("Name and organization fields required.");
    }

    audit.debug("Creating Level...");
    DTOLevel level = serviceLevel.create(dtoCreateLevel);

    audit.debug("Creating URI...");
    URI uri = URI.create(PATH_BASE_RESOURCE + level.getLevelId());

    return RestResponse.ResponseBuilder
        .create(RestResponse.Status.CREATED, level)
        .location(uri)
        .build();

  }

  @PATCH
  @Path("{id}")
  @Operation(summary = "Update a Level.")
  @APIResponse(responseCode = "200", description = "Level successfully updated.", content = @Content(schema = @Schema(implementation = DTOLevel.class)))
  @APIResponse(responseCode = "400", description = "Failed to update Level. Verify 'Warning' Header.")
  @APIResponse(responseCode = "204", description = "Failed to update Level. Verify 'Warning' Header.")
  public RestResponse<DTOLevel> update(@PathParam("id") Integer id,
      DTOUpdateLevel dtoUpdateLevel) {

    if (dtoUpdateLevel == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    audit.debug("Verifying if the ID of the Body and the Path are the same...");
    if (id.compareTo(dtoUpdateLevel.getLevelId()) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same.");
    }

    String name = dtoUpdateLevel.getName();
    Integer organization = dtoUpdateLevel.getOrganization();
    Integer parent = dtoUpdateLevel.getParent();
    String description = dtoUpdateLevel.getDescription();
    if (!utilityVerifyRequestField.isValidField(name) &&
        !utilityVerifyRequestField.isValidField(parent) &&
        !utilityVerifyRequestField.isValidField(organization) &&
        !utilityVerifyRequestField.isValidField(description)) {
      throw new HttpBadRequestException("No updates to make.");
    }

    audit.debug("Updating Level" + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceLevel.update(id, dtoUpdateLevel)).build();

  }

  @DELETE
  @Path("{id}")
  @Operation(summary = "Delete a  Level by its ID.")
  @APIResponse(responseCode = "200", description = "Level successfully deleted.", content = @Content(schema = @Schema(implementation = DTOLevel.class)))
  @APIResponse(responseCode = "204", description = "Failed to delete Level. Verify 'Warning' Header.")
  public RestResponse<DTOLevel> delete(@PathParam("id") Integer id) {

    audit.debug("Deleting Level " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceLevel.delete(id)).build();

  }

  // USER-ROLE HANDLING IN LEVEL

  @Deprecated
  @GET
  @Path("{id}/users")
  @Operation(summary = "Retrieve all the Users and Roles in a  Level.")
  @APIResponse(responseCode = "200", description = "Roles of User in Level successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOUserRoleLevel.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Roles of User in Level. Verify 'Warning' Header.")
  public RestResponse<List<DTOUserRoleLevel>> getAll(@PathParam("id") Integer id) {

    audit.debug("Getting all the UserRole of Level " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceLevel.getUserRoleLevel(id)).build();

  }

  @Deprecated
  @GET
  @Path("{id}/users/{user}")
  @Operation(summary = "Retrieve all the Roles of User in a  Level.")
  @APIResponse(responseCode = "200", description = "Roles of User in Level successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOUserRoleLevel.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Roles of User in Level. Verify 'Warning' Header.")
  public RestResponse<List<DTOUserRoleLevel>> getAllRolesOfUserInLevel(@PathParam("id") Integer idLevel,
      @PathParam("user") Integer idUser) {

    audit.debug("Getting all the Roles of User (" + idUser + ") in Level " + idLevel + "...");
    return RestResponse.ResponseBuilder.ok(serviceLevel.getAllRolesInLevelByUser(idLevel, idUser)).build();

  }

  @GET
  @Path("{id}/users/roles")
  @Operation(summary = "Retrieve Users with Role in Level.")
  @APIResponse(responseCode = "200", description = "Users in Level with Role successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOUserRoleLevel.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve User with Role in Level. Verify 'Warning' Header.")
  public RestResponse<List<DTOUserRoleLevel>> getUsersWithRole(@PathParam("id") Integer idLevel,
      @QueryParam("role") Integer idRole,
      @QueryParam("user") Integer idUser) {

    if (idRole == null && idUser == null) {
      audit.debug("Getting all the Users with Roles in Level " + idLevel + "...");
      return RestResponse.ResponseBuilder.ok(serviceLevel.getAllUsersWithRoleByLevel(idLevel)).build();
    }

    if (idRole == null) {
      audit.debug("Getting all Roles of User(" + idUser + ") in Level " + idLevel + "...");
      return RestResponse.ResponseBuilder.ok(serviceLevel.getAllRolesInLevelByUser(idLevel, idUser)).build();
    }

    if (idUser == null) {
      audit.debug("Getting all the Users with Role(" + idRole + ") in Level " + idLevel + "...");
      return RestResponse.ResponseBuilder.ok(serviceLevel.getAllUsersWithRoleInLevel(idLevel, idRole)).build();
    }

    audit.debug("Getting User(" + idUser + ") with Role (" + idRole + ") in Level " + idLevel + "...");
    return RestResponse.ResponseBuilder.ok(List.of(serviceLevel.getUserRoleLevel(idLevel, idUser, idRole))).build();

  }

  @Deprecated
  @GET
  @Path("/{id}/users/{user}/roles/{role}")
  @Operation(summary = "Retrieve a User Role in Level.")
  @APIResponse(responseCode = "200", description = "UserRoleLevel successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOUserRoleLevel.class)))
  @APIResponse(responseCode = "204", description = "User doesn't have that Role in Level.")
  public RestResponse<DTOUserRoleLevel> getUserRoleLevel(@PathParam("id") Integer idLevel,
      @PathParam("user") Integer idUser,
      @PathParam("role") Integer idRole) {

    audit.debug("Getting User(" + idUser + ")Role(" + idRole + ")Level(" + idUser + ") " + idLevel + "...");
    return RestResponse.ResponseBuilder.ok(serviceLevel.getUserRoleLevel(idLevel, idUser, idRole)).build();

  }

  @POST
  @Path("{id}/users/roles") // /{user}/roles/{role}")
  @Operation(summary = "Assign Role to User in Level.")
  @APIResponse(responseCode = "201", description = "Role successfully assign to User in Level.", content = @Content(schema = @Schema(implementation = DTOUserRoleLevel.class)))
  @APIResponse(responseCode = "400", description = "Failed to Assign Role to User in Level. Verify 'Warning' Header.")
  @APIResponse(responseCode = "204", description = "Failed to Assign Role to User in Level. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to Assign Role to User in Level. Verify 'Warning' Header.")
  public RestResponse<DTOUserRoleLevel> assignRole(@PathParam("id") Integer idLevel,
      // @PathParam("user") Integer idUser,
      // @PathParam("role") Integer idRole,
      DTOAssignRoleToUserLevel dtoAssignRoleToUserLevel) {

    if (dtoAssignRoleToUserLevel == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    Integer user = dtoAssignRoleToUserLevel.getUser();
    Integer level = dtoAssignRoleToUserLevel.getLevel();
    Integer role = dtoAssignRoleToUserLevel.getRole();
    if (!utilityVerifyRequestField.isValidField(user.toString()) ||
        !utilityVerifyRequestField.isValidField(level.toString()) ||
        !utilityVerifyRequestField.isValidField(role.toString())) {
      throw new HttpBadRequestException("All fields required.");
    }

    audit.debug("Verifying if the ID of the Body and the Path are the same...");
    if (idLevel.compareTo(dtoAssignRoleToUserLevel.getLevel()) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same for Level.");
    }

    audit.debug("Assigning Role" + role
        + " to User " + user
        + " in Level " + idLevel + "...");
    DTOUserRoleLevel dtoUserRoleLevel = serviceLevel.assignRoleToUserInLevel(idLevel, user, role);

    audit.debug("Creating URI...");
    URI uri = URI.create(PATH_BASE_RESOURCE + dtoUserRoleLevel.getLevel() +
        "?users=" + dtoUserRoleLevel.getUser() +
        "&roles=" + dtoUserRoleLevel.getRole());

    return RestResponse.ResponseBuilder
        .create(RestResponse.Status.CREATED, dtoUserRoleLevel)
        .location(uri)
        .build();

  }

  @PATCH
  @Path("{id}/users/roles") // {user}/roles/{role}")
  @Operation(summary = "Update Role of User in Level.")
  @APIResponse(responseCode = "200", description = "Role successfully updated to User in Level.", content = @Content(schema = @Schema(implementation = DTOUserRoleLevel.class)))
  @APIResponse(responseCode = "400", description = "Failed to update Role to User in Level. Verify 'Warning' Header.")
  @APIResponse(responseCode = "204", description = "Failed to update Role to User in Level. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to update Role to User in Level. Verify 'Warning' Header.")
  public RestResponse<DTOUserRoleLevel> updateRoleOfUserInLevel(@PathParam("id") Integer idLevel,
      // @PathParam("user") Integer idUser,
      // @PathParam("role") Integer idRole,
      DTOUpdateRoleUserLevel dtoUpdateRoleUserLevel) {

    if (dtoUpdateRoleUserLevel == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    Integer user = dtoUpdateRoleUserLevel.getUser();
    Integer level = dtoUpdateRoleUserLevel.getLevel();
    Integer role = dtoUpdateRoleUserLevel.getRole();
    Integer newRole = dtoUpdateRoleUserLevel.getRole();
    if (!utilityVerifyRequestField.isValidField(user) ||
        !utilityVerifyRequestField.isValidField(level) ||
        !utilityVerifyRequestField.isValidField(newRole)) {
      throw new HttpBadRequestException("All fields required.");
    }

    audit.debug("Verifying if the ID of the Body and the Path are the same...");
    if (idLevel.compareTo(level) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same for Level.");
    }

    audit.debug("Updating Role" + newRole
        + " of User " + user
        + " in Level " + idLevel + "...");
    DTOUserRoleLevel dtoUserRoleLevel = serviceLevel.updateRoleOfUserInLevel(idLevel, user, role, newRole);

    return RestResponse.ResponseBuilder.ok(dtoUserRoleLevel).build();

  }

  @DELETE
  @Path("{id}/users/{user}")
  @Operation(summary = "Delete all Roles of a User in a Level.")
  @APIResponse(responseCode = "200", description = "Roles of User successfully deleted in Level.", content = @Content(schema = @Schema(implementation = DTOUserRoleLevel.class)))
  @APIResponse(responseCode = "204", description = "Failed to delete Roles of User in Level. Verify 'Warning' Header.")
  public RestResponse<List<DTOUserRoleLevel>> deleteAllRolesOfUserInLevel(@PathParam("id") Integer idLevel,
      @PathParam("user") Integer idUser) {

    audit.debug("Deleting all Roles of User(" + idUser + ") in Level (" + idLevel + ")...");
    return RestResponse.ResponseBuilder.ok(serviceLevel.deleteAllRolesOfUserInLevel(idLevel, idUser)).build();

  }

  @DELETE
  @Path("{id}/users/{user}/roles/{role}")
  @Operation(summary = "Delete a Role of a User in a Level.")
  @APIResponse(responseCode = "200", description = "Role of User successfully deleted in Level.", content = @Content(schema = @Schema(implementation = DTOUserRoleLevel.class)))
  @APIResponse(responseCode = "204", description = "Failed to delete Role of User in Level. Verify 'Warning' Header.")
  public RestResponse<DTOUserRoleLevel> deleteUserRoleLevel(@PathParam("id") Integer idLevel,
      @PathParam("user") Integer idUser,
      @PathParam("role") Integer idRole) {

    audit.debug("Deleting User(" + idUser + ")Role(" + idRole + ")Level(" + idUser + ") " + idLevel + "...");
    return RestResponse.ResponseBuilder.ok(serviceLevel.deleteUserRoleLevel(idLevel, idUser, idRole)).build();

  }

  // VOTES HANDLING IN LEVEL
  @Deprecated(since = "1.0.1")
  @POST
  @Path("{id}/votes")
  @Operation(summary = "Vote Level.")
  @APIResponse(responseCode = "201", description = "Level successfully voted.", content = @Content(schema = @Schema(implementation = DTOVote.class)))
  @APIResponse(responseCode = "400", description = "Failed to Vote Level. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to Vote Level. Verify 'Warning' Header.")
  public RestResponse<DTOVote> vote(@PathParam("id") Integer idLevel,
      DTOCreateVote dtoCreateVote) {

    if (dtoCreateVote == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    Integer user = dtoCreateVote.getUser();
    Integer level = dtoCreateVote.getEntity();
    if (!utilityVerifyRequestField.isValidField(user) ||
        !utilityVerifyRequestField.isValidField(level)) {
      throw new HttpBadRequestException("All fields required.");
    }

    audit.debug("Verifying if the ID of the Body and the Path are the same...");
    if (idLevel.compareTo(dtoCreateVote.getEntity()) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same for Level.");
    }
    audit.debug("Vote of User " + user
        + " in Level " + idLevel + "...");
    DTOVote dtoVote = serviceLevel.vote(idLevel, user);

    audit.debug("Creating URI...");
    URI uri = URI.create(PATH_BASE_RESOURCE_VOTE + dtoVote.getVoteId());

    return RestResponse.ResponseBuilder
        .create(RestResponse.Status.CREATED, dtoVote)
        .location(uri)
        .build();

  }

  @GET
  @Path("/votes")
  @Operation(summary = "Retrieve votes of Levels.")
  @APIResponse(responseCode = "200", description = "Votes of Levels successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOVotedEntity.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Votes of Levels. Verify 'Warning' Header.")
  public RestResponse<List<DTOVotedEntity>> getAllVotes() {

    audit.debug("Getting Levels Votes...");
    return RestResponse.ResponseBuilder.ok(serviceLevel.getAllVotes()).build();

  }

  @GET
  @Path("{id}/votes")
  @Operation(summary = "Retrieve votes of a Level.")
  @APIResponse(responseCode = "200", description = "Votes of Level successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOVotedEntity.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Votes of Level. Verify 'Warning' Header.")
  public RestResponse<List<DTOVotedEntity>> getVotes(@PathParam("id") Integer id) {

    audit.debug("Getting Level " + id + " Votes...");
    return RestResponse.ResponseBuilder.ok(serviceLevel.getVotes(id)).build();

  }

}
