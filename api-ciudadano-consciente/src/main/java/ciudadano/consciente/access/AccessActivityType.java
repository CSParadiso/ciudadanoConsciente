package ciudadano.consciente.access;

import ciudadano.consciente.model.ActivityType;
import ciudadano.consciente.model.Organization;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessActivityType implements PanacheRepositoryBase<ActivityType, Integer> {

    @Inject
    Logger audit;

    public Optional<ActivityType> save(ActivityType activityType) {

        audit.debug("Trying to persist Activity Type " + activityType.getName() + ".");
        persist(activityType);
        return findByIdOptional(activityType.getActivityTypeId());

    }

    public List<ActivityType> getAll() {

        audit.debug("Trying to retrieve all Activity Types.");
        return findAll().stream().toList();

    }

    public Optional<ActivityType> get(Integer id) {

        audit.debug("Trying to retrieve Activity Type " + id + ".");
        return findByIdOptional(id);

    }

    public boolean remove(Integer id) {

        audit.debug("Trying to delete Activity Type " + id + ".");
        return deleteById(id) ;

    }

    public boolean existsName(String name) {

        audit.debug("Verifying if name " + name + " already exists.");
        return count("name", name) > 0;

    }

}
