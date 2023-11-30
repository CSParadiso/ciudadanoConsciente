package ciudadano.consciente.acceso;

import ciudadano.consciente.modelo.Rol;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccesoRol implements PanacheRepositoryBase<Rol, Integer> {

    @Inject
    Logger auditor;


    public List<Rol> obtenerTodos() {

        auditor.debug("Intentando recuperar todos los roles.");
        return findAll().stream().toList();

    }

    public Optional<Rol> obtener(Integer id) {

        return findByIdOptional(id);

    }

    public boolean existeNombre(String name) {

        auditor.debug("Corroborando si existe el Rol.");
        return count("name", name) > 0;

    }

    public Optional<Rol> persistir(Rol rol) {

        auditor.debug("Intentando persistir Rol.");
        persist(rol);
        return findByIdOptional(rol.getRoleId());

    }

    public boolean eliminar(Integer identificador) {

        return deleteById(identificador);

    }


}
