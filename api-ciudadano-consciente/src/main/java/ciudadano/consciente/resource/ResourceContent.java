package ciudadano.consciente.resource;

import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceContent;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.print.attribute.standard.Media;
import java.net.URI;
import java.util.List;

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

    @GET
    @Operation(summary = "Retrieve all Contents.")
    @APIResponse(
            responseCode = "200",
            description = "Contents successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve all Contents. Verify 'Warning' header."
    )
    public Response getAll() {

        audit.debug("Getting all Contents.");
        return Response.ok(serviceContent.getAll())
                .build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Retrieve a specific Content.")
    @APIResponse(
            responseCode = "200",
            description = "Content successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve Content. Verify 'Warning' header."
    )
    public Response get(@PathParam("id") Integer id) {

        audit.debug("Getting Content.");
        return Response.ok(serviceContent.get(id))
                .build();

    }

    @POST
    @Operation(summary = "Create a new Content for a Activity Type Version.")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @APIResponse(
            responseCode = "201",
            description = "Content successfully created."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to create Content. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to create Content. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to create new Content. Verify 'Warning' Header."
    )
    public Response create(DTOCreateContent dtoCreateContent) {

        if(dtoCreateContent == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        audit.debug(dtoCreateContent.getActivityTypeVersionId());
        Integer activityTypeVersionId = dtoCreateContent.getActivityTypeVersionId();
        audit.debug("ActivityType: " + activityTypeVersionId);
        byte[] model = dtoCreateContent.getModel();
        audit.debug("Model: " + model);
        if(!utilityVerifyRequestField.isValidField(activityTypeVersionId) ||
                !utilityVerifyRequestField.isValidField(model)) {
            throw new HttpBadRequestException("All fields required. (No empty files allowed.)");
        }

        audit.debug("Creating new Content...");
        DTOContent dtoContent = serviceContent.create(dtoCreateContent);

        audit.debug("Creating URI for new Activity Type Version");
        URI uri = URI.create(BASE_PATH_RESOURCE + dtoContent.getContentId());

        return Response.created(uri)
                .entity(dtoContent)
                .build();

    }

    @POST
    @Path("images")
    @Operation(summary = "Add Images to Content.")
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    @APIResponse(
            responseCode = "201",
            description = "Image successfully added."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to add Image. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to add Image. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to add Image. Verify 'Warning' Header."
    )
    //public Response create(DTOCreateContent dtoCreateContent) {
    public Response addImageToContent(DTOCreateImage dtoCreateImage) {

        if(dtoCreateImage == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        Integer content = dtoCreateImage.getContent();
        audit.debug("Content: " + content);
        String imageName = dtoCreateImage.getImageName();
        audit.debug("Image name: " + imageName);
        byte[] image = dtoCreateImage.getImage();
        audit.debug("Image: " + image);
        if(!utilityVerifyRequestField.isValidField(content) ||
                !utilityVerifyRequestField.isValidField(imageName) ||
                !utilityVerifyRequestField.isValidField(image)) {
            throw new HttpBadRequestException("All fields required. (No empty files allowed.)");
        }

        audit.debug("Adding Image to Content...");
        DTOImage dtoImage = serviceContent.addImage(dtoCreateImage);

        audit.debug("Creating URI for new Image");
        URI uri = URI.create(BASE_PATH_RESOURCE + dtoImage.getContentId() + "/images/" + dtoImage.getImageId());

        return Response.created(uri)
                .entity(dtoImage)
                .build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Delete a specific Content by its ID.")
    @APIResponse(
            responseCode = "200",
            description = "Content successfully deleted."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to delete Content. Verify 'Warning' Header."
    )
    public Response delete(@PathParam("id") Integer id) {

        audit.debug("Deleting Content " + id + "...");
        return Response.ok(serviceContent.delete(id)).build();

    }

    @GET
    @Path("{content}/images")
    @Operation(summary = "Retrieve all Images from Content.")
    @APIResponse(
            responseCode = "200",
            description = "Images successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve images. Verify 'Warning' header."
    )
    public Response getAllImage(@PathParam("content") Integer content) {

        audit.debug("Getting all Images from Content.");
        return Response.ok(serviceContent.getAllImages(content))
                .build();

    }

    @GET
    @Path("{content}/images/{id}")
    @Operation(summary = "Retrieve a Image File from Content.")
    @Produces({MediaType.APPLICATION_JSON, "image/png"})
    @APIResponse(
            responseCode = "200",
            description = "Image successfully retrieved.",
            content = @Content(mediaType = "image/png")
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve image. Verify 'Warning' header.",
            content = @Content(mediaType = "application.json")
    )
    public Response getImage(@PathParam("content") Integer content,
                             @PathParam("id") Integer image) {

        audit.debug("Getting Image from Content.");
        Object imageFile = serviceContent.getImage(content, image);
        String type = imageFile != null ? "image/png" : "application/json"; // TODO Corregir esta asignación de respuesta (anda bien el endpoint pero la documentación de la respuesta no es correcta)
        return Response.ok(imageFile)
                .type(type)
                .build();

    }

    @GET
    @Path("{content}/model")
    @Operation(summary = "Retrieve a Model file from Content.")
    @APIResponse(
            responseCode = "200",
            description = "Model file successfully retrieved."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to retrieve Model file. Verify 'Warning' header."
    )
    public Response getModel(@PathParam("content") Integer content) {

        audit.debug("Getting Model from Content.");
        return Response.ok(serviceContent.getModel(content))
                .type("application/json")
                .build();

    }

    @PATCH
    @Path("{id}")
    @Operation( summary = "Update a Content.")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @APIResponse(
            responseCode = "200",
            description = "Content updated successfully."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to update Content. Verify 'Warning' header."
    )
    public Response update(@PathParam("id") Integer id, DTOUpdateContent dtoUpdateContent) {

        if(dtoUpdateContent == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        Integer content = dtoUpdateContent.getContent();
        byte [] model = dtoUpdateContent.getModel();
        if(!utilityVerifyRequestField.isValidField(content) &&
                !utilityVerifyRequestField.isValidField(model)) {
            throw new HttpBadRequestException("No updates to make.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(id.compareTo(dtoUpdateContent.getContent()) != 0) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        audit.debug("Updating Content " + id + "...");
        return Response.ok(serviceContent.update(id, dtoUpdateContent)).build();

    }

    @PATCH
    @Path("{content}/images/{id}")
    @Operation( summary = "Update a Image file from Content.")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @APIResponse(
            responseCode = "200",
            description = "Image File updated successfully."
    )
    @APIResponse(
            responseCode = "204",
            description = "Failed to update Image File. Verify 'Warning' header."
    )
    public Response updateImage(@PathParam("content") Integer contentId, @PathParam("id") Integer imageId,
                                DTOUpdateContentImage dtoUpdateContentImage) {

        if(dtoUpdateContentImage == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        Integer content = dtoUpdateContentImage.getContent();
        Integer image = dtoUpdateContentImage.getImage();
        byte [] imageFile = dtoUpdateContentImage.getImageFile();
        if(!utilityVerifyRequestField.isValidField(content) &&
                !utilityVerifyRequestField.isValidField(image) &&
                !utilityVerifyRequestField.isValidField(imageFile)) {
            throw new HttpBadRequestException("No updates to make.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(contentId.compareTo(dtoUpdateContentImage.getContent()) != 0 ||
                imageId.compareTo(dtoUpdateContentImage.getImage()) != 0) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same.");
        }

        audit.debug("Updating Image " + imageId + " from Content " + contentId + "...");
        return Response.ok(serviceContent.updateImage(dtoUpdateContentImage)).build();

    }

    // VOTES HANDLING IN Content
    @POST
    @Path("{id}/votes")
    @Operation(summary = "Vote Content.")
    @APIResponse(
            responseCode = "201",
            description = "Content successfully voted."
    )
    @APIResponse(
            responseCode = "400",
            description = "Failed to Vote Content. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "404",
            description = "Failed to Vote Content. Verify 'Warning' Header."
    )
    @APIResponse(
            responseCode = "500",
            description = "Failed to Vote Content. Verify 'Warning' Header."
    )
    public Response vote(@PathParam("id") Integer idContent,
                         DTOCreateVote dtoCreateVote) {

        if(dtoCreateVote == null) {
            throw new HttpBadRequestException("Body of request required.");
        }

        Integer user = dtoCreateVote.getUser();
        Integer content = dtoCreateVote.getEntity();
        if(!utilityVerifyRequestField.isValidField(user) ||
                !utilityVerifyRequestField.isValidField(content)) {
            throw new HttpBadRequestException("All fields required.");
        }

        audit.debug("Verifying if the ID of the Body and the Path are the same...");
        if(idContent.compareTo(dtoCreateVote.getEntity()) != 0) {
            throw new HttpBadRequestException("Body ID and Path ID must be the same for Content.");
        }
        audit.debug("Vote of User " + user
                + " in Content " + idContent + "...");
        DTOVote dtoVote = serviceContent.vote(idContent, user);

        audit.debug("Creating URI...");
        URI uri = URI.create(BASE_PATH_RESOURCE_VOTE + dtoVote.getVoteId());

        return Response.created(uri).entity(dtoVote).build();

    }

}
