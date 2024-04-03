package ciudadano.consciente.access;

import ciudadano.consciente.model.Concern;
import ciudadano.consciente.model.Organization;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessConcern implements PanacheRepositoryBase<Concern, Integer> {

    @Inject
    Logger audit;

    public List<Concern> getAll() {

        audit.debug("Trying to get all Concerns");
        return findAll().stream().toList();

    }

    public Optional<Concern> get(Integer id) {

        audit.debug("Trying to retrieve Concern " + id + ".");
        return findByIdOptional(id);

    }

    public Optional<Concern> save(Concern concern) {

        audit.debug("Trying to persist Concern" + concern.getConcernId() + ".");
        persist(concern);
        return findByIdOptional(concern.getConcernId());
        
    }

    public boolean remove(Integer concernId) {

        audit.debug("Trying to delete Concern  " + concernId  + ".");
        return deleteById(concernId);

    }
}
