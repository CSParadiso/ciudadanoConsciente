package ciudadano.consciente.acceso;

import ciudadano.consciente.modelo.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccesoUsuario implements PanacheRepositoryBase<Usuario, Integer> {

    @Inject
    Logger auditor;

    public Optional<Usuario> obtener(Integer identificador) {
        auditor.debug("Intentando recuperar user del identificador " + identificador);
        return findByIdOptional(identificador);
    }

    public List<Usuario> obtenerTodos() {

        auditor.debug("Intentando recuperar todos los usuarios");
        return findAll().stream().toList();

    }

    public Optional<Usuario> persistir(Usuario usuario) {

        auditor.debug("Intentando persistir usuario");
        auditor.debug(usuario.getUserId());
        persist(usuario);
        return find("username", usuario.getUsername()).firstResultOptional();

    }

    public boolean eliminar(Integer identificador) {

        auditor.debug("Intentando eliminar usuario " + identificador);
        return deleteById(identificador);

    }

}
