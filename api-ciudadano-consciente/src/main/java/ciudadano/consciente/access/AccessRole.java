package ciudadano.consciente.access;

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
    Logger auditor;


    public List<Role> obtenerTodos() {

        auditor.debug("Intentando recuperar todos los roles.");
        return findAll().stream().toList();

    }

    public Optional<Role> get(Integer id) {

        return findByIdOptional(id);

    }

    public boolean existeNombre(String name) {

        auditor.debug("Corroborando si existe el Rol.");
        return count("name", name) > 0;

    }

    public Optional<Role> persistir(Role role) {

        auditor.debug("Intentando persistir Rol.");
        persist(role);
        return findByIdOptional(role.getRoleId());

    }

    public boolean eliminar(Integer identificador) {

        auditor.debug("Intentando elminar Rol.");
        return deleteById(identificador);

    }


}
