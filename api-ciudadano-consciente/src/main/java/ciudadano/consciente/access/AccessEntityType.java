package ciudadano.consciente.access;

import ciudadano.consciente.model.EntityType;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessEntityType implements PanacheRepositoryBase<EntityType, Integer> {

  @Inject
  Logger audit;

  public List<EntityType> getAll() {

    audit.debug("Trying to retrieve all types of Entity");
    return findAll().stream().toList();

  }

  public Optional<EntityType> get(Integer id) {

    audit.debug("Trying to retrieve category of EntityType " + id + ".");
    return findByIdOptional(id);

  }

  public boolean existTitle(String title) {

    audit.debug("Verifying if title " + title + "already exists.");
    return count("title", title) > 0;

  }

  public Optional<EntityType> save(EntityType entityType) {

    audit.debug("Trying to persist EntityType" + entityType.getEntityTypeId() + ".");
    persist(entityType);
    return findByIdOptional(entityType.getEntityTypeId());

  }

  public boolean remove(Integer entityId) {

    audit.debug("Trying to delete EntityType  " + entityId + ".");
    return deleteById(entityId);

  }

  public Optional<EntityType> getByName(String entityTypeTitle) {

    audit.debug("Trying to retrieve title " + entityTypeTitle + ".");
    return find("title", entityTypeTitle).firstResultOptional();

  }

}
