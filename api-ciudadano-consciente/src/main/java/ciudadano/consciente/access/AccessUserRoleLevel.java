package ciudadano.consciente.access;

import ciudadano.consciente.model.Level;
import ciudadano.consciente.model.UserRoleLevel;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessUserRoleLevel implements PanacheRepositoryBase<UserRoleLevel, Integer> {

    @Inject
    Logger audit;


    public Optional<UserRoleLevel> save(UserRoleLevel userRoleLevel) {

        audit.debug("Trying to persist UserRole in Level" + userRoleLevel.getUrlId() + ".");
        persist(userRoleLevel);
        return findByIdOptional(userRoleLevel.getUrlId());

    }


    public List<UserRoleLevel> getByLevel(Level level) {

        audit.debug("Trying to get all UserRoles in Level " + level + ".");
        return list("level", level);

    }

}
