package ciudadano.consciente.access;

import ciudadano.consciente.model.Organization;
import ciudadano.consciente.model.User;
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

    public Optional<UserRolOrganization> getByOrganizationAndUser(Integer idOrganization, Integer idUser) {

        audit.debug("Trying to retrieve User(" + idUser + ") Role in Organization(" + idOrganization + ".");
        return find("organization.organizationId = ?1 and user.userId = ?2", idOrganization, idUser).stream().findFirst();
        
    }

    public List<UserRolOrganization> getByOrganizationAndRole(Integer idOrganization, Integer idRole) {

        audit.debug("Trying to retrieve all Users with Role(" + idRole + ") in Organization(" + idOrganization + ").");
        return find("organization.organizationId = ?1 and role.roleId = ?2", idOrganization, idRole).stream().toList();
        
    }

    public Optional<UserRolOrganization> get(Integer idOrganization, Integer idUser, Integer idRole) {

        audit.debug("Trying to retrieve User(" + idUser + ")Role(" + idRole + ")Organization(" + idUser + ") " + idOrganization + ".");
        return find("organization.organizationId = ?1 and user.userId = ?2 and role.roleId = ?3", idOrganization, idUser, idRole).firstResultOptional();
        
    }

    public List<UserRolOrganization> getByUser(User user) {

        audit.debug("Trying to retrive Organizations by user " + user.getUserId());
        return find("user", user).stream().toList();

    }

    public boolean remove(Integer uroId) {

        audit.debug("Trying to delete UserRoleLevel " + uroId + ".");
        return deleteById(uroId);

    }
}
