package ciudadano.consciente.service;

import ciudadano.consciente.exception.HttpNoContentException;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

import ciudadano.consciente.access.AccessActivity;
import ciudadano.consciente.access.AccessActivityType;
import ciudadano.consciente.access.AccessActivityTypeVersion;
import ciudadano.consciente.access.AccessConcern;
import ciudadano.consciente.access.AccessContent;
import ciudadano.consciente.access.AccessEntityType;
import ciudadano.consciente.access.AccessLevel;
import ciudadano.consciente.access.AccessOrganization;
import ciudadano.consciente.access.AccessReference;
import ciudadano.consciente.access.AccessTag;
import ciudadano.consciente.access.AccessTagged;
import ciudadano.consciente.dto.DTOTagged;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.mapper.MapperTagged;
import ciudadano.consciente.model.ActivityType;
import ciudadano.consciente.model.Concern;
import ciudadano.consciente.model.EntityType;
import ciudadano.consciente.model.Level;
import ciudadano.consciente.model.Organization;
import ciudadano.consciente.model.Reference;
import ciudadano.consciente.model.Tag;
import ciudadano.consciente.model.Taggable;
import ciudadano.consciente.model.Tagged;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@RequestScoped
public class ServiceTagged {

  @Inject
  Logger audit;

  @Inject
  AccessTagged accessTagged;

  @Inject
  AccessEntityType accessEntityType;

  @Inject
  AccessTag accessTag;

  @Inject
  AccessReference accessReference;

  @Inject
  AccessOrganization accessOrganization;

  @Inject
  AccessLevel accessLevel;

  @Inject
  AccessContent accessContent;

  @Inject
  AccessActivityType accessActivityType;

  @Inject
  AccessActivityTypeVersion accessActivityTypeVersion;

  @Inject
  AccessConcern accessConcern;

  @Inject
  MapperTagged mapperTagged;

  public List<DTOTagged> getAll() {
    audit.debug("Retrieving all Concerns.");
    return mapperTagged.entityToDto(accessTagged.getAll());
  }

  public DTOTagged get(Integer id) {
    audit.debug("Retrieving  Tagged " + id + " .");
    return mapperTagged.entityToDto(accessTagged.get(id)
        .orElseThrow(() -> new HttpNoContentException("Tagged not found.")));
  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOTagged tagEntity(Integer tagId, Integer entityTypeId, Integer entityId) {

    audit.debug("Retrieving Tag.");
    Tag tag = accessTag.get(tagId)
        .orElseThrow(() -> new HttpNotFoundException("Tag not found."));

    audit.debug("Retrieving Entity Type.");
    EntityType entityType = accessEntityType.get(entityTypeId)
        .orElseThrow(() -> new HttpNotFoundException("Entity Type not found."));

    // TODO Verificar que existe esa entidad
    // ¿Cómo determinar su tipo y acceder a su accesor para recuperarla?
    // Algo de interfaces hay que implementar
    Taggable entity = retrieveEntity(entityType, entityId);

    audit.debug("Tagging " + entityType.getTitle() + " Entity.");
    Tagged tagged = new Tagged(tag, entityType, entity.getTaggableId());

    audit.debug("Saving Tagged Entity.");
    try {
      accessTagged.save(tagged)
          .orElseThrow(() -> new HttpInternalServerException("Failed to persist new Tagged Entity."));
    } catch (ConstraintViolationException e) {
      audit.debug("Tagged already exists.");
      throw new HttpBadRequestException("Tagged already exists.");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperTagged.entityToDto(tagged);

  }

  private Taggable retrieveEntity(EntityType entityType, Integer entityId) {
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

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOTagged delete(Integer id) {

    audit.debug("Deleting Tagged " + id + ".");
    Tagged tagged = accessTagged.get(id)
        .orElseThrow(() -> new HttpNotFoundException("Tagged not found."));

    if (!accessTagged.remove(tagged.getTaggedId())) {
      throw new HttpInternalServerException("Failed to delete Tagged.");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperTagged.entityToDto(tagged);

  }

}
