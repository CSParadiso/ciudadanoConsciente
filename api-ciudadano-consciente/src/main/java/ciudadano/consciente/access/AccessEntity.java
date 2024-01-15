package ciudadano.consciente.access;

import ciudadano.consciente.model.Entity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessEntity implements PanacheRepositoryBase<Entity, Integer> {

    @Inject
    Logger audit;

    public List<Entity> getAll() {

        audit.debug("Trying to retrieve all categories of Entities");
        return findAll().stream().toList();

    }

    public Optional<Entity> get(Integer id) {

        audit.debug("Trying to retrieve category of Entity " + id + ".");
        return findByIdOptional(id);

    }

    public boolean existTitle(String title) {

        audit.debug("Verifying if title " + title + "already exists.");
        return count("title", title) > 0;

    }

    public Optional<Entity> save(Entity entity) {

        audit.debug("Trying to persist Entity" + entity.getEntityId() + ".");
        persist(entity);
        return findByIdOptional(entity.getEntityId());
        
    }

    public boolean remove(Integer entityId) {

        audit.debug("Trying to delete Entity  " + entityId  + ".");
        return deleteById(entityId);

    }
}
