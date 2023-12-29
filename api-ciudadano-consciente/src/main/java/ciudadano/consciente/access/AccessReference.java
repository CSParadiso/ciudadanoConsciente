package ciudadano.consciente.access;

import ciudadano.consciente.model.Level;
import ciudadano.consciente.model.Reference;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessReference implements PanacheRepositoryBase<Reference, Integer> {

    @Inject
    Logger audit;

    public boolean existsTitleInLevel(Level level, String title) {

        audit.debug("Verifying if exists Title in Level.");
        return count("level = ?1 and title = ?2", level, title) > 0;

    }

    public Optional<Reference> save(Reference reference) {

        audit.debug("Trying to persist Reference.");
        persist(reference);
        return  findByIdOptional(reference.getReferenceId());

    }

    public List<Reference> getAll() {

        audit.debug("Trying to retrieve all References.");
        return findAll().stream().toList();

    }

    public Optional<Reference> get(Integer id) {

        audit.debug("Trying to retrieve Reference " + id + ".");
        return findByIdOptional(id);

    }

    public boolean remove(Integer id) {

        audit.debug("Trying to delete Reference " + id + ".");
        return deleteById(id);

    }
}
