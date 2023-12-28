package ciudadano.consciente.access;

import ciudadano.consciente.model.UserRolOrganization;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.Optional;

@RequestScoped
public class AccessUserRoleOrganization implements PanacheRepositoryBase<UserRolOrganization, Integer> {

    @Inject
    Logger audit;

    public Optional<UserRolOrganization> save(UserRolOrganization userRolOrganization) {

        audit.debug("Trying to persist UserRoleOrganization " + userRolOrganization.getUroId());
        persist(userRolOrganization);
        return findByIdOptional(userRolOrganization.getUroId());

    }
}
