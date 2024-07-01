package ciudadano.consciente.service;

import ciudadano.consciente.access.*;
import ciudadano.consciente.dto.DTOReference;
import ciudadano.consciente.dto.DTOVote;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.mapper.MapperVote;
import ciudadano.consciente.model.*;
import ciudadano.consciente.dto.DTOUpdateReference;
import ciudadano.consciente.dto.DTOCreateReference;
import ciudadano.consciente.mapper.MapperReference;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceReference {

  final String ENTITY_NAME = "Reference";

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

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOReference create(DTOCreateReference dtoCreateReference) {

    Integer levelDto = dtoCreateReference.getLevel();
    Level level = accessLevel.get(levelDto)
        .orElseThrow(() -> new HttpNoContentException("Level not found."));

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
  public DTOReference update(Integer id, DTOUpdateReference dtoUpdateReference) {

    Reference reference = accessReference.get(id)
        .orElseThrow(() -> new HttpNoContentException("Reference not found."));

    Integer level = dtoUpdateReference.getLevel();
    if (utilityVerifyRequestField.isValidField(level)) {
      reference.setLevel(accessLevel.get(level)
          .orElseThrow(() -> new HttpNoContentException("Level not found.")));
    }

    String title = dtoUpdateReference.getTitle();
    if (utilityVerifyRequestField.isValidField(title)) {
      if (accessReference.existsTitleInLevel(reference.getLevel(), title)) {
        throw new HttpBadRequestException("Already exists a Reference with that title in Level.");
      }
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
  public DTOReference delete(Integer id) {

    audit.debug("Deleting Reference " + id + ".");
    Reference reference = accessReference.get(id)
        .orElseThrow(() -> new HttpNoContentException("Reference not found."));

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

}
