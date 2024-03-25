package ciudadano.consciente.access;

import ciudadano.consciente.model.Level;
import ciudadano.consciente.model.Organization;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessLevel implements PanacheRepositoryBase<Level, Integer> {

    @Inject
    Logger audit;

    @Inject
    EntityManager entityManager;

    public List<Level> getAll() {

        audit.debug("Trying to retrieve all Levels.");
        return findAll(Sort.by("levelId")).stream().toList();

    }

    public Optional<Level> get(Integer id) {

        audit.debug("Trying to retrieve Level " + id + ".");
        return findByIdOptional(id);

    }

    public List<Level> getAllChildrens(Integer levelId) {

        audit.debug("Trying to retrieve childrens of level...");
        return entityManager
                .createNamedQuery("Level.getAllChildrens", Level.class)
                .setParameter("parentLevelId", levelId)
                .getResultList();

    }

    public boolean remove(Integer id) {

        audit.debug("Trying to delete Level  " + id  + ".");
        return deleteById(id);

    }

    public Optional<Level> save(Level level) {

        audit.debug("Trying to persist Level" + level.getLevelId() + ".");
        persist(level);
        return findByIdOptional(level.getLevelId());

    }

    public boolean existName(String name) {

        audit.debug("Verifying if name " + name + "already exists.");
        return count("name", name) > 0;

    }

    public List<Level> getAllPaths() {

        audit.debug("Trying to retrieve all Paths...");
        return find("parent = null").stream().toList();

    }

    public List<Level> getAllPathsByOrganization(Organization organization) {

        audit.debug("Trying to retrieve all Paths from Organization...");
        return find("organization = ?1 and parent = null", organization).stream().toList();

    }

}
