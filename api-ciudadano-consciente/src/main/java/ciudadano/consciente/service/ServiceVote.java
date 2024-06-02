package ciudadano.consciente.service;

import ciudadano.consciente.access.*;
import ciudadano.consciente.dto.DTOVote;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.mapper.MapperVote;
import ciudadano.consciente.model.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceVote {

  @Inject
  Logger audit;

  @Inject
  AccessVote accessVote;

  @Inject
  AccessOrganization accessOrganization;

  @Inject
  AccessConcern accessConcern;

  @Inject
  AccessReference accessReference;

  @Inject
  AccessLevel accessLevel;

  @Inject
  AccessActivityType accessActivityType;

  @Inject
  AccessActivityTypeVersion accessActivityTypeVersion;

  @Inject
  AccessContent accessContent;

  @Inject
  MapperVote mapperVote;

  @Inject
  AccessUser accessUser;

  @Inject
  AccessEntityType accessEntityType;

  public List<DTOVote> getAll() {

    audit.debug("Getting all Answers.");
    return mapperVote.entityToDto(accessVote.getAll());

  }

  public DTOVote get(Integer id) {

    audit.debug("Getting Vote " + id + ".");
    Vote vote = accessVote.get(id)
        .orElseThrow(() -> new HttpNotFoundException("Vote not found."));

    audit.debug("Mapping Vote into DTO.");
    return mapperVote.entityToDto(vote);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOVote updateStatus(Integer id) {

    Vote vote = accessVote.get(id)
        .orElseThrow(() -> new HttpNotFoundException("Vote not found."));

    audit.debug("Updating Vote " + id + ".");
    vote.setActive(false);

    audit.debug("Saving Vote " + vote.getVoteId() + ".");
    accessVote.save(vote)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated Vote."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperVote.entityToDto(vote);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOVote voteEntity(Integer userId, Integer entityTypeId, Integer entityId) {

    audit.debug("Retrieving User.");
    User user = accessUser.get(userId)
        .orElseThrow(() -> new HttpNotFoundException("User not found."));

    audit.debug("Retrieving Entity Type.");
    EntityType entityType = accessEntityType.get(entityTypeId)
        .orElseThrow(() -> new HttpNotFoundException("Entity Type not found."));

    // TODO Verificar que existe esa entidad
    // ¿Cómo determinar su tipo y acceder a su accesor para recuperarla?
    // Algo de interfaces hay que implementar
    Votable entity = retrieveEntity(entityType, entityId);

    audit.debug("Votting " + entityType.getTitle() + " Entity.");
    Vote vote = new Vote(user, entity.getId(), entityType);

    audit.debug("Saving Voted Entity.");
    try {
      accessVote.save(vote)
          .orElseThrow(() -> new HttpInternalServerException("Failed to persist new Vote."));
    } catch (ConstraintViolationException e) {
      audit.debug("Vote already exists.");
      throw new HttpBadRequestException("Vote already exists.");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperVote.entityToDto(vote);

  }

  private Votable retrieveEntity(EntityType entityType, Integer entityId) {

    String title = entityType.getTitle();
    switch (title) {
      case "Organization":
        return accessOrganization.get(entityId)
            .orElseThrow(() -> new HttpNoContentException("Organization not found."));
      case "Concern":
        return accessConcern.get(entityId)
            .orElseThrow(() -> new HttpNoContentException("Concern not found."));
      case "Reference":
        return accessReference.get(entityId)
            .orElseThrow(() -> new HttpNoContentException("Reference not found."));
      case "Level":
        return accessLevel.get(entityId)
            .orElseThrow(() -> new HttpNoContentException("Level not found."));
      case "ActivityType":
        return accessActivityType.get(entityId)
            .orElseThrow(() -> new HttpNoContentException("Activity Type not found."));
      case "ActivityTypeVersion":
        return accessActivityTypeVersion.get(entityId)
            .orElseThrow(() -> new HttpNoContentException("Activity Type Version not found."));
      case "Content":
        return accessContent.get(entityId)
            .orElseThrow(() -> new HttpNoContentException("Content not found."));
      default:
        throw new HttpNoContentException("Entity Type can't be tagged");
    }

  }

}
