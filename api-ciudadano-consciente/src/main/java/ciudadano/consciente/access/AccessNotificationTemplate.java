package ciudadano.consciente.access;

import ciudadano.consciente.model.NotificationTemplate;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessNotificationTemplate implements PanacheRepositoryBase<NotificationTemplate, Integer> {

  @Inject
  Logger audit;

  public List<NotificationTemplate> getAll() {

    audit.debug("Trying to retrieve all types of Entity");
    return findAll().stream().toList();

  }

  public Optional<NotificationTemplate> get(Integer id) {

    audit.debug("Trying to retrieve category of NotificationTemplate " + id + ".");
    return findByIdOptional(id);

  }

  public boolean existTitle(String title) {

    audit.debug("Verifying if title " + title + "already exists.");
    return count("title", title) > 0;

  }

  public Optional<NotificationTemplate> save(NotificationTemplate entityType) {

    audit.debug("Trying to persist NotificationTemplate" + entityType.getNotificationTemplateId() + ".");
    persist(entityType);
    return findByIdOptional(entityType.getNotificationTemplateId());

  }

  public boolean remove(Integer entityId) {

    audit.debug("Trying to delete NotificationTemplate  " + entityId + ".");
    return deleteById(entityId);

  }

  public Optional<NotificationTemplate> getByName(String entityTypeTitle) {

    audit.debug("Trying to retrieve title " + entityTypeTitle + ".");
    return find("title", entityTypeTitle).firstResultOptional();

  }

}
