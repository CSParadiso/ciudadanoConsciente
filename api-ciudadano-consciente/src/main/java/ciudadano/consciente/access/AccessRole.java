package ciudadano.consciente.access;

import ciudadano.consciente.model.Organization;
import ciudadano.consciente.model.Role;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessRole implements PanacheRepositoryBase<Role, Integer> {

    @Inject
    Logger audit;


    public List<Role> getAll() {

        audit.debug("Trying to retrive all Roles.");
        return findAll().stream().toList();

    }

    public Optional<Role> get(Integer id) {

        audit.debug("Getting Role " + id + ".");
        return findByIdOptional(id);

    }

    public boolean existsName(String name) {

        audit.debug("Verifying if name already exists.");
        return count("name", name) > 0;

    }

    public Optional<Role> save(Role role) {

        audit.debug("Trying to persist Role " + role.getRoleId() + ".");
        persist(role);
        return findByIdOptional(role.getRoleId());

    }

    public boolean remove(Integer id) {

        audit.debug("Trying to remove Role.");
        return deleteById(id);

    }


}
