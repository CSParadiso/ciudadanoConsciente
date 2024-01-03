package ciudadano.consciente.access;

import ciudadano.consciente.model.Activity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessActivity implements PanacheRepositoryBase<Activity, Integer> {

    @Inject
    Logger audit;

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

    public boolean remove(Integer id) {

        audit.debug("Trying to delete Activity Type " + id + ".");
        return deleteById(id) ;

    }
}
