package ciudadano.consciente.resource;

import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.AuthDenialSecurityException;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceUser;
import ciudadano.consciente.utility.UtilityAuthVerifier;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import io.quarkus.oidc.UserInfo;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
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

@Authenticated
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

  @Inject
  UtilityAuthVerifier utilityAuthVerifier;

  @RolesAllowed("Ciuco-Admin")
  @GET
  @Operation(summary = "Retrieve all users.")
  @APIResponse(responseCode = "200", description = "Users successfully retrieved.",
          content = @Content(schema = @Schema(implementation = DTOUser.class)))
  public RestResponse<List<DTOUser>> getAll() {

    audit.debug("Getting all Users...");
    return RestResponse.ResponseBuilder.ok(serviceUser.getAll()).build();

  }

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

  // TODO: NO SE UTILIZA DESDE EL FRONT
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

  @GET
  @Path("email/{email}")
  @Operation(summary = "Retrieve a specific User by its email address.")
  @APIResponse(responseCode = "200", description = "User successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOUser.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve User. Verify 'Warning' Header.")
  public RestResponse<DTOUser> getByEmailAddress(@PathParam("email") String email) {

    audit.debug("Getting User with email address " + email + "...");

    UserInfo userInfo = securityIdentity.getAttribute("userinfo");
    boolean userRequested = !securityIdentity.hasRole("Ciuco-Admin");

    if (userRequested) {
      audit.debug("User " + userInfo.getEmail() + " is trying to retrieve User with email " + email);
    } else {
      audit.debug("Admin " + userInfo.getEmail() + " is trying to retrieve User with email " + email);
    }

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

  @GET
  @Path("statistics")
  @Operation(summary = "Retrieve statistics of User.")
  @APIResponse(responseCode = "200", description = "User statistics successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOUserStatistics.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve User statistics. Verify 'Warning' Header.")
  public RestResponse<DTOUserStatistics> getStatistics() {

    UtilityAuthVerifier.UserAuthData userAuthData =
            utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});

    return RestResponse.ResponseBuilder.ok(serviceUser.getStatistics(userAuthData)).build();

  }

  @POST
  @Operation(summary = "Create a new User.")
  @APIResponse(responseCode = "201", description = "User successfully created.", content = @Content(schema = @Schema(implementation = DTOUser.class)))
  @APIResponse(responseCode = "400", description = "Failed to create User. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to create User. Verify 'Warning' Header.")
  public RestResponse<DTOUser> create() {

    UtilityAuthVerifier.UserAuthData userAuthData = utilityAuthVerifier.getPermissions(securityIdentity,
            new Object(){});

    DTOUser user = serviceUser.create(userAuthData);

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
  @Path("migrate/{id}")
  @Operation(summary = "Migrate Auth Server Identifier of User.")
  @APIResponse(responseCode = "200", description = "User Auth Id successfully migrate.", content = @Content(schema =
  @Schema(implementation = DTOUser.class)))
  @APIResponse(responseCode = "204", description = "Failed to migrate User. Verify 'Warning' Header.")
  @APIResponse(responseCode = "400", description = "Failed to migrate User. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to migrate User. Verify 'Warning' Header.")
  public RestResponse<DTOUser> migrate(@PathParam("id") Integer id,
      DTOMigrateUserIdentityProvider dtoMigrateUserIdentityProvider) {

    if (dtoMigrateUserIdentityProvider == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    String actualAuthServerID = dtoMigrateUserIdentityProvider.getActualAuthServerId();
    String newAuthServerId = dtoMigrateUserIdentityProvider.getNewAuthServerId();
    if (!utilityVerifyRequestField.isValidField(actualAuthServerID) &&
        !utilityVerifyRequestField.isValidField(newAuthServerId)) {
      throw new HttpBadRequestException("No migrations to make.");
    }

    audit.debug("Migrating User... " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceUser.migrate(id, dtoMigrateUserIdentityProvider)).build();

  }

  @PATCH
  @Path("{id}")
  @Operation(summary = "Update a User.")
  @APIResponse(responseCode = "200", description = "User successfully updated.", content = @Content(schema =
  @Schema(implementation = DTOUser.class)))
  @APIResponse(responseCode = "204", description = "Failed to update User. Verify 'Warning' Header.")
  @APIResponse(responseCode = "400", description = "Failed to update User. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to update User. Verify 'Warning' Header.")
  public RestResponse<DTOUser> update(@PathParam("id") Integer id,
                                       DTOUpdateUser dtoUpdateUser) {

    if (dtoUpdateUser == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    String username = dtoUpdateUser.getUsername();
    if (!utilityVerifyRequestField.isValidField(username)) {
      throw new HttpBadRequestException("No update to make.");
    }

    audit.debug("Verifying if the ID of the Body and the Path are the same...");
    if (id.compareTo(dtoUpdateUser.getUserId()) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same for User.");
    }

    UserInfo userInfo = securityIdentity.getAttribute("userinfo");

    audit.debug("Updating User... " + id + "...");
    return RestResponse.ResponseBuilder.ok(serviceUser.update(id, dtoUpdateUser, userInfo)).build();

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
