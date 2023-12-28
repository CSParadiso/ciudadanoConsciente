package ciudadano.consciente.access;

import ciudadano.consciente.model.Organization;
import ciudadano.consciente.model.UserRolOrganization;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
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

    public List<UserRolOrganization> getByOrganization(Organization organization) {

        audit.debug("Trying to get all UserRoles in Organization " + organization + ".");
        return list("organization", organization);

    }
}
