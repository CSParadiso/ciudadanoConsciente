package ciudadano.consciente.resource;

import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.AuthDenialSecurityException;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceContent;
import ciudadano.consciente.utility.UtilityAuthVerifier;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import io.quarkus.oidc.UserInfo;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.jpa.Roles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;

@Authenticated
@RequestScoped
@Tag(name = "Content Resource")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("contents")
public class ResourceContent {

  final String BASE_PATH_RESOURCE = "/contents/";
  final String BASE_PATH_RESOURCE_VOTE = "/votes/";

  @Inject
  Logger audit;

  @Inject
  UtilityVerifyRequestField utilityVerifyRequestField;

  @Inject
  ServiceContent serviceContent;

  @Inject
  SecurityIdentity securityIdentity;

  @Inject
  UtilityAuthVerifier utilityAuthVerifier;

  @RolesAllowed("Ciuco-Admin")
  @GET
  @Path("all")
  @Operation(summary = "Retrieve all Contents.")
  @APIResponse(
          responseCode = "200",
          description = "Contents successfully retrieved.",
          content = @Content (schema = @Schema(implementation = DTOContent.class))
  )
  @APIResponse(responseCode = "204", description = "Failed to retrieve all Contents. Verify 'Warning' header.")
  public RestResponse<List<DTOContent>> getAll() {

    utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});

    return RestResponse.ResponseBuilder.ok(serviceContent.getAll()).build();

  }

  @GET
  @Operation(summary = "Retrieve all public Contents.")
  @APIResponse(
          responseCode = "200",
          description = "Public contents successfully retrieved.",
          content = @Content (schema = @Schema(implementation = DTOContent.class))
  )
  @APIResponse(responseCode = "204", description = "Failed to retrieve all public contents. Verify 'Warning' header.")
  public RestResponse<List<DTOContent>> getAllPublic() {

    utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});

    return RestResponse.ResponseBuilder.ok(serviceContent.getAllPublic()).build();

  }

  @GET
  @Path("organizations/{organization}")
  @Operation(summary = "Retrieve all Contents of Organization.")
  @APIResponse(
          responseCode = "200",
          description = "Contents of Organization successfully retrieved.",
          content = @Content (schema = @Schema(implementation = DTOContent.class))
  )
  @APIResponse(responseCode = "204", description = "Failed to retrieve all Contents. Verify 'Warning' header.")
  public RestResponse<List<DTOContent>> getByOrganization(@PathParam("organization") Integer organizationId,
                                                          @QueryParam("public") Boolean isPublic) {

    UtilityAuthVerifier.UserAuthData userAuthData =
            utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});

    return RestResponse.ResponseBuilder.ok(serviceContent.getAllByOrganization(organizationId, isPublic, userAuthData)).build();

  }

  @GET
  @Path("users/{userId}")
  @Operation(summary = "Retrieve all Contents of a User.")
  @APIResponse(
          responseCode = "200",
          description = "Contents of User successfully retrieved.",
          content = @Content (schema = @Schema(implementation = DTOContent.class))
  )
  @APIResponse(responseCode = "204", description = "Failed to retrieve all Contents. Verify 'Warning' header.")
  public RestResponse<List<DTOContent>> getByUser(@PathParam("userId") Integer userId,
                                                  @QueryParam("public") Boolean isPublic) {

    UtilityAuthVerifier.UserAuthData userAuthData =
            utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});

    return RestResponse.ResponseBuilder.ok(serviceContent.getAllByUser(userId, isPublic, userAuthData)).build();

  }

  @GET
  @Path("{id}")
  @Operation(summary = "Retrieve a specific Content.")
  @APIResponse(
          responseCode = "200",
          description = "Content successfully retrieved.",
          content = @Content (schema = @Schema(implementation = DTOContent.class))
  )
  @APIResponse(responseCode = "204", description = "Failed to retrieve Content. Verify 'Warning' header.")
  public RestResponse<DTOContent> get(@PathParam("id") Integer id) {

    UtilityAuthVerifier.UserAuthData userAuthData =
            utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});
    return RestResponse.ResponseBuilder.ok(serviceContent.get(id, userAuthData)).build();

  }

  @Authenticated
  @POST
  @Operation(summary = "Create a new Content for a Activity Type Version.")
  @Consumes({ MediaType.MULTIPART_FORM_DATA })
  @APIResponse(
          responseCode = "201",
          description = "Content successfully created.",
          content = @Content (schema = @Schema(implementation = DTOContent.class))
  )
  @APIResponse(responseCode = "400", description = "Failed to create Content. Verify 'Warning' Header.")
  @APIResponse(responseCode = "204", description = "Failed to create Content. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to create new Content. Verify 'Warning' Header.")
  public RestResponse<DTOContent> create(@RequestBody @Valid DTOCreateContent dtoCreateContent) {

    UtilityAuthVerifier.UserAuthData userAuthData =
            utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});

    DTOContent dtoContent = serviceContent.create(dtoCreateContent, userAuthData);

    URI uri = URI.create(BASE_PATH_RESOURCE + dtoContent.getContentId());

    return RestResponse.ResponseBuilder
            .create(RestResponse.Status.CREATED, dtoContent)
            .location(uri)
            .build();

  }

  @POST
  @Path("images")
  @Operation(summary = "Add Images to Content.")
  @Consumes({ MediaType.MULTIPART_FORM_DATA })
  @APIResponse(
          responseCode = "201",
          description = "Image successfully added.",
          content = @Content (schema = @Schema(implementation = DTOImage.class))
  )
  @APIResponse(responseCode = "400", description = "Failed to add Image. Verify 'Warning' Header.")
  @APIResponse(responseCode = "204", description = "Failed to add Image. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to add Image. Verify 'Warning' Header.")
  // public Response create(DTOCreateContent dtoCreateContent) {
  public RestResponse<DTOImage> addImageToContent(@RequestBody @Valid DTOCreateImage dtoCreateImage) {

    UtilityAuthVerifier.UserAuthData userAuthData =
            utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});

    DTOImage dtoImage = serviceContent.addImage(dtoCreateImage, userAuthData);

    URI uri = URI.create(BASE_PATH_RESOURCE + dtoImage.getContentId() + "/images/" + dtoImage.getImageId());

    return RestResponse.ResponseBuilder
            .create(RestResponse.Status.CREATED, dtoImage)
            .location(uri)
            .build();

  }

  @DELETE
  @Path("{id}")
  @Operation(summary = "Delete a specific Content by its ID.")
  @APIResponse(
          responseCode = "200",
          description = "Content successfully deleted.",
          content = @Content (schema = @Schema(implementation = DTOContent.class))
  )
  @APIResponse(responseCode = "204", description = "Failed to delete Content. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to delete Content. Verify 'Warning' Header.")
  public RestResponse<DTOContent> delete(@PathParam("id") Integer id) {

    UtilityAuthVerifier.UserAuthData userAuthData =
            utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});

    return RestResponse.ResponseBuilder.ok(serviceContent.delete(id, userAuthData)).build();

  }

  @GET
  @Path("{content}/images")
  @Operation(summary = "Retrieve all Images from Content.")
  @APIResponse(
          responseCode = "200",
          description = "Images successfully retrieved.",
          content = @Content (schema = @Schema(implementation = DTOImage.class))
  )
  @APIResponse(responseCode = "204", description = "Failed to retrieve images. Verify 'Warning' header.")
  public RestResponse<List<DTOImage>> getAllImages(@PathParam("content") Integer content) {

    UtilityAuthVerifier.UserAuthData userAuthData =
            utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});

    return RestResponse.ResponseBuilder.ok(serviceContent.getAllImages(content, userAuthData))
        .build();

  }

  @GET
  @Path("{content}/images/{id}")
  @Operation(summary = "Retrieve a Image File from Content.")
  @Produces({ "application/json", "image/png" })
  @APIResponse(responseCode = "200", description = "Image successfully retrieved.", content = @Content(mediaType = "image/png"))
  @APIResponse(responseCode = "204", description = "Failed to retrieve image. Verify 'Warning' header.", content = @Content(mediaType = "application.json"))
  public Response getImage(@PathParam("content") Integer content,
      @PathParam("id") Integer image) {

    UtilityAuthVerifier.UserAuthData userAuthData =
            utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});

    Object imageFile = serviceContent.getImage(content, image, userAuthData);
    String type = imageFile != null ? "image/png" : "application/json"; // TODO Corregir esta asignación de respuesta
                                                                        // (anda bien el endpoint pero la documentación
                                                                        // de la respuesta no es correcta)
    return Response.ok(imageFile)
        .type(type)
        .build();

  }

  @GET
  @Path("{content}/model")
  @Operation(summary = "Retrieve a Model file from Content.")
  @APIResponse(responseCode = "200", description = "Model file successfully retrieved.")
  @APIResponse(responseCode = "204", description = "Failed to retrieve Model file. Verify 'Warning' header.")
  public Response getModel(@PathParam("content") Integer content) {

    UtilityAuthVerifier.UserAuthData userAuthData =
            utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});

    return Response.ok(serviceContent.getModel(content, userAuthData))
        .type("application/json")
        .build();

  }

  @PATCH
  @Path("{id}")
  @Operation(summary = "Update a Content.")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @APIResponse(
          responseCode = "200",
          description = "Content updated successfully.",
          content = @Content (schema = @Schema(implementation = DTOContent.class))
  )
  @APIResponse(responseCode = "204", description = "Failed to update Content. Verify 'Warning' header.")
  public RestResponse<DTOContent> update(@PathParam("id") Integer id,
                                         @RequestBody @Valid DTOUpdateContent dtoUpdateContent) {

    if (id.compareTo(dtoUpdateContent.getContent()) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same.");
    }

    UtilityAuthVerifier.UserAuthData userAuthData =
            utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});

    return RestResponse.ResponseBuilder.ok(serviceContent.update(id, dtoUpdateContent, userAuthData)).build();

  }

  @PATCH
  @Path("{content}/images/{id}")
  @Operation(summary = "Update a Image file from Content.")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @APIResponse(responseCode = "200", description = "Image File updated successfully.")
  @APIResponse(responseCode = "204", description = "Failed to update Image File. Verify 'Warning' header.")
  public Response updateImage(@PathParam("content") Integer contentId, @PathParam("id") Integer imageId,
      @RequestBody @Valid DTOUpdateContentImage dtoUpdateContentImage) {

    audit.debug("Verifying if the ID of the Body and the Path are the same...");
    if (contentId.compareTo(dtoUpdateContentImage.getContent()) != 0 ||
        imageId.compareTo(dtoUpdateContentImage.getImage()) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same.");
    }

    UtilityAuthVerifier.UserAuthData userAuthData =
            utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});

    return Response.ok(serviceContent.updateImage(dtoUpdateContentImage, userAuthData)).build();

  }

  // VOTES HANDLING IN Content
  @Deprecated(since = "1.0.1")
  @POST
  @Path("{id}/votes")
  @Operation(summary = "Vote Content.")
  @APIResponse(
          responseCode = "201",
          description = "Content successfully voted.",
          content = @Content (schema = @Schema(implementation = DTOVote.class))
  )
  @APIResponse(responseCode = "400", description = "Failed to Vote Content. Verify 'Warning' Header.")
  @APIResponse(responseCode = "204", description = "Failed to Vote Content. Verify 'Warning' Header.")
  @APIResponse(responseCode = "500", description = "Failed to Vote Content. Verify 'Warning' Header.")
  public RestResponse<DTOVote> vote(@PathParam("id") Integer idContent,
      DTOCreateVote dtoCreateVote) {

    if (dtoCreateVote == null) {
      throw new HttpBadRequestException("Body of request required.");
    }

    Integer user = dtoCreateVote.getUser();
    Integer content = dtoCreateVote.getEntity();
    if (!utilityVerifyRequestField.isValidField(user) ||
        !utilityVerifyRequestField.isValidField(content)) {
      throw new HttpBadRequestException("All fields required.");
    }

    audit.debug("Verifying if the ID of the Body and the Path are the same...");
    if (idContent.compareTo(dtoCreateVote.getEntity()) != 0) {
      throw new HttpBadRequestException("Body ID and Path ID must be the same for Content.");
    }
    audit.debug("Vote of User " + user
        + " in Content " + idContent + "...");
    DTOVote dtoVote = serviceContent.vote(idContent, user);

    audit.debug("Creating URI...");
    URI uri = URI.create(BASE_PATH_RESOURCE_VOTE + dtoVote.getVoteId());

    return RestResponse.ResponseBuilder
            .create(RestResponse.Status.CREATED, dtoVote)
            .location(uri)
            .build();

  }

  @RolesAllowed("Ciuco-Admin")
  @GET
  @Path("/votes")
  @Operation(summary = "Retrieve votes of Contents.")
  @APIResponse(responseCode = "200", description = "Votes of Contents successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOVotedEntity.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Votes of Contents. Verify 'Warning' Header.")
  public RestResponse<List<DTOVotedEntity>> getAllVotes() {

    utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});

    return RestResponse.ResponseBuilder.ok(serviceContent.getAllVotes()).build();

  }

  @GET
  @Path("{id}/votes")
  @Operation(summary = "Retrieve votes of a Content.")
  @APIResponse(responseCode = "200", description = "Votes of Content successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOVotedEntity.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Votes of Content. Verify 'Warning' Header.")
  public RestResponse<List<DTOVotedEntity>> getVotes(@PathParam("id") Integer id) {

    utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});

    return RestResponse.ResponseBuilder.ok(serviceContent.getVotes(id)).build();

  }

  @GET
  @Path("/tags")
  @Operation(summary = "Retrieve tags of Contents.")
  @APIResponse(responseCode = "200", description = "Tags of Contents successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOTaggedEntity.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Tags of Contents. Verify 'Warning' Header.")
  public RestResponse<List<DTOTaggedEntity>> getAllTags() {

    utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});

    return RestResponse.ResponseBuilder.ok(serviceContent.getAllTags()).build();

  }

  @GET
  @Path("{id}/tags")
  @Operation(summary = "Retrieve tags of a Content.")
  @APIResponse(responseCode = "200", description = "Tags of Content successfully retrieved.", content = @Content(schema = @Schema(implementation = DTOTaggedEntity.class)))
  @APIResponse(responseCode = "204", description = "Failed to retrieve Tags of Content. Verify 'Warning' Header.")
  public RestResponse<List<DTOTaggedEntity>> getTags(@PathParam("id") Integer id) {

    utilityAuthVerifier.getPermissions(securityIdentity, new Object(){});

    return RestResponse.ResponseBuilder.ok(serviceContent.getTags(id)).build();

  }

}
