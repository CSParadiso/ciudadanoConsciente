package ciudadano.consciente.resource;

import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceUser;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import io.quarkus.oidc.UserInfo;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
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

@Tag(name = "User Resource")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/users")
public class ResourceUser {

  final String BASE_PATH_RESOURCE = "/users/";

  @Inject
  SecurityIdentity securityIdentity;

  @Inject
  ServiceUser serviceUser;

  @Inject
  Logger audit;

  @Inject
  UtilityVerifyRequestField utilityVerifyRequestField;

  @RolesAllowed("Ciuco-Admin")
  @GET
  @Operation(summary = "Retrieve all users.")
  @APIResponse(responseCode = "200", description = "Users successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOUser.class)))
  public RestResponse<List<DTOUser>> getAll() {

    audit.debug("Getting all Users...");
    return RestResponse.ResponseBuilder.ok(serviceUser.getAll()).build();

  }

  // TODO Quizás O-Divulgator cuando quiera asignar Roles (se puede verificar el
  // path desde donde viene en el MainFilter)
  @RolesAllowed("Ciuco-Admin")
  @GET
  @Path("/{id}/")
  @Operation(summary = "Retrieve a specific User by its ID.")
  @APIResponse(responseCode = "200", description = "User successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOUser.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve User. Verify 'Warning' Header.")
  public RestResponse<DTOUser> get(@PathParam("id") Integer id) {

    audit.debug("Getting User " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceUser.get(id)).build();

  }

  // TODO Quizás O-Divulgator cuando quiera asignar Roles (se puede verificar el
  // path desde donde viene en el MainFilter)
  @RolesAllowed("Ciuco-Admin")
  @GET
  @Path("username/{username}")
  @Operation(summary = "Retrieve a specific User by its username.")
  @APIResponse(responseCode = "200", description = "User successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOUser.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve User. Verify 'Warning' Header.")
  public RestResponse<DTOUser> getByUsername(@PathParam("username") String username) {

    audit.debug("Getting User with username " + username + "...");
    return RestResponse.ResponseBuilder.ok(serviceUser.getByUsername(username)).build();

  }

  // TODO Quizás O-Divulgator cuando quiera asignar Roles (se puede verificar el
  // path desde donde viene en el MainFilter)
  @RolesAllowed("Ciuco-Admin")
  @GET
  @Path("email/{email}")
  @Operation(summary = "Retrieve a specific User by its email address.")
  @APIResponse(responseCode = "200", description = "User successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOUser.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve User. Verify 'Warning' Header.")
  public RestResponse<DTOUser> getByEmailAddress(@PathParam("email") String email) {

    audit.debug("Getting User with email address " + email + "...");
    return RestResponse.ResponseBuilder.ok(serviceUser.getByEmail(email)).build();

  }

  // @Deprecated(since = "1.1.0. The data to create the User is in the
  // AccessToken, not in a DTO")
  // @Authenticated
  // @POST
  // @Operation(summary = "Create a new User.")
  // @APIResponse(responseCode = "201", description = "User successfully
  // created.", content = @Content(schema = @Schema(implementation =
  // DTOUser.class)))
  // @APIResponse(responseCode = "400", description = "Failed to create User.
  // Verify 'Warning' Header.")
  // @APIResponse(responseCode = "500", description = "Failed to create User.
  // Verify 'Warning' Header.")
  // public RestResponse<DTOUser> createUserDeprecated(DTOCreateUser
  // dtoCreateUser) {

  // if (dtoCreateUser == null) {
  // throw new HttpBadRequestException("Body of request required.");
  // }

  // String email = dtoCreateUser.getEmail();
  // String username = dtoCreateUser.getUsername();
  // String password = dtoCreateUser.getPassword();
  // if (!utilityVerifyRequestField.isValidField(email) ||
  // !utilityVerifyRequestField.isValidField(username) ||
  // !utilityVerifyRequestField.isValidField(password)) {
  // throw new HttpBadRequestException("All fields required.");
  // }

  // audit.debug("Creating User...");
  // DTOUser user = serviceUser.createUser(dtoCreateUser);

  // audit.debug("Creating URI...");
  // URI uri = URI.create(BASE_PATH_RESOURCE + user.getUserId());

  // return RestResponse.ResponseBuilder
  // .create(RestResponse.Status.CREATED, user)
  // .location(uri)
  // .build();

  // }

  @Authenticated
  @POST
  @Operation(summary = "Create a new User.")
  @APIResponse(responseCode = "201", description = "User successfully created.", content = @Content(schema = @Schema(implementation = DTOUser.class)))
  @APIResponse(responseCode = "400", description = "Failed to create User. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to create User. Verify 'Warning' Header.")
  public RestResponse<DTOUser> create() {

    UserInfo userInfo = securityIdentity.getAttribute("userinfo");

    String authServerId = userInfo.getSubject();
    //String username = userInfo.getPreferredUserName();
    String username = userInfo.getName();
    String email = userInfo.getEmail();
    if (!utilityVerifyRequestField.isValidField(authServerId) ||
        !utilityVerifyRequestField.isValidField(username) ||
        !utilityVerifyRequestField.isValidField(email)) {
      throw new HttpBadRequestException("Missing mandatory Claims (sub, preferred_username, email) from Access Token.");
    }

    audit.debug("KC-ID: " + authServerId); // keycloak.user_entity.id
    audit.debug("KC-PUN: " + username); // keycloak.user_entity.username
    audit.debug("KC-EMAIL: " + email); // keycloak.user_entity.email_constraint

    audit.debug("Creating User...");
    DTOUser user = serviceUser.create(authServerId, username, email);

    audit.debug("Creating URI...");
    URI uri = URI.create(BASE_PATH_RESOURCE + user.getUserId());

    return RestResponse.ResponseBuilder
        .create(RestResponse.Status.CREATED, user)
        .location(uri)
        .build();

  }

  // @Deprecated(since = "1.1.0. The only field that can be updated is
  // 'authServerId' if IdentityProvider changes.")
  // @PATCH
  // @Path("{id}")
  // @Operation(summary = "Update User.")
  // @APIResponse(responseCode = "200", description = "User successfully
  // updated.", content = @Content(schema = @Schema(implementation =
  // DTOUser.class)))
  // @APIResponse(responseCode = "204", description = "Failed to update User.
  // Verify 'Warning' Header.")
  // @APIResponse(responseCode = "400", description = "Failed to update User.
  // Verify 'Warning' Header.")
  // @APIResponse(responseCode = "500", description = "Failed to update User.
  // Verify 'Warning' Header.")
  // public RestResponse<DTOUser> updateUserDeprecated(@PathParam("id") Integer
  // id,
  // DTOUpdateUser dtoUpdateUser) {

  // if (dtoUpdateUser == null) {
  // throw new HttpBadRequestException("Body of request required.");
  // }

  // String email = dtoUpdateUser.getEmail();
  // String username = dtoUpdateUser.getUsername();
  // String password = dtoUpdateUser.getPassword();
  // if (!utilityVerifyRequestField.isValidField(email) &&
  // !utilityVerifyRequestField.isValidField(username) &&
  // !utilityVerifyRequestField.isValidField(password)) {
  // throw new HttpBadRequestException("No updates to make.");
  // }

  // audit.debug("Verifying if the ID of the Body and the Path are the same...");
  // if (id.compareTo(dtoUpdateUser.getUserId()) != 0) {
  // throw new HttpBadRequestException("Body ID and Path ID must be the same.");
  // }

  // audit.debug("Updating User... " + id + "...");
  // return RestResponse.ResponseBuilder.ok(serviceUser.updateUser(id,
  // dtoUpdateUser)).build();

  // }

  @RolesAllowed("Ciuco-Admin")
  @PATCH
  @Path("{id}")
  @Operation(summary = "Update User.")
  @APIResponse(responseCode = "200", description = "User successfully updated.", content = @Content(schema = @Schema(implementation = DTOUser.class)))
  @APIResponse(responseCode = "204", description = "Failed to update User. Verify 'Warning' Header.")
  @APIResponse(responseCode = "400", description = "Failed to update User. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to update User. Verify 'Warning' Header.")
  public RestResponse<DTOUser> update(@PathParam("id") Integer id,
      DTOUpdateUserIdentityProvider dtoUpdateUserIdentityProvider) {

    if (dtoUpdateUserIdentityProvider == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    String actualAuthServerID = dtoUpdateUserIdentityProvider.getActualAuthServerId();
    String newAuthServerId = dtoUpdateUserIdentityProvider.getNewAuthServerId();
    if (!utilityVerifyRequestField.isValidField(actualAuthServerID) &&
        !utilityVerifyRequestField.isValidField(newAuthServerId)) {
      throw new HttpBadRequestException("No updates to make.");
    }

    audit.debug("Updating User... " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceUser.update(id, dtoUpdateUserIdentityProvider)).build();

  }

  // @Deprecated(since = "1.1.0. Only Admin and User upon request could delete
  // User.")
  // @DELETE
  // @Path("{id}")
  // @Operation(summary = "Delete a User.")
  // @APIResponse(responseCode = "200", description = "User successfully
  // deleted.", content = @Content(schema = @Schema(implementation =
  // DTOUser.class)))
  // @APIResponse(responseCode = "204", description = "Failed to create User.
  // Verify 'Warning' Header.")
  // public RestResponse<DTOUser> deleteUserDeprecated(@PathParam("id") Integer
  // id) {

  // audit.debug("Deleting User " + id + "...");
  // return RestResponse.ResponseBuilder.ok(serviceUser.deleteUser(id)).build();

  // }

  @Authenticated
  @DELETE
  @Path("{id}")
  @Operation(summary = "Delete a User.")
  @APIResponse(responseCode = "200", description = "User successfully deleted.", content = @Content(schema = @Schema(implementation = DTOUser.class)))
  @APIResponse(responseCode = "204", description = "Failed to create User. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to create User. Verify 'Warning' Header.")
  // REQUIRES UPDATE KEYCLOAK SERVER
  public RestResponse<DTOUser> delete(@PathParam("id") Integer id) {

    audit.debug("Deleting User " + id + "...");

    UserInfo userInfo = securityIdentity.getAttribute("userinfo");
    boolean userRequested = !securityIdentity.hasRole("Ciuco-Admin");

    if (userRequested) {
      audit.debug("User " + userInfo.getEmail() + " is trying to delete User " + id);
    } else {
      audit.debug("Admin " + userInfo.getEmail() + " is trying to delete User " + id);
    }

    return RestResponse.ResponseBuilder.ok(serviceUser.delete(id, userInfo, userRequested)).build();

  }

}
