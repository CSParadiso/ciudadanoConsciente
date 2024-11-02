package ciudadano.consciente.access;

import ciudadano.consciente.model.Organization;
//import ciudadano.consciente.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessOrganization implements PanacheRepositoryBase<Organization, Integer> {

    @Inject
    Logger audit;

    public List<Organization> getAll() {

        audit.debug("Trying to retrieve all Organizations.");
        return findAll().stream().toList();

    }

    public Optional<Organization> get(Integer id) {

        audit.debug("Trying to retrieve Organization " + id + ".");
        return findByIdOptional(id);

    }

    public Optional<Organization> save(Organization organization) {

        audit.debug("Trying to persist Organization " + organization.getOrganizationId() + ".");
        persist(organization);
        return findByIdOptional(organization.getOrganizationId());

    }

    public boolean remove(Integer id) {

        audit.debug("Trying to delete Organization " + id + ".");
        return deleteById(id);

    }

    public boolean existName(String name) {

        audit.debug("Verifying if name " + name + " already exists.");
        return count("name", name) > 0;

    }

    public boolean existEmail(String email) {

        audit.debug("Verifying if email " + email + " already exists.");
        return count("email", email) > 0;

    }

}
