package ciudadano.consciente.service;

import ciudadano.consciente.access.*;
import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.AuthDenialSecurityException;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.mapper.MapperTaggedEntity;
import ciudadano.consciente.mapper.MapperVote;
import ciudadano.consciente.mapper.MapperVotedEntity;
import ciudadano.consciente.model.*;
import ciudadano.consciente.mapper.MapperReference;
import ciudadano.consciente.utility.UtilityAuthVerifier;
import ciudadano.consciente.utility.UtilityMetadataClasses;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceReference {

  final String ENTITY_NAME = UtilityMetadataClasses.getTableName(Reference.class);

  @Inject
  UtilityVerifyRequestField utilityVerifyRequestField;

  @Inject
  AccessLevel accessLevel;

  @Inject
  AccessReference accessReference;

  @Inject
  MapperReference mapperReference;

  @Inject
  Logger audit;

  @Inject
  AccessEntityType accessEntityType;

  @Inject
  AccessVote accessVote;

  @Inject
  MapperVote mapperVote;

  @Inject
  AccessUser accessUser;
  
  @Inject
  AccessVotedReference accessVotedReference;
  
  @Inject
  MapperVotedEntity mapperVotedEntity;

  @Inject
  AccessTaggedReference accessTaggedReference;

  @Inject
  MapperTaggedEntity mapperTaggedEntity;

  @Inject
  AccessUserRoleLevel accessUserRoleLevel;

  public List<DTOReference> getAll() {

    audit.debug("Retrieving all References.");
    return mapperReference.entityToDto(accessReference.getAll());

  }

  public DTOReference get(Integer id) {

    audit.debug("Retrieving Reference " + id + ".");
    Reference reference = accessReference.get(id)
        .orElseThrow(() -> new HttpNoContentException("Reference not found."));

    return mapperReference.entityToDto(reference);

  }

  public List<DTOReference> getByLevel(Integer levelId) {

    Level level = accessLevel.get(levelId)
            .orElseThrow(() -> new HttpNoContentException("Level not found."));
    return mapperReference.entityToDto(accessReference.getByLevel(level));

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOReference create(DTOCreateReference dtoCreateReference, UtilityAuthVerifier.UserAuthData userAuthData) {

    Integer levelDto = dtoCreateReference.getLevel();
    Level level = accessLevel.get(levelDto)
        .orElseThrow(() -> new HttpNoContentException("Level not found."));

    // If user doesnt have ORG Roles, mut have levels roles
    if (!userAuthData.hasOrgRoles(level.getOrganization().getOrganizationId())) {
      boolean authorizedInLevel = false;
      List<UserRoleLevel> userRoleLevelList = accessUserRoleLevel.getAncestorByLevel(level);
      for (UserRoleLevel url : userRoleLevelList) {
        if (userAuthData.hasLevelRoles(level.getLevelId())) {
          authorizedInLevel = true;
          break;
        }
      }
      if (!authorizedInLevel) {
        throw new AuthDenialSecurityException("Mismatch: User is not allowed to create References in Level.");
      }
    }

    String title = dtoCreateReference.getTitle();
    audit.debug("Verifying if title " + title + " of Reference already exists in Level " + levelDto);
    if (accessReference.existsTitleInLevel(level, title)) {
      throw new HttpBadRequestException("Already exists a Reference with that title in Level.");
    }

    audit.debug("Creating Reference.");
    Reference reference = mapperReference.dtoToEntity(dtoCreateReference);

    audit.debug("Saving Reference " + reference.getReferenceId() + ".");
    accessReference.save(reference)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist new Reference."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperReference.entityToDto(reference);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOReference update(Integer id, DTOUpdateReference dtoUpdateReference, UtilityAuthVerifier.UserAuthData userAuthData) {

    Reference reference = accessReference.get(id)
        .orElseThrow(() -> new HttpNoContentException("Reference not found."));


    Level referenceLevel = accessLevel.get(reference.getLevel().getLevelId())
            .orElseThrow(() -> new HttpNoContentException("Level not found."));

    // If user doesnt have ORG Roles, mut have levels roles
    if (!userAuthData.hasOrgRoles(referenceLevel.getOrganization().getOrganizationId())) {
      boolean authorizedInLevel = false;
      List<UserRoleLevel> userRoleLevelList = accessUserRoleLevel.getAncestorByLevel(referenceLevel);
      for (UserRoleLevel url : userRoleLevelList) {
        if (userAuthData.hasLevelRoles(referenceLevel.getLevelId())) {
          authorizedInLevel = true;
          break;
        }
      }
      if (!authorizedInLevel) {
        throw new AuthDenialSecurityException("Mismatch: User is not allowed to update References in Level.");
      }
    }

    Integer level = dtoUpdateReference.getLevel();
    if (utilityVerifyRequestField.isValidField(level)) {

      Level newLevel = accessLevel.get(level)
              .orElseThrow(() -> new HttpNoContentException("Level not found."));

      // If user doesnt have ORG Roles, mut have levels roles
      if (!userAuthData.hasOrgRoles(newLevel.getOrganization().getOrganizationId())) {
        boolean authorizedInNewLevel = false;
        List<UserRoleLevel> userRoleLevelList = accessUserRoleLevel.getAncestorByLevel(newLevel);
        for (UserRoleLevel url : userRoleLevelList) {
          if (userAuthData.hasLevelRoles(newLevel.getLevelId())) {
            authorizedInNewLevel = true;
            break;
          }
        }
        if (!authorizedInNewLevel) {
          throw new AuthDenialSecurityException("Mismatch: User is not allowed to update References in Level.");
        }
      }

      reference.setLevel(newLevel);
    }

    String title = dtoUpdateReference.getTitle();
    if (utilityVerifyRequestField.isValidField(title)) {
      //if (accessReference.existsTitleInLevel(reference.getLevel(), title)) {
      //  throw new HttpBadRequestException("Already exists a Reference with that title in Level.");
      //}
      reference.setTitle(title);
    }

    audit.debug("Updating Reference " + id + ".");
    String url = dtoUpdateReference.getUrl();
    String description = dtoUpdateReference.getDescription();

    if (utilityVerifyRequestField.isValidField(url)) {
      reference.setUrl(url);
    }

    if (utilityVerifyRequestField.isValidField(description)) {
      reference.setDescription(description);
    }

    audit.debug("Saving Reference " + reference.getReferenceId() + ".");
    accessReference.save(reference)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated Reference."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperReference.entityToDto(reference);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOReference delete(Integer id, UtilityAuthVerifier.UserAuthData userAuthData) {

    audit.debug("Deleting Reference " + id + ".");
    Reference reference = accessReference.get(id)
        .orElseThrow(() -> new HttpNoContentException("Reference not found."));

    Level referenceLevel = accessLevel.get(reference.getLevel().getLevelId())
            .orElseThrow(() -> new HttpNoContentException("Level not found."));

    // If user doesnt have ORG Roles, mut have levels roles
    if (!userAuthData.hasOrgRoles(referenceLevel.getOrganization().getOrganizationId())) {
      boolean authorizedInLevel = false;
      List<UserRoleLevel> userRoleLevelList = accessUserRoleLevel.getAncestorByLevel(referenceLevel);
      for (UserRoleLevel url : userRoleLevelList) {
        if (userAuthData.hasLevelRoles(referenceLevel.getLevelId())) {
          authorizedInLevel = true;
          break;
        }
      }
      if (!authorizedInLevel) {
        throw new AuthDenialSecurityException("Mismatch: User is not allowed to update References in Level.");
      }
    }

    if (!accessReference.remove(reference.getReferenceId())) {
      throw new HttpInternalServerException("Failed to delete Reference");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperReference.entityToDto(reference);

  }

  @Deprecated(since = "1.0.1")
  @Transactional(Transactional.TxType.REQUIRED)
  public DTOVote vote(Integer idReference, Integer idUser) {

    audit.debug("Retrieving Entity Type");
    EntityType entityType = accessEntityType.getByName(ENTITY_NAME)
        .orElseThrow(() -> new HttpNoContentException("Entity Type not found."));

    Reference reference = accessReference.get(idReference)
        .orElseThrow(() -> new HttpNoContentException("Reference not found."));

    User user = accessUser.get(idUser)
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    audit.debug("Verify if Vote already exists.");
    if (accessVote.getByKeys(user, reference.getReferenceId(), entityType).isPresent()) {
      throw new HttpBadRequestException("Vote already exists.");
    }

    audit.debug("Creating Vote for Reference.");
    Vote vote = new Vote(user, reference.getReferenceId(), entityType);

    audit.debug("Saving Vote.");
    accessVote.save(vote)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist new Vote."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperVote.entityToDto(vote);

  }

  public List<DTOVotedEntity> getAllVotes() {

    audit.debug("Retrieving all votes from References.");
    return mapperVotedEntity.votedReferenceEntityToDto((accessVotedReference.getAllVotes()));

  }

  public List<DTOVotedEntity> getVotes(Integer id) {

    Reference reference = accessReference.get(id)
            .orElseThrow(() -> new HttpNoContentException("Reference not found."));

    return mapperVotedEntity.votedReferenceEntityToDto(accessVotedReference.getVotes(reference));

  }

  public List<DTOTaggedEntity> getAllTags() {

    audit.debug("Retrieving all tags from References.");
    return mapperTaggedEntity.taggedReferenceEntityToDto((accessTaggedReference.getAllTags()));

  }

  public List<DTOTaggedEntity> getTags(Integer id) {

    Reference reference = accessReference.get(id)
            .orElseThrow(() -> new HttpNoContentException("Reference not found."));

    return mapperTaggedEntity.taggedReferenceEntityToDto(accessTaggedReference.getTags(reference));

  }

}
