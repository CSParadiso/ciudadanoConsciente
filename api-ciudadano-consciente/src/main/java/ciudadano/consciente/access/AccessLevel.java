package ciudadano.consciente.access;

import ciudadano.consciente.model.Level;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessLevel implements PanacheRepositoryBase<Level, Integer> {

    @Inject
    Logger audit;

    public List<Level> getAll() {

        audit.debug("Trying to retrieve all Levels.");
        return findAll(Sort.by("levelId")).stream().toList();

    }

    public Optional<Level> get(Integer id) {

        audit.debug("Trying to retrieve Level " + id + ".");
        return findByIdOptional(id);

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
}
