package ciudadano.consciente.service;

import ciudadano.consciente.access.*;
import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.mapper.MapperContent;
import ciudadano.consciente.mapper.MapperImage;
import ciudadano.consciente.mapper.MapperVote;
import ciudadano.consciente.model.*;
import ciudadano.consciente.utility.UtilityFileSignature;
import ciudadano.consciente.utility.UtilityFileSystem;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.jboss.logging.Logger;

import java.util.Arrays;
import java.util.List;

@RequestScoped
public class ServiceContent {

  final String ENTITY_NAME = "Content";

  @Inject
  Logger audit;

  @Inject
  AccessActivityTypeVersion accessActivityTypeVersion;

  @Inject
  UtilityFileSignature utilityFileSignature;

  @Inject
  UtilityFileSystem utilityFileSystem;

  @Inject
  AccessContent accessContent;

  @Inject
  AccessImage accessImage;

  @Inject
  MapperImage mapperImage;

  @Inject
  MapperContent mapperContent;

  @Inject
  AccessUser accessUser;

  @Inject
  AccessEntityType accessEntityType;

  @Inject
  AccessVote accessVote;

  @Inject
  MapperVote mapperVote;

  @Inject
  AccessOrganization accessOrganization;

  public List<DTOContent> getAll() {

    audit.debug("Retrieving all Contents.");
    return mapperContent.entityToDto(accessContent.getAll());

  }

  public DTOContent get(Integer id) {

    audit.debug("Retrieving Content.");
    Content content = accessContent.get(id)
        .orElseThrow(() -> new HttpNoContentException("Content not found."));

    audit.debug("Mapping Entity into DTO.");
    return mapperContent.entityToDto(content);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOContent create(DTOCreateContent dtoCreateContent) {

    audit.debug("Verifying if ActivityTypeVersion exists.");
    Integer version = dtoCreateContent.getActivityTypeVersionId();
    ActivityTypeVersion activityTypeVersion = accessActivityTypeVersion.get(version)
        .orElseThrow(() -> new HttpNoContentException("Activity Type Version not found."));

    if (activityTypeVersion.getActivityTypeVersionStatusId().getTitle().equals("DELETED")) {
      throw new HttpNoContentException("Activity Type Version has been deleted.");
    }

    User creator = accessUser.get(dtoCreateContent.getCreator())
                    .orElseThrow( ()-> new HttpNoContentException("User not found.") );

    Organization organization = null;
    if(dtoCreateContent.getOrganization() != null) {
       organization = accessOrganization.get(dtoCreateContent.getOrganization())
              .orElseThrow( ()-> new HttpNoContentException("Organization not found.") );
    }

    audit.debug("Verifying files format.");
    byte[] modelFile = dtoCreateContent.getModel();
    if (!utilityFileSignature.detectFileType(modelFile).equals("json")) {
      throw new HttpBadRequestException("Model file is not a valid .json file");
    }
    // Make it String to allow save it as json
    String model = new String(modelFile);

    audit.debug("Creating Content.");
    Content content = new Content(activityTypeVersion, model, creator, organization, dtoCreateContent.isPublicContent());

    audit.debug("Saving new Content.");
    try {
      accessContent.save(content)
          .orElseThrow(() -> new HttpInternalServerException("Failed to create new Content."));
    } catch (DataException e) {
      audit.debug("Invalid files uploaded. " + e);
      throw new HttpBadRequestException("Uploaded model file is not correct." + e);
    }

    audit.debug("Mapping Entity into DTO.");
    return mapperContent.entityToDto(content);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOContent update(Integer id, DTOUpdateContent dtoUpdateContent) {

    Integer contentId = dtoUpdateContent.getContent();
    byte[] modelFile = dtoUpdateContent.getModel();

    Content content = accessContent.get(contentId)
        .orElseThrow(() -> new HttpNoContentException("Content not found."));

    audit.debug("Verifying file format.");
    if (!utilityFileSignature.detectFileType(modelFile).equals("json")) {
      throw new HttpBadRequestException("Model file is not a valid .json file");
    }
    // Make it String to allow save it as json
    String model = new String(modelFile);

    audit.debug("Updating Content " + id);
    content.setModel(model);

    audit.debug("Saving updated Content.");
    try {
      accessContent.save(content)
          .orElseThrow(() -> new HttpInternalServerException("Failed to update Content."));
    } catch (DataException e) {
      audit.debug("Invalid files uploaded. " + e);
      throw new HttpBadRequestException("Uploaded model file is not correct." + e);
    }

    audit.debug("Mapping Entity into DTO.");
    return mapperContent.entityToDto(content);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOImage addImage(DTOCreateImage dtoCreateImage) {

    audit.debug("Verifying if Content exists.");
    Integer contentId = dtoCreateImage.getContent();
    Content content = accessContent.get(contentId)
        .orElseThrow(() -> new HttpNoContentException("Content not found."));

    audit.debug("Verifying extension and size of file."); // Todo esto podría ir en FileSystem (incluso la extension de
                                                          // los files allowed)
    List<String> allowedImagesExtensions = Arrays.asList("png", "webp", "gif", "jpg", "jpeg"); // Allowed images
    byte[] imageFile = dtoCreateImage.getImage();
    String fileType = utilityFileSignature.detectFileType(imageFile);
    if (!allowedImagesExtensions.contains(fileType)) {
      throw new HttpBadRequestException("Extension of file (." + fileType + ") not allowed.");
    }
    if (!utilityFileSystem.smallerThanMaxMbAllowed(imageFile.length)) {
      throw new HttpBadRequestException(
          "File size is larger than allowed (" + utilityFileSystem.getImageMaxMbFileSize() + "MB).");
    }

    audit.debug("Mapping DTO into Entity");
    Image image = mapperImage.dtoToEntity(dtoCreateImage);

    audit.debug("Saving Content Images.");
    // Save to DB (image metadata) Should ignore image itself
    try {
      accessImage.save(image)
          .orElseThrow(() -> new HttpInternalServerException("Failed to save Image to DB."));
    } catch (ConstraintViolationException e) {
      audit.debug("Image name already exists in Content.");
      throw new HttpBadRequestException("Image name already exists in Content.");
    }

    // Save to FileSystem (image)
    utilityFileSystem.saveContentImageToFileSystem(content.getContentId().toString(), image.getImageName(),
        image.getImage());

    audit.debug("Mapping Entity into DTO.");
    return mapperImage.entityToDto(image);

  }

  public Object getImage(Integer contentId, Integer imageId) {

    audit.debug("Verifying if Content exists.");
    Content content = accessContent.get(contentId)
        .orElseThrow(() -> new HttpNoContentException("Content not found."));

    audit.debug("Verifying if Image exists.");
    Image image = accessImage.get(imageId)
        .orElseThrow(() -> new HttpNoContentException("Image not found."));
    // TODO Quizás se deba persistir el nombre compuesto y no normalizado para no
    // hacer esta concatenación manual
    return utilityFileSystem.getContentImages(content.getContentId().toString(), image.getImageName());

  }

  public List<DTOImage> getAllImages(Integer contentId) {

    audit.debug("Verifying if Content exists.");
    Content content = accessContent.get(contentId)
        .orElseThrow(() -> new HttpNoContentException("Content not found."));

    audit.debug("Verifying if Images exists.");
    List<Image> imageList = accessImage.getImageByContent(content);

    return mapperImage.dtoToEntity(imageList);

  }

  public Object getModel(Integer contentId) {

    audit.debug("Verifying if Content exists.");
    Content content = accessContent.get(contentId)
        .orElseThrow(() -> new HttpNoContentException("Content not found."));

    return content.getModel();

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOContent delete(Integer id) {

    audit.debug("Verifying if Content exists.");
    Content content = accessContent.get(id)
        .orElseThrow(() -> new HttpNoContentException("Content not found."));

    List<Image> imageList = accessImage.getImageByContent(content);
    audit.debug("Deleting Content images from File System");
    for (Image image : imageList) {
      utilityFileSystem.deleteContentDirectoryFromFileSystem(content.getContentId().toString());
    }

    audit.debug("Deleting Content " + id + ".");
    if (!accessContent.remove(content.getContentId())) {
      throw new HttpInternalServerException("Failed to delete Content");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperContent.entityToDto(content);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public Object updateImage(DTOUpdateContentImage dtoUpdateContentImage) {

    audit.debug("Verifying if Content exists.");
    Integer contentId = dtoUpdateContentImage.getContent();
    Content content = accessContent.get(contentId)
        .orElseThrow(() -> new HttpNoContentException("Content not found."));

    audit.debug("Verifying if Image exists.");
    Integer imageId = dtoUpdateContentImage.getImage();
    Image image = accessImage.get(imageId)
        .orElseThrow(() -> new HttpNoContentException("Image not found."));

    audit.debug("Verifying extension and size of file."); // Todo esto podría ir en FileSystem (incluso la extension de
                                                          // los files allowed)
    List<String> allowedImagesExtensions = Arrays.asList("png", "webp", "gif", "jpg", "jpeg"); // Allowed images
    byte[] imageFile = dtoUpdateContentImage.getImageFile();
    String fileType = utilityFileSignature.detectFileType(imageFile);
    if (!allowedImagesExtensions.contains(fileType)) {
      throw new HttpBadRequestException("Extension of file (." + fileType + ") not allowed.");
    }
    if (!utilityFileSystem.smallerThanMaxMbAllowed(imageFile.length)) {
      throw new HttpBadRequestException(
          "File size is larger than allowed (" + utilityFileSystem.getImageMaxMbFileSize() + "MB).");
    }

    audit.debug("Deleting existing Image.");
    utilityFileSystem.deleteContentImageFromFileSystem(content.getContentId().toString(), image.getImageName());
    audit.debug("Saving new Image.");
    utilityFileSystem.saveContentImageToFileSystem(content.getContentId().toString(), image.getImageName(), imageFile);

    audit.debug("Mapping Entity into DTO.");
    return mapperImage.entityToDto(image);

  }

  // VOTE HANDLING IN CONTENT
  @Deprecated(since = "1.0.1")
  @Transactional(Transactional.TxType.REQUIRED)
  public DTOVote vote(Integer idContent, Integer idUser) {

    audit.debug("Retrieving Entity Type");
    EntityType entityType = accessEntityType.getByName(ENTITY_NAME)
        .orElseThrow(() -> new HttpNoContentException("Entity Type not found."));

    Content Content = accessContent.get(idContent)
        .orElseThrow(() -> new HttpNoContentException("Content not found."));

    User user = accessUser.get(idUser)
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    audit.debug("Verify if Vote already exists.");
    if (accessVote.getByKeys(user, Content.getContentId(), entityType).isPresent()) {
      throw new HttpBadRequestException("Vote already exists.");
    }

    audit.debug("Creating Vote for Content.");
    Vote vote = new Vote(user, Content.getContentId(), entityType);

    audit.debug("Saving Vote.");
    accessVote.save(vote)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist new Vote."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperVote.entityToDto(vote);

  }

}
