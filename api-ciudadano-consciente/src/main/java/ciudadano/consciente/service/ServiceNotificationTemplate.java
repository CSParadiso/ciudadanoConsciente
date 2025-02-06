package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessNotificationTemplate;
import ciudadano.consciente.dto.DTOCreateNotificationTemplate;
import ciudadano.consciente.dto.DTONotificationTemplate;
import ciudadano.consciente.dto.DTOUpdateNotificationTemplate;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.mapper.MapperNotificationTemplate;
import ciudadano.consciente.model.NotificationTemplate;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceNotificationTemplate {

  @Inject
  Logger audit;

  @Inject
  MapperNotificationTemplate mapperNotificationTemplate;

  @Inject
  AccessNotificationTemplate accessNotificationTemplate;

  @Inject
  UtilityVerifyRequestField utilityVerifyRequestField;

  public List<DTONotificationTemplate> getAll() {

    audit.debug("Retrieving all categories of NotificationTemplates...");
    return mapperNotificationTemplate.entityToDto(accessNotificationTemplate.getAll());

  }

  public DTONotificationTemplate get(Integer id) {

    audit.debug("Getting NotificationTemplate " + id + ".");
    NotificationTemplate entityType = accessNotificationTemplate.get(id)
        .orElseThrow(() -> new HttpNoContentException("NotificationTemplate not found."));

    audit.debug("Mapping NotificationTemplate into DTO.");
    return mapperNotificationTemplate.entityToDto(entityType);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTONotificationTemplate create(DTOCreateNotificationTemplate dtoCreateNotificationTemplate) {

    String title = dtoCreateNotificationTemplate.getTitle();
    if (accessNotificationTemplate.existTitle(title)) {
      throw new HttpBadRequestException("The title already exists.");
    }

    audit.debug("Creating category of NotificationTemplate.");
    NotificationTemplate entityType = new NotificationTemplate(dtoCreateNotificationTemplate.getTitle(),
            new String(dtoCreateNotificationTemplate.getTemplate()));

    audit.debug("Saving category of NotificationTemplate " + entityType.getNotificationTemplateId() + ".");
    accessNotificationTemplate.save(entityType)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist new category of NotificationTemplate"));

    audit.debug("Mapping NotificationTemplate into DTO.");
    return mapperNotificationTemplate.entityToDto(entityType);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTONotificationTemplate update(Integer id, DTOUpdateNotificationTemplate dtoUpdateNotificationTemplate) {

    audit.debug("Updating category of NotificationTemplate " + id + ".");
    String title = dtoUpdateNotificationTemplate.getTitle();
    String template = new String(dtoUpdateNotificationTemplate.getTemplate());

    NotificationTemplate entityType = accessNotificationTemplate.get(id)
        .orElseThrow(() -> new HttpNoContentException("Category of NotificationTemplate not found."));

    if (utilityVerifyRequestField.isValidField(title)) {
      if (accessNotificationTemplate.existTitle(title)) {
        throw new HttpBadRequestException("The title already exists.");
      }
      entityType.setTitle(title);
    }

    if (utilityVerifyRequestField.isValidField(template)) {
      entityType.setTemplate(template);
    }

    audit.debug("Saving updated category of NotificationTemplate " + entityType.getNotificationTemplateId() + ".");
    accessNotificationTemplate.save(entityType)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated category of NotificationTemplate."));

    audit.debug("Mapping NotificationTemplate into DTO.");
    return mapperNotificationTemplate.entityToDto(entityType);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTONotificationTemplate delete(Integer id) {

    audit.debug("Deleting category of NotificationTemplate " + id + ".");
    NotificationTemplate entityType = accessNotificationTemplate.get(id)
        .orElseThrow(() -> new HttpNoContentException("NotificationTemplate not found."));

    if (!accessNotificationTemplate.remove(entityType.getNotificationTemplateId())) {
      throw new HttpInternalServerException("Failed to delete NotificationTemplate");
    }

    audit.debug("Mapping NotificationTemplate into DTO.");
    return mapperNotificationTemplate.entityToDto(entityType);

  }

}
