package ciudadano.consciente.service;

import ciudadano.consciente.access.*;
import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.*;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.mapper.MapperConcern;
import ciudadano.consciente.mapper.MapperTaggedEntity;
import ciudadano.consciente.mapper.MapperVote;
import ciudadano.consciente.mapper.MapperVotedEntity;
import ciudadano.consciente.model.*;
import ciudadano.consciente.utility.UtilityAuthVerifier;
import ciudadano.consciente.utility.UtilityMetadataClasses;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceConcern {

  final String ENTITY_NAME = UtilityMetadataClasses.getTableName(Concern.class);

  @Inject
  Logger audit;

  @Inject
  MapperConcern mapperConcern;

  @Inject
  AccessConcern accessConcern;

  @Inject
  AccessUser accessUser;

  @Inject
  UtilityVerifyRequestField utilityVerifyRequestField;

  @Inject
  AccessEntityType accessEntityType;

  @Inject
  MapperVote mapperVote;

  @Inject
  AccessVote accessVote;
  
  @Inject
  AccessVotedConcern accessVotedConcern;
  
  @Inject
  MapperVotedEntity mapperVotedEntity;

  @Inject
  AccessTaggedConcern accessTaggedConcern;

  @Inject
  MapperTaggedEntity mapperTaggedEntity;

  public List<DTOConcern> getAll() {

    audit.debug("Retrieving all Concerns.");
    return mapperConcern.entityToDto(accessConcern.getAll());

  }

  public DTOConcern get(Integer id) {

    audit.debug("Getting Concern " + id + ".");
    Concern concern = accessConcern.get(id)
        .orElseThrow(() -> new HttpNoContentException("Concern not found."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperConcern.entityToDto(concern);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOConcern create(DTOCreateConcern dtoCreateConcern, UtilityAuthVerifier.UserAuthData userAuthData) {

    User user = accessUser.getByAuthServerId(userAuthData.getUserInfo().getSubject())
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    Concern concern = mapperConcern.dtoToEntity(dtoCreateConcern, user);

    audit.debug("Saving Concern.");
    try {
      accessConcern.save(concern)
          .orElseThrow(() -> new HttpInternalServerException("Failed to persist new Concern."));
    } catch (ConstraintViolationException e) {
      audit.debug("Concern already exists: " + e.getErrorMessage());
      throw new HttpBadRequestException("Concern already exists: " + e.getErrorMessage());
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperConcern.entityToDto(concern);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOConcern update(DTOUpdateConcern dtoUpdateConcern, UtilityAuthVerifier.UserAuthData userAuthData) {

    Concern concern = accessConcern.get(dtoUpdateConcern.getConcernId())
        .orElseThrow(() -> new HttpNoContentException("Concern not found."));

    User user = accessUser.getByAuthServerId(userAuthData.getUserInfo().getSubject())
            .orElseThrow(() -> new HttpNoContentException("User not found."));

    if (concern.getUser().getUserId() != user.getUserId()) {
      throw new AuthDenialSecurityException("Mismatch: User is not allowed to update Concern.");
    }

    // audit.debug("Retrieving User.");
    // User user = accessUser.get(dtoUpdateConcern.getUser())
    // .orElseThrow( ()-> new HttpNoContentException("User not found.") );
    //
    // audit.debug("Verifying if user updating is the same in DB");
    // if(user != concern.getUser()) {
    // throw new HttpBadRequestException("Only User related to Concern can update
    // it.");
    // }

    String description = dtoUpdateConcern.getDescription();
    if (utilityVerifyRequestField.isValidField(description)) {
      concern.setDescription(description);
    }

    String explanation = dtoUpdateConcern.getExplanation();
    if (utilityVerifyRequestField.isValidField(explanation)) {
      concern.setExplanation(explanation);
    }

    accessConcern.save(concern)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated Concern."));

    return mapperConcern.entityToDto(concern);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOConcern delete(Integer id) {

    audit.debug("Deleting Concern " + id + ".");
    Concern concern = accessConcern.get(id)
        .orElseThrow(() -> new HttpNoContentException("Concern not found."));

    if (!accessConcern.remove(concern.getConcernId())) {
      throw new HttpInternalServerException("Failed to delete Concern");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperConcern.entityToDto(concern);

  }

  @Deprecated(since = "1.0.1")
  @Transactional(Transactional.TxType.REQUIRED)
  public DTOVote vote(Integer idConcern, Integer idUser) {

    audit.debug("Retrieving Entity Type");
    EntityType entityType = accessEntityType.getByName(ENTITY_NAME)
        .orElseThrow(() -> new HttpNoContentException("Entity Type not found."));

    Concern concern = accessConcern.get(idConcern)
        .orElseThrow(() -> new HttpNoContentException("Concern not found."));

    User user = accessUser.get(idUser)
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    audit.debug("Verify if Vote already exists.");
    if (accessVote.getByKeys(user, concern.getConcernId(), entityType).isPresent()) {
      throw new HttpBadRequestException("Vote already exists.");
    }

    audit.debug("Creating Vote for Concern.");
    Vote vote = new Vote(user, concern.getConcernId(), entityType);

    audit.debug("Saving Vote.");
    accessVote.save(vote)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist new Vote."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperVote.entityToDto(vote);

  }

  public List<DTOVotedEntity> getAllVotes() {

    audit.debug("Retrieving all votes from Concerns.");
    return mapperVotedEntity.votedConcernEntityToDto((accessVotedConcern.getAllVotes()));

  }

  public List<DTOVotedEntity> getVotes(Integer id) {

    Concern concern = accessConcern.get(id)
            .orElseThrow(() -> new HttpNoContentException("Concern not found."));

    return mapperVotedEntity.votedConcernEntityToDto(accessVotedConcern.getVotes(concern));

  }

  public List<DTOTaggedEntity> getAllTags() {

    audit.debug("Retrieving all tags from Concerns.");
    return mapperTaggedEntity.taggedConcernEntityToDto((accessTaggedConcern.getAllTags()));

  }

  public List<DTOTaggedEntity> getTags(Integer id) {

    Concern concern = accessConcern.get(id)
            .orElseThrow(() -> new HttpNoContentException("Concern not found."));

    return mapperTaggedEntity.taggedConcernEntityToDto(accessTaggedConcern.getTags(concern));

  }

  public List<DTOConcern> getAllByUser(Integer userId) {

    User user = accessUser.get(userId)
            .orElseThrow(()-> new HttpNoContentException("User not found not found."));

    return mapperConcern.entityToDto(accessConcern.getByUser(user));

  }

}
