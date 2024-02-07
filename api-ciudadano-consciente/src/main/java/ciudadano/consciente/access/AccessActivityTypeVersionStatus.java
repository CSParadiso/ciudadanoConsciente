package ciudadano.consciente.access;

import ciudadano.consciente.model.ActivityTypeVersionStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessActivityTypeVersionStatus implements PanacheRepositoryBase<ActivityTypeVersionStatus, Integer> {

    @Inject
    Logger audit;

    public List<ActivityTypeVersionStatus> getAll() {

        audit.debug("Trying to retrieve all categories of Version Status");
        return findAll().stream().toList();

    }

    public Optional<ActivityTypeVersionStatus> get(Integer id) {

        audit.debug("Trying to retrieve category of Version Status " + id + ".");
        return findByIdOptional(id);

    }

    public boolean existTitle(String title) {

        audit.debug("Verifying if title " + title + "already exists.");
        return count("title", title) > 0;

    }

    public Optional<ActivityTypeVersionStatus> save(ActivityTypeVersionStatus activityTypeVersionStatus) {

        audit.debug("Trying to persist ActivityTypeVersionStatus" + activityTypeVersionStatus.getActivityTypeVersionStatusId() + ".");
        persist(activityTypeVersionStatus);
        return findByIdOptional(activityTypeVersionStatus.getActivityTypeVersionStatusId());

    }

    public boolean remove(Integer answersStatusId) {

        audit.debug("Trying to delete Version Status  " + answersStatusId  + ".");
        return deleteById(answersStatusId);

    }
}
