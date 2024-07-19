
package ciudadano.consciente.resource;

import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceOrganization;
import ciudadano.consciente.dto.*;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
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

@Tag(name = "Organization Resource")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("organizations")
public class ResourceOrganization {

  final String PATH_BASE_RESOURCE = "/organizations/";
  final String PATH_BASE_RESOURCE_VOTE = "/votes/";

  @Inject
  ServiceOrganization serviceOrganization;

  @Inject
  Logger audit;

  @Inject
  UtilityVerifyRequestField utilityVerifyRequestField;

  @GET
  @Operation(summary = "Retrieve all Organizations.")
  @APIResponse(responseCode = "200", description = "Organizations retrieved successfully.", content = @Content(schema = @Schema(implementation = DTOOrganization.class)))
  public RestResponse<List<DTOOrganization>> getAll() {

    audit.debug("Getting all Organizations...");
    return RestResponse.ResponseBuilder.ok(serviceOrganization.getAll()).build();

  }

  @GET
  @Path("{id}/")
  @Operation(summary = "Retrieve an specific Organization by its ID.")
  @APIResponse(responseCode = "200", description = "Organization successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOOrganization.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Organization. Verify 'Warning' Header.")
  public RestResponse<DTOOrganization> get(@PathParam("id") Integer id) {

    audit.debug("Getting Organization " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceOrganization.get(id)).build();

  }

  @GET
  @Path("users/{userId}/")
  @Operation(summary = "Retrieve all Organization in which a User participate.")
  @APIResponse(responseCode = "200", description = "Organization successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOOrganization.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Organizations. Verify 'Warning' Header.")
  public RestResponse<List<DTOOrganization>> getOrganizationsByUser(@PathParam("userId") Integer userId) {

    audit.debug("Getting Organizations by userId " + userId + "...");
    return RestResponse.ResponseBuilder.ok(serviceOrganization.getOrganizationsByUser(userId)).build();

  }

  @POST
  @Operation(summary = "Create a new Organization.")
  @APIResponse(responseCode = "201", description = "Organization successfully created.", content = @Content(schema = @Schema(implementation = DTOOrganization.class)))
  @APIResponse(responseCode = "400", description = "Failed to create new Organization. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to create new Organization. Verify 'Warning' Header.")
  public RestResponse<DTOOrganization> create(DTOCreateOrganization dtoCreateOrganization) {

    if (dtoCreateOrganization == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    String email = dtoCreateOrganization.getEmail();
    String name = dtoCreateOrganization.getName();
    if (!utilityVerifyRequestField.isValidField(email) ||
        !utilityVerifyRequestField.isValidField(name)) {
      throw new HttpBadRequestException("Email and name required.");
    }

    audit.debug("Creating Organization...");
    DTOOrganization organization = serviceOrganization.create(dtoCreateOrganization);

    audit.debug("Creating URI...");
    URI uri = URI.create(PATH_BASE_RESOURCE + organization.getOrganizationId());

    return RestResponse.ResponseBuilder
        .create(RestResponse.Status.CREATED, organization)
        .location(uri)
        .build();

  }

  @PATCH
  @Path("{id}")
  @Operation(summary = "Update Organization.")
  @APIResponse(responseCode = "200", description = "Organization successfully updated.", content = @Content(schema = @Schema(implementation = DTOOrganization.class)))
  @APIResponse(responseCode = "204", description = "Failed to update new Organization. Verify 'Warning' Header.")
  @APIResponse(responseCode = "400", description = "Failed to update new Organization. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to update new Organization. Verify 'Warning' Header.")
  public RestResponse<DTOOrganization> update(@PathParam("id") Integer id,
      DTOUpdateOrganization dtoUpdateOrganization) {

    if (dtoUpdateOrganization == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    String email = dtoUpdateOrganization.getEmail();
    String name = dtoUpdateOrganization.getName();
    String description = dtoUpdateOrganization.getDescription();
    if (!utilityVerifyRequestField.isValidField(email) &&
        !utilityVerifyRequestField.isValidField(name) &&
        !utilityVerifyRequestField.isValidField(description)) {
      throw new HttpBadRequestException("No updates to make.");
    }

    audit.debug("Verifying if the ID of the Body and the Path are the same...");
    if (id.compareTo(dtoUpdateOrganization.getOrganizationId()) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same.");
    }

    audit.debug("Updating Organization" + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceOrganization.update(id, dtoUpdateOrganization)).build();

  }

  @DELETE
  @Path("{id}")
  @Operation(summary = "Delete an Organization.")
  @APIResponse(responseCode = "200", description = "Organization successfully deleted.", content = @Content(schema = @Schema(implementation = DTOOrganization.class)))
  @APIResponse(responseCode = "204", description = "Failed to delete new Organization. Verify 'Warning' Header.")
  public RestResponse<DTOOrganization> delete(@PathParam("id") Integer id) {

    audit.debug("Deleting Organization " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceOrganization.delete(id)).build();

  }

  // ROLE HANDLING IN ORGANIZATIONS

  @Deprecated
  @POST
  @Path("{id}/roles")
  @Operation(summary = "Assign Role to User in Organization.")
  @APIResponse(responseCode = "201", description = "Role successfully assign to User in Organization.", content = @Content(schema = @Schema(implementation = DTOUserRoleOrganization.class)))
  @APIResponse(responseCode = "400", description = "Failed to Assign Role to User in Organization. Verify 'Warning' Header.")
  @APIResponse(responseCode = "204", description = "Failed to Assign Role to User in Organization. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to Assign Role to User in Organization. Verify 'Warning' Header.")
  public RestResponse<DTOUserRoleOrganization> assignRoleToUserInLevel(@PathParam("id") Integer id,
      DTOAssingRoleToUserOrganization dtoAssingRoleToUserOrganization) {

    final String PATH_BASE_USER_ROL_ORGANIZATION = "/user-rol-organization/";

    audit.debug("Verifying if the ID of the Body and the Path are the same...");
    if (id.compareTo(dtoAssingRoleToUserOrganization.getOrganization()) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same.");
    }

    audit.debug("Assigning Role" + dtoAssingRoleToUserOrganization.getRole()
        + " to User " + dtoAssingRoleToUserOrganization.getUser()
        + " in Organization " + dtoAssingRoleToUserOrganization.getOrganization() + "...");
    DTOUserRoleOrganization dtoUserRoleOrganization = serviceOrganization.assignRole(dtoAssingRoleToUserOrganization);

    audit.debug("Creating URI...");
    URI uri = URI.create(PATH_BASE_USER_ROL_ORGANIZATION + dtoUserRoleOrganization.getOrganization());

    return RestResponse.ResponseBuilder
        .create(RestResponse.Status.CREATED, dtoUserRoleOrganization)
        .location(uri)
        .build();

  }

  @GET
  @Path("{id}/users/roles")
  @Operation(summary = "Retrieve Users with Role in Organization.")
  @APIResponse(responseCode = "200", description = "Users in Organization with Role successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOUserRoleOrganization.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve User with Role in Organization. Verify 'Warning' Header.")
  public RestResponse<List<DTOUserRoleOrganization>> getUsersWithRole(@PathParam("id") Integer idOrganization,
      @QueryParam("role") Integer idRole,
      @QueryParam("user") Integer idUser) {

    if (idRole == null && idUser == null) {
      audit.debug("Getting all the Users with Roles in Organization " + idOrganization + "...");
      return RestResponse.ResponseBuilder.ok(serviceOrganization.getAllUsersWithRoleByOrganization(idOrganization))
          .build();
    }

    if (idRole == null) {
      audit.debug("Getting all Roles of User(" + idUser + ") in Organization " + idOrganization + "...");
      return RestResponse.ResponseBuilder
          .ok(serviceOrganization.getAllRolesInOrganizationByUser(idOrganization, idUser)).build();
    }

    if (idUser == null) {
      audit.debug("Getting all the Users with Role(" + idRole + ") in Organization " + idOrganization + "...");
      return RestResponse.ResponseBuilder
          .ok(serviceOrganization.getAllUsersWithRoleInOrganization(idOrganization, idRole)).build();
    }

    audit.debug("Getting User(" + idUser + ") with Role (" + idRole + ") in Organization " + idOrganization + "...");
    return RestResponse.ResponseBuilder
        .ok(List.of(serviceOrganization.getUserRoleOrganization(idOrganization, idUser, idRole))).build();

  }

  @POST
  @Path("{id}/users/roles") // /{user}/roles/{role}")
  @Operation(summary = "Assign Role to User in Organization.")
  @APIResponse(responseCode = "201", description = "Role successfully assign to User in Organization.", content = @Content(schema = @Schema(implementation = DTOUserRoleOrganization.class)))
  @APIResponse(responseCode = "400", description = "Failed to Assign Role to User in Organization. Verify 'Warning' Header.")
  @APIResponse(responseCode = "204", description = "Failed to Assign Role to User in Organization. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to Assign Role to User in Organization. Verify 'Warning' Header.")
  public RestResponse<DTOUserRoleOrganization> assignRole(@PathParam("id") Integer idOrganization,
      // @PathParam("user") Integer idUser,
      // @PathParam("role") Integer idRole,
      DTOAssingRoleToUserOrganization dtoAssignRoleToUserOrganization) {

    if (dtoAssignRoleToUserOrganization == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    Integer user = dtoAssignRoleToUserOrganization.getUser();
    Integer organization = dtoAssignRoleToUserOrganization.getOrganization();
    Integer role = dtoAssignRoleToUserOrganization.getRole();
    if (!utilityVerifyRequestField.isValidField(user) ||
        !utilityVerifyRequestField.isValidField(organization) ||
        !utilityVerifyRequestField.isValidField(role)) {
      throw new HttpBadRequestException("All fields required.");
    }

    audit.debug("Verifying if the ID of the Body and the Path are the same...");
    if (idOrganization.compareTo(dtoAssignRoleToUserOrganization.getOrganization()) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same for Organization.");
    }

    audit.debug("Assigning Role" + role
        + " to User " + user
        + " in Organization " + idOrganization + "...");
    DTOUserRoleOrganization dtoUserRoleOrganization = serviceOrganization.assignRoleToUserInOrganization(idOrganization,
        user, role);

    audit.debug("Creating URI...");
    URI uri = URI.create(PATH_BASE_RESOURCE + dtoUserRoleOrganization.getOrganization() +
        "?users=" + dtoUserRoleOrganization.getUser() +
        "&roles=" + dtoUserRoleOrganization.getRole());

    return RestResponse.ResponseBuilder
        .create(RestResponse.Status.CREATED, dtoUserRoleOrganization)
        .location(uri)
        .build();

  }

  @PATCH
  @Path("{id}/users/roles") // {user}/roles/{role}")
  @Operation(summary = "Update Role of User in Organization.")
  @APIResponse(responseCode = "200", description = "Role successfully updated to User in Organization.", content = @Content(schema = @Schema(implementation = DTOUserRoleOrganization.class)))
  @APIResponse(responseCode = "400", description = "Failed to update Role to User in Organization. Verify 'Warning' Header.")
  @APIResponse(responseCode = "204", description = "Failed to update Role to User in Organization. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to update Role to User in Organization. Verify 'Warning' Header.")
  public RestResponse<DTOUserRoleOrganization> updateRoleOfUserInOrganization(@PathParam("id") Integer idOrganization,
      // @PathParam("user") Integer idUser,
      // @PathParam("role") Integer idRole,
      DTOUpdateRoleUserOrganization dtoUpdateRoleUserOrganization) {

    if (dtoUpdateRoleUserOrganization == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    Integer user = dtoUpdateRoleUserOrganization.getUser();
    Integer organization = dtoUpdateRoleUserOrganization.getOrganization();
    Integer role = dtoUpdateRoleUserOrganization.getRole();
    Integer newRole = dtoUpdateRoleUserOrganization.getRole();
    if (!utilityVerifyRequestField.isValidField(user) ||
        !utilityVerifyRequestField.isValidField(organization) ||
        !utilityVerifyRequestField.isValidField(newRole)) {
      throw new HttpBadRequestException("All fields required.");
    }

    audit.debug("Verifying if the ID of the Body and the Path are the same...");
    if (idOrganization.compareTo(organization) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same for Organization.");
    }

    audit.debug("Updating Role" + newRole
        + " of User " + user
        + " in Organization " + idOrganization + "...");
    DTOUserRoleOrganization dtoUserRoleOrganization = serviceOrganization.updateRoleOfUserInOrganization(idOrganization,
        user, role, newRole);

    return RestResponse.ResponseBuilder.ok(dtoUserRoleOrganization).build();

  }

  @DELETE
  @Path("{id}/users/{user}")
  @Operation(summary = "Delete all Roles of a User in a Organization.")
  @APIResponse(responseCode = "200", description = "Roles of User successfully deleted in Organization.", content = @Content(schema = @Schema(implementation = DTOUserRoleOrganization.class)))
  @APIResponse(responseCode = "204", description = "Failed to delete Roles of User in Organization. Verify 'Warning' Header.")
  public RestResponse<List<DTOUserRoleOrganization>> deleteAllRolesOfUserInOrganization(
      @PathParam("id") Integer idOrganization,
      @PathParam("user") Integer idUser) {

    audit.debug("Deleting all Roles of User(" + idUser + ") in Organization (" + idOrganization + ")...");
    return RestResponse.ResponseBuilder
        .ok(serviceOrganization.deleteAllRolesOfUserInOrganization(idOrganization, idUser)).build();

  }

  @DELETE
  @Path("{id}/users/{user}/roles/{role}")
  @Operation(summary = "Delete a Role of a User in a Organization.")
  @APIResponse(responseCode = "200", description = "Role of User successfully deleted in Organization.", content = @Content(schema = @Schema(implementation = DTOUserRoleOrganization.class)))
  @APIResponse(responseCode = "204", description = "Failed to delete Role of User in Organization. Verify 'Warning' Header.")
  public RestResponse<DTOUserRoleOrganization> deleteUserRoleOrganization(@PathParam("id") Integer idOrganization,
      @PathParam("user") Integer idUser,
      @PathParam("role") Integer idRole) {

    audit.debug(
        "Deleting User(" + idUser + ")Role(" + idRole + ")Organization(" + idUser + ") " + idOrganization + "...");
    return RestResponse.ResponseBuilder
        .ok(serviceOrganization.deleteUserRoleOrganization(idOrganization, idUser, idRole)).build();

  }

  // VOTES HANDLING IN ORGANIZATIONS
  @Deprecated(since = "1.0.1")
  @POST
  @Path("{id}/votes")
  @Operation(summary = "Vote Organization.")
  @APIResponse(responseCode = "201", description = "Organization successfully voted.", content = @Content(schema = @Schema(implementation = DTOVote.class)))
  @APIResponse(responseCode = "400", description = "Failed vote Organization. Verify 'Warning' Header.")
  @APIResponse(responseCode = "204", description = "Failed to vote Organization. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to vote Organization. Verify 'Warning' Header.")
  public RestResponse<DTOVote> vote(@PathParam("id") Integer idOrganization,
      DTOCreateVote dtoCreateVote) {

    if (dtoCreateVote == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    Integer user = dtoCreateVote.getUser();
    Integer organization = dtoCreateVote.getEntity();
    if (!utilityVerifyRequestField.isValidField(user) ||
        !utilityVerifyRequestField.isValidField(organization)) {
      throw new HttpBadRequestException("All fields required.");
    }

    audit.debug("Verifying if the ID of the Body and the Path are the same...");
    if (idOrganization.compareTo(dtoCreateVote.getEntity()) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same for Organization.");
    }
    audit.debug("Vote of User " + user
        + " in Organization " + idOrganization + "...");
    DTOVote dtoVote = serviceOrganization.vote(idOrganization, user);

    audit.debug("Creating URI...");
    URI uri = URI.create(PATH_BASE_RESOURCE_VOTE + dtoVote.getVoteId());

    return RestResponse.ResponseBuilder
        .create(RestResponse.Status.CREATED, dtoVote)
        .location(uri)
        .build();

  }

  @GET
  @Path("/votes")
  @Operation(summary = "Retrieve votes of Organizations.")
  @APIResponse(responseCode = "200", description = "Votes of Organizations successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOVotedEntity.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Votes of Organizations. Verify 'Warning' Header.")
  public RestResponse<List<DTOVotedEntity>> getAllVotes() {

    audit.debug("Getting Organizations Votes...");
    return RestResponse.ResponseBuilder.ok(serviceOrganization.getAllVotes()).build();

  }

  @GET
  @Path("{id}/votes")
  @Operation(summary = "Retrieve votes of a Organization.")
  @APIResponse(responseCode = "200", description = "Votes of Organization successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOVotedEntity.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Votes of Organization. Verify 'Warning' Header.")
  public RestResponse<List<DTOVotedEntity>> getVotes(@PathParam("id") Integer id) {

    audit.debug("Getting Organization " + id + " Votes...");
    return RestResponse.ResponseBuilder.ok(serviceOrganization.getVotes(id)).build();

  }

  @GET
  @Path("/tags")
  @Operation(summary = "Retrieve tags of Organizations.")
  @APIResponse(responseCode = "200", description = "Tags of Organizations successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOTaggedEntity.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Tags of Organizations. Verify 'Warning' Header.")
  public RestResponse<List<DTOTaggedEntity>> getAllTags() {

    audit.debug("Getting Organizations Tags...");
    return RestResponse.ResponseBuilder.ok(serviceOrganization.getAllTags()).build();

  }

  @GET
  @Path("{id}/tags")
  @Operation(summary = "Retrieve tags of a Organization.")
  @APIResponse(responseCode = "200", description = "Tags of Organization successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOTaggedEntity.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Tags of Organization. Verify 'Warning' Header.")
  public RestResponse<List<DTOTaggedEntity>> getTags(@PathParam("id") Integer id) {

    audit.debug("Getting Organization " + id + " Tags...");
    return RestResponse.ResponseBuilder.ok(serviceOrganization.getTags(id)).build();

  }

}
