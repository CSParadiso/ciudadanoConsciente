package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessConcern;
import ciudadano.consciente.access.AccessEntityType;
import ciudadano.consciente.access.AccessUser;
import ciudadano.consciente.access.AccessVote;
import ciudadano.consciente.dto.DTOConcern;
import ciudadano.consciente.dto.DTOCreateConcern;
import ciudadano.consciente.dto.DTOUpdateConcern;
import ciudadano.consciente.dto.DTOVote;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.mapper.MapperConcern;
import ciudadano.consciente.mapper.MapperVote;
import ciudadano.consciente.model.*;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceConcern {

  final String ENTITY_NAME = "Concern";

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
  public DTOConcern create(DTOCreateConcern dtoCreateConcern) {

    Integer userDto = dtoCreateConcern.getUser();
    accessUser.get(userDto)
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    audit.debug("Creating Concern.");
    Concern concern = mapperConcern.dtoToEntity(dtoCreateConcern);

    audit.debug("Saving Concern.");
    try {
      accessConcern.save(concern)
          .orElseThrow(() -> new HttpInternalServerException("Failed to persist new Concern."));
    } catch (ConstraintViolationException e) {
      audit.debug("Concern already exists.");
      throw new HttpBadRequestException("Concern already exists.");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperConcern.entityToDto(concern);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOConcern update(Integer id, DTOUpdateConcern dtoUpdateConcern) {

    audit.debug("Retrieving Concern.");
    Concern concern = accessConcern.get(id)
        .orElseThrow(() -> new HttpNoContentException("Concern not found."));

    // audit.debug("Retrieving User.");
    // User user = accessUser.get(dtoUpdateConcern.getUser())
    // .orElseThrow( ()-> new HttpNoContentException("User not found.") );
    //
    // audit.debug("Verifying if user updating is the same in DB");
    // if(user != concern.getUser()) {
    // throw new HttpBadRequestException("Only User related to Concern can update
    // it.");
    // }

    audit.debug("Updating Concern " + id + ".");
    String description = dtoUpdateConcern.getDescription();
    if (utilityVerifyRequestField.isValidField(description)) {
      concern.setDescription(description);
    }

    String explanation = dtoUpdateConcern.getExplanation();
    if (utilityVerifyRequestField.isValidField(explanation)) {
      concern.setExplanation(explanation);
    }

    audit.debug("Saving Concern " + concern.getConcernId() + ".");
    accessConcern.save(concern)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated Concern."));

    audit.debug("Mapping EntityType into DTO.");
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

}
