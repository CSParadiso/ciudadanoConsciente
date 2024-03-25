package ciudadano.consciente.access;

import ciudadano.consciente.model.Activity;
import ciudadano.consciente.model.Level;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessActivity implements PanacheRepositoryBase<Activity, Integer> {

    @Inject
    Logger audit;

    @Inject
    EntityManager entityManager;

    public List<Activity> getAll() {

        audit.debug("Trying to retreive all Activities.");
        return findAll().stream().toList();

    }

    public Optional<Activity> save(Activity activity) {

        audit.debug("Trying to persist Activity " + activity.getActivityId());
        persist(activity);
        return findByIdOptional(activity.getActivityId());

    }

    public Optional<Activity> get(Integer id) {

        audit.debug("Trying to retrieve Activity " + id + ".");
        return findByIdOptional(id);

    }

    public Optional<Activity> getByLevel(Level level) {

        audit.debug("Trying to retrieve Activity by Level " + level.getLevelId());
        return find("level", level).stream().findFirst();

    }

    public boolean remove(Integer id) {

        audit.debug("Trying to delete Activity Type " + id + ".");
        return deleteById(id) ;

    }

//    public String getTemplate(Integer activityId) {
//
//        audit.debug("Retrieving template...");
//        return find("Activity.getTemplateFromActivityTypeVersion", Parameters.with("activityId", activityId)).toString();
//
//    }

    public String getTemplate(Integer activityId) {
        List<String> resultList = entityManager
                .createNamedQuery("Activity.getTemplateFromActivityTypeVersion", String.class)
                .setParameter("activityId", activityId)
                .getResultList();

        // Check if the result list is not empty and return the first element
        if (!resultList.isEmpty()) {
            return resultList.get(0);
        }

        // Return null if no result is found
        return null;
    }

}
