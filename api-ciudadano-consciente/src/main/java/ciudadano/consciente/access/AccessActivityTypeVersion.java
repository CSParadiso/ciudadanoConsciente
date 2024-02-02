package ciudadano.consciente.access;

import ciudadano.consciente.model.ActivityType;
import ciudadano.consciente.model.ActivityTypeVersion;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessActivityTypeVersion implements PanacheRepositoryBase<ActivityTypeVersion, Integer> {

    @Inject
    Logger audit;

    public List<ActivityTypeVersion> getAllByActivityType(ActivityType activityType) {

        audit.debug("Trying to retrieve all versions of Activity Type");
        return find("activityTypeId", activityType).stream().toList();

    }

    public Optional<ActivityTypeVersion> get(Integer id) {

        audit.debug("Trying to retrieve version of Activity Type");
        return findByIdOptional(id);

    }

    public Optional<ActivityTypeVersion> save(ActivityTypeVersion activityTypeVersion) {

        audit.debug("Trying to persist Version of Activty Type");
        persist(activityTypeVersion);
        return findByIdOptional(activityTypeVersion.getActivityTypeVersionId());

    }

    public boolean remove(Integer activityTypeVersionId) {

        audit.debug("Trying to delete Version of Activty Type");
        return deleteById(activityTypeVersionId);

    }

}
