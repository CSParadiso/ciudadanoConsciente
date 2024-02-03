package ciudadano.consciente.access;

import ciudadano.consciente.model.ActivityType;
import ciudadano.consciente.model.ActivityTypeVersion;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessActivityTypeVersion implements PanacheRepositoryBase<ActivityTypeVersion, Integer> {

    @Inject
    EntityManager entityManager;

    @Inject
    Logger audit;

    public List<ActivityTypeVersion> getAllByActivityType(ActivityType activityType) {
        audit.debug("Trying to retrieve all versions of Activity Type");
        return find("activityTypeId", activityType).list();

    }

    public Optional<ActivityTypeVersion> get(Integer id) {

        audit.debug("Trying to retrieve version of Activity Type");
        return findByIdOptional(id);

    }

    public Optional<ActivityTypeVersion> save(ActivityTypeVersion activityTypeVersion) {

        audit.debug("Trying to persist Version of Activty Type");
        /***
         * Because of the generated value of versionNumber in the Database
         * (which triggers a function to autoincrement the version if is from the same
         * ActivityType) is necessary to flush the changes and refresh to get the
         * actual version number. Otherwise, we get null version number.
         */
        persistAndFlush(activityTypeVersion);
        entityManager.refresh(activityTypeVersion);
        return findByIdOptional(activityTypeVersion.getActivityTypeVersionId());

    }

    public boolean remove(Integer activityTypeVersionId) {

        audit.debug("Trying to delete Version of Activty Type");
        return deleteById(activityTypeVersionId);

    }

}
