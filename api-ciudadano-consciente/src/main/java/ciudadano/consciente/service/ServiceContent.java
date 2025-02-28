package ciudadano.consciente.service;

import ciudadano.consciente.access.*;
import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.*;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.mapper.*;
import ciudadano.consciente.model.*;
import ciudadano.consciente.utility.*;
import io.quarkus.oidc.UserInfo;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.jboss.logging.Logger;

import java.util.Arrays;
import java.util.List;

@RequestScoped
public class ServiceContent {

  final String ENTITY_NAME = UtilityMetadataClasses.getTableName(Content.class);

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
  
  @Inject
  AccessVotedContent accessVotedContent;
  
  @Inject
  MapperVotedEntity mapperVotedEntity;
  
  @Inject
  AccessTaggedContent accessTaggedContent;
  
  @Inject
  MapperTaggedEntity mapperTaggedEntity;

  @Inject
  UtilityVerifyRequestField utilityVerifyRequestField;

  @Inject
  AccessActivity accessActivity;

  public List<DTOContent> getAll() {

    audit.debug("Retrieving all Contents.");
    return mapperContent.entityToDto(accessContent.getAll());

  }

  public List<DTOContent> getAllPublic() {

    audit.debug("Retrieving all public Contents.");
    return mapperContent.entityToDto(accessContent.getAllPublic());

  }

  public List<DTOContent> getAllByOrganization(Integer organizationId, Boolean isPublic,
                                               UtilityAuthVerifier.UserAuthData userAuthData) {

    Organization organization = accessOrganization.get(organizationId)
                    .orElseThrow(()-> new HttpNoContentException("Organization not found."));

    if (isPublic == null && !userAuthData.hasOrgRoles(organization.getOrganizationId())) {
      isPublic = true;
    }

    if (isPublic != null && !isPublic && !userAuthData.hasOrgRoles(organization.getOrganizationId())) {
      throw new AuthDenialSecurityException("Mismatch: User is not allowed to retrieve private Contents of " +
              "Organization.");
    }

    return mapperContent.entityToDto(accessContent.getAllByOrganization(organization, isPublic));

  }

  public List<DTOContent> getAllByUser(Integer userId, Boolean isPublic, UtilityAuthVerifier.UserAuthData userAuthData) {

    User userRequester = accessUser.getByEmail(userAuthData.getUserInfo().getEmail())
            .orElseThrow(()-> new HttpNoContentException("User not found not found."));

    User user = accessUser.get(userId)
            .orElseThrow(()-> new HttpNoContentException("User not found not found."));

    boolean selfRequested = userRequester.getAuthServerId() == user.getAuthServerId();

    // If trying to retrieve all, verify if requester is the same user as creator
    if (isPublic == null && !selfRequested) {
      isPublic = true;
    }

    if (isPublic != null && !isPublic && !selfRequested) {
      throw new AuthDenialSecurityException("Mismatch: User is not allowed to retrieve private Contents of another " +
              "Creator.");
    }

    return mapperContent.entityToDto(accessContent.getAllByUser(user, isPublic));

  }

  public DTOContent get(Integer id, UtilityAuthVerifier.UserAuthData userAuthData) {

    Content content = accessContent.get(id)
            .orElseThrow(() -> new HttpNoContentException("Content not found."));

//    // If user is not Ciuco-Admin and content is private
//    if(!userAuthData.isCiucoAdmin() && !content.isPublicContent()) {
//
//      // If Content doesn't have ORG, User must be Creator to see private Content
//      if(content.getOrganization() == null) {
//        User user = accessUser.getByEmail(userAuthData.getUserInfo().getEmail())
//                .orElseThrow( () -> new HttpNoContentException("User not found."));
//
//        // Verify is User is creator of Content
//        if(user.getUserId() != content.getCreator().getUserId()) {
//          throw new AuthDenialSecurityException("Mismatch: User is not Creator of Content.");
//        }
//        // Verify if User belongs to ORG of Content
//      } else if (!userAuthData.hasOrgRoles(content.getOrganization().getOrganizationId())) {
//        throw new AuthDenialSecurityException("Mismatch: User is not allowed to retrieve Contents of Organization.");
//      }
//
//    }

    //audit.debug("Mapping Entity into DTO.");
    return mapperContent.entityToDto(content);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOContent create(DTOCreateContent dtoCreateContent, UtilityAuthVerifier.UserAuthData userAuthData) {

    audit.debug("Verifying files format.");
    byte[] modelFile = dtoCreateContent.getModel();
    if (!utilityFileSignature.detectFileType(modelFile).equals("json")) {
      throw new HttpBadRequestException("Model file is not a valid .json file");
    }
    // Make it String to allow save it as json
    String model = new String(modelFile);
    String description = dtoCreateContent.getDescription();

    //audit.debug("Verifying if ActivityTypeVersion exists.");
    //Integer version = dtoCreateContent.getActivityTypeVersionId();
    ActivityTypeVersion activityTypeVersion = accessActivityTypeVersion.get(dtoCreateContent.getActivityTypeVersionId())
        .orElseThrow(() -> new HttpNoContentException("Activity Type Version not found."));

    if (activityTypeVersion.getActivityTypeVersionStatusId().getTitle().equals("DELETED")) {
      throw new HttpNoContentException("Activity Type Version has been deleted.");
    }

    User creator = accessUser.getByEmail(userAuthData.getUserInfo().getEmail())
                    .orElseThrow( ()-> new HttpNoContentException("User not found.") );

    Organization organization = null;
    if(dtoCreateContent.getOrganization() != null) {

      organization = accessOrganization.get(dtoCreateContent.getOrganization())
              .orElseThrow( ()-> new HttpNoContentException("Organization not found.") );

       // If User doesn't have role in ORG
       if (!userAuthData.hasOrgRoles(organization.getOrganizationId())) {
         throw new AuthDenialSecurityException("Mismatch: User is not allowed to submit Content to Organization.");
       }

    }

    audit.debug("Creating Content.");
    Content content = new Content(activityTypeVersion, model, creator, dtoCreateContent.isPublicContent(),
            organization, description);

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
  public DTOContent update(Integer id, DTOUpdateContent dtoUpdateContent, UtilityAuthVerifier.UserAuthData userAuthData) {

    Content content = accessContent.get(dtoUpdateContent.getContent())
        .orElseThrow(() -> new HttpNoContentException("Content not found."));

    // If Content doesn't have ORG, User must be Creator to update Content
    if(content.getOrganization() == null) {
      User user = accessUser.getByEmail(userAuthData.getUserInfo().getEmail())
              .orElseThrow( () -> new HttpNoContentException("User not found."));

      // Verify is User is creator of Content
      if(user.getUserId() != content.getCreator().getUserId()) {
        throw new AuthDenialSecurityException("Mismatch: User is not Creator of Content.");
      }
      // Verify if User belongs to ORG of Content
    } else if (!userAuthData.hasOrgRoles(content.getOrganization().getOrganizationId())) {
      throw new AuthDenialSecurityException("Mismatch: User is not allowed to update Content of Organization.");
    }

    byte[] modelFile = dtoUpdateContent.getModel();
    if (modelFile.length != 0) {
      if (!utilityFileSignature.detectFileType(modelFile).equals("json")) {
        throw new HttpBadRequestException("Model file is not a valid .json file");
      } else {
        // Make it String to allow save it as json
        String model = new String(modelFile);
        content.setModel(model);
      }
    }


    String description = dtoUpdateContent.getDescription();
    if(utilityVerifyRequestField.isValidField(description)) {
      content.setDescription(description);
    }

    Boolean publicContent = dtoUpdateContent.getPublicContent();
    if(utilityVerifyRequestField.isValidField(publicContent)) {
      content.setPublicContent(publicContent);
    }

    try {
      accessContent.save(content)
          .orElseThrow(() -> new HttpInternalServerException("Failed to update Content."));
    } catch (DataException e) {
      throw new HttpBadRequestException("Uploaded model file is not correct." + e);
    }

    return mapperContent.entityToDto(content);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOImage addImage(DTOCreateImage dtoCreateImage, UtilityAuthVerifier.UserAuthData userAuthData) {

    //audit.debug("Verifying if Content exists.");
    //Integer contentId = dtoCreateImage.getContent();
    Content content = accessContent.get(dtoCreateImage.getContent())
        .orElseThrow(() -> new HttpNoContentException("Content not found."));

    audit.debug("Verifying extension and size of file."); // Todo esto podría ir en FileSystem (incluso la extension de
                                                          // los files allowed)
    List<String> allowedImagesExtensions = Arrays.asList("png", "gif", "jpg", "jpeg"); // Allowed images
    byte[] imageFile = dtoCreateImage.getImage();
    String fileType = utilityFileSignature.detectFileType(imageFile);
    if (!allowedImagesExtensions.contains(fileType)) {
      throw new HttpBadRequestException("Extension of file (." + fileType + ") not allowed.");
    }
    if (!utilityFileSystem.smallerThanMaxMbAllowed(imageFile.length)) {
      throw new HttpBadRequestException(
          "File size is larger than allowed (" + utilityFileSystem.getImageMaxMbFileSize() + "MB).");
    }

    // If Content doesn't have ORG, User must be Creator to add Image
    if(content.getOrganization() == null) {
      User user = accessUser.getByEmail(userAuthData.getUserInfo().getEmail())
              .orElseThrow( () -> new HttpNoContentException("User not found."));

      // Verify is User is creator of Content
      if(user.getUserId() != content.getCreator().getUserId()) {
        throw new AuthDenialSecurityException("Mismatch: User is not Creator of Content.");
      }
      // Verify if User belongs to ORG of Content
    } else if (!userAuthData.hasOrgRoles(content.getOrganization().getOrganizationId())) {
      throw new AuthDenialSecurityException("Mismatch: User is not allowed to submit Image to Content of " +
              "Organization.");
    }

    audit.debug("Mapping DTO into Entity");
    Image image = mapperImage.dtoToEntity(dtoCreateImage);

    audit.debug("Saving Content Images.");
    // Save to DB (image metadata) Should ignore image itself
    try {
      accessImage.save(image)
          .orElseThrow(() -> new HttpInternalServerException("Failed to save Image to DB."));
    } catch (ConstraintViolationException e) {
      audit.debug("Image name already exists in Content: " + e.getErrorMessage());
      throw new HttpBadRequestException("Image name already exists in Content: " + e.getErrorMessage());
    }

    // Save to FileSystem (image)
    utilityFileSystem.saveContentImageToFileSystem(content.getContentId().toString(), image.getImageName(),
        image.getImage());

    audit.debug("Mapping Entity into DTO.");
    return mapperImage.entityToDto(image);

  }

  public Object getImage(Integer contentId, Integer imageId, UtilityAuthVerifier.UserAuthData userAuthData) {

    Content content = accessContent.get(contentId)
        .orElseThrow(() -> new HttpNoContentException("Content not found."));

    // If user is not Ciuco-Admin and content is private
//    if(!userAuthData.isCiucoAdmin() && !content.isPublicContent()) {
//
//      // If Content doesn't have ORG, User must be Creator to retrieve Image of private Content
//      if (content.getOrganization() == null) {
//        User user = accessUser.getByEmail(userAuthData.getUserInfo().getEmail())
//                .orElseThrow(() -> new HttpNoContentException("User not found."));
//
//        // Verify is User is creator of Content
//        if (user.getUserId() != content.getCreator().getUserId()) {
//          throw new AuthDenialSecurityException("Mismatch: User is not Creator of Content.");
//        }
//        // Verify if User belongs to ORG of Content
//      } else if (!userAuthData.hasOrgRoles(content.getOrganization().getOrganizationId())) {
//        throw new AuthDenialSecurityException("Mismatch: User is not allowed to retrieve Content Image of " +
//                "Organization.");
//      }
//
//    }

    Image image = accessImage.get(imageId)
        .orElseThrow(() -> new HttpNoContentException("Image not found."));
    // TODO Quizás se deba persistir el nombre compuesto y no normalizado para no
    // hacer esta concatenación manual
    return utilityFileSystem.getContentImages(content.getContentId().toString(), image.getImageName());

  }

  public List<DTOImage> getAllImages(Integer contentId, UtilityAuthVerifier.UserAuthData userAuthData) {

    Content content = accessContent.get(contentId)
        .orElseThrow(() -> new HttpNoContentException("Content not found."));

//    // If user is not Ciuco-Admin and content is private
//    if(!userAuthData.isCiucoAdmin() && !content.isPublicContent()) {
//
//      // If Content doesn't have ORG, User must be Creator to retrieve Images of private Content
//      if(content.getOrganization() == null) {
//        User user = accessUser.getByEmail(userAuthData.getUserInfo().getEmail())
//                .orElseThrow( () -> new HttpNoContentException("User not found."));
//
//        // Verify is User is creator of Content
//        if(user.getUserId() != content.getCreator().getUserId()) {
//          throw new AuthDenialSecurityException("Mismatch: User is not Creator of Content.");
//        }
//        // Verify if User belongs to ORG of Content
//      } else if (!userAuthData.hasOrgRoles(content.getOrganization().getOrganizationId())) {
//        throw new AuthDenialSecurityException("Mismatch: User is not allowed to retrieve Content Images of " +
//                "Organization.");
//      }
//
//    }

    List<Image> imageList = accessImage.getImageByContent(content);

    return mapperImage.dtoToEntity(imageList);

  }

  public Object getModel(Integer contentId, UtilityAuthVerifier.UserAuthData userAuthData) {

    Content content = accessContent.get(contentId)
        .orElseThrow(() -> new HttpNoContentException("Content not found."));

    // If Content doesn't have ORG, User must be Creator to retrieve Images of private Content
//    if(content.getOrganization() == null) {
//      User user = accessUser.getByEmail(userAuthData.getUserInfo().getEmail())
//              .orElseThrow( () -> new HttpNoContentException("User not found."));
//
//      // Verify is User is creator of Content
//      if(user.getUserId() != content.getCreator().getUserId()) {
//        throw new AuthDenialSecurityException("Mismatch: User is not Creator of Content.");
//      }
//      // Verify if User belongs to ORG of Content
//    } else if (!userAuthData.hasOrgRoles(content.getOrganization().getOrganizationId())) {
//      throw new AuthDenialSecurityException("Mismatch: User is not allowed to retrieve Content Model of " +
//              "Organization.");
//    }

    return content.getModel();

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOContent delete(Integer id, UtilityAuthVerifier.UserAuthData userAuthData) {

    Content content = accessContent.get(id)
        .orElseThrow(() -> new HttpNoContentException("Content not found."));

    // If Content doesn't have ORG, User must be Creator to delete Content
    if(content.getOrganization() == null) {
      User user = accessUser.getByEmail(userAuthData.getUserInfo().getEmail())
              .orElseThrow( () -> new HttpNoContentException("User not found."));

      // Verify is User is creator of Content
      if(user.getUserId() != content.getCreator().getUserId()) {
        throw new AuthDenialSecurityException("Mismatch: User is not Creator of Content.");
      }
      // Verify if User belongs to ORG of Content
    } else if (!userAuthData.hasOrgRoles(content.getOrganization().getOrganizationId())) {
      throw new AuthDenialSecurityException("Mismatch: User is not allowed to delete Content of Organization.");
    }

    // DB Restriction. If the content is in an Activity, cant be deleted.
    if(!accessActivity.getByContent(content).isEmpty()) {
      audit.warn("[Content is currently available in an Activity. Can't be deleted.]");
      throw new HttpInternalServerException("Content is currently available in an Activity. Can't be deleted.");
    }

    //audit.debug("Deleting Content " + id + ".");
    accessContent.remove(content.getContentId());

    if(!accessImage.getImageByContent(content).isEmpty()) {
      utilityFileSystem.deleteContentDirectoryFromFileSystem(content.getContentId().toString());
    }

    //audit.debug("Mapping EntityType into DTO.");
    return mapperContent.entityToDto(content);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOImage deleteImage(Integer contentId, Integer imageId, UtilityAuthVerifier.UserAuthData userAuthData) {

    Content content = accessContent.get(contentId)
            .orElseThrow(() -> new HttpNoContentException("Content not found."));

    // If Content doesn't have ORG, User must be Creator to delete Image Content
    if(content.getOrganization() == null) {
      User user = accessUser.getByEmail(userAuthData.getUserInfo().getEmail())
              .orElseThrow( () -> new HttpNoContentException("User not found."));

      // Verify is User is creator of Content
      if(user.getUserId() != content.getCreator().getUserId()) {
        throw new AuthDenialSecurityException("Mismatch: User is not Creator of Content.");
      }
      // Verify if User belongs to ORG of Content
    } else if (!userAuthData.hasOrgRoles(content.getOrganization().getOrganizationId())) {
      throw new AuthDenialSecurityException("Mismatch: User is not allowed to delete Image of Content of Organization.");
    }

    Image image = accessImage.get(imageId)
            .orElseThrow(() -> new HttpNoContentException("Image not found."));

    if(accessImage.remove(image.getImageId())) {
      utilityFileSystem.deleteContentImageFromFileSystem(content.getContentId().toString(), image.getImageName());
    }

    return mapperImage.entityToDto(image);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public Object updateImage(DTOUpdateContentImage dtoUpdateContentImage, UtilityAuthVerifier.UserAuthData userAuthData) {

    Content content = accessContent.get(dtoUpdateContentImage.getContent())
        .orElseThrow(() -> new HttpNoContentException("Content not found."));

    // If Content doesn't have ORG, User must be Creator to update Contents Image
    if(content.getOrganization() == null) {
      User user = accessUser.getByEmail(userAuthData.getUserInfo().getEmail())
              .orElseThrow( () -> new HttpNoContentException("User not found."));

      // Verify is User is creator of Content
      if(user.getUserId() != content.getCreator().getUserId()) {
        throw new AuthDenialSecurityException("Mismatch: User is not Creator of Content.");
      }
      // Verify if User belongs to ORG of Content
    } else if (!userAuthData.hasOrgRoles(content.getOrganization().getOrganizationId())) {
      throw new AuthDenialSecurityException("Mismatch: User is not allowed to update Contents Image of Organization.");
    }

    Integer imageId = dtoUpdateContentImage.getImage();
    Image image = accessImage.get(imageId)
        .orElseThrow(() -> new HttpNoContentException("Image not found."));

    // Todo esto podría ir en FileSystem (incluso la extension de los files allowed)
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

    utilityFileSystem.deleteContentImageFromFileSystem(content.getContentId().toString(), image.getImageName());
    utilityFileSystem.saveContentImageToFileSystem(content.getContentId().toString(), image.getImageName(), imageFile);

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

  public List<DTOVotedEntity> getAllVotes() {

    return mapperVotedEntity.votedContentEntityToDto((accessVotedContent.getAllVotes()));

  }

  public List<DTOVotedEntity> getVotes(Integer id) {

    Content content = accessContent.get(id)
            .orElseThrow(() -> new HttpNoContentException("Content not found."));

    return mapperVotedEntity.votedContentEntityToDto(accessVotedContent.getVotes(content));

  }

  public List<DTOTaggedEntity> getAllTags() {

    audit.debug("Retrieving all tags from Contents.");
    return mapperTaggedEntity.taggedContentEntityToDto((accessTaggedContent.getAllTags()));

  }

  public List<DTOTaggedEntity> getTags(Integer id) {

    Content content = accessContent.get(id)
            .orElseThrow(() -> new HttpNoContentException("Content not found."));

    return mapperTaggedEntity.taggedContentEntityToDto(accessTaggedContent.getTags(content));

  }
  
}
