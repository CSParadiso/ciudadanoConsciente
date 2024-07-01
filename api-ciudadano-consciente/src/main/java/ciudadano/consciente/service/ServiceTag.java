package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessTag;
import ciudadano.consciente.dto.DTOCreateTag;
import ciudadano.consciente.dto.DTOTag;
import ciudadano.consciente.dto.DTOUpdateTag;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.mapper.MapperTag;
import ciudadano.consciente.model.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceTag {

  @Inject
  Logger audit;

  @Inject
  MapperTag mapperTag;

  @Inject
  AccessTag accessTag;

  public List<DTOTag> getAll() {

    audit.debug("Retrieving all Tags.");
    return mapperTag.entityToDto(accessTag.getAll());

  }

  public DTOTag get(Integer id) {

    audit.debug("Retrieving Tag " + id + ".");
    Tag tag = accessTag.get(id)
        .orElseThrow(() -> new HttpNoContentException("Tag not found."));

    return mapperTag.entityToDto(tag);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOTag create(DTOCreateTag dtoCreateTag) {

    audit.debug("Creating Tag.");
    Tag tag = mapperTag.dtoToEntity(dtoCreateTag);

    audit.debug(tag.getTagId());
    audit.debug(tag.getName());

    audit.debug("Saving Tag.");
    try {
      accessTag.save(tag)
          .orElseThrow(() -> new HttpInternalServerException("Failed to persist new Tag."));
    } catch (ConstraintViolationException e) {
      audit.debug("Tag already exists.");
      throw new HttpBadRequestException("Tag already exists.");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperTag.entityToDto(tag);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOTag update(Integer id, DTOUpdateTag dtoUpdateTag) {

    audit.debug("Retrieving Tag.");
    Tag tag = accessTag.get(id)
        .orElseThrow(() -> new HttpNoContentException("Tag not found."));

    audit.debug("Updating Concern " + id + ".");
    mapperTag.update(tag, dtoUpdateTag);

    audit.debug("Saving Tag " + tag.getTagId() + ".");
    accessTag.save(tag)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated Tag."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperTag.entityToDto(tag);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOTag delete(Integer id) {

    audit.debug("Deleting Concern " + id + ".");
    Tag tag = accessTag.get(id)
        .orElseThrow(() -> new HttpNoContentException("Tag not found."));

    if (!accessTag.remove(tag.getTagId())) {
      throw new HttpInternalServerException("Failed to delete Tag");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperTag.entityToDto(tag);

  }

}
