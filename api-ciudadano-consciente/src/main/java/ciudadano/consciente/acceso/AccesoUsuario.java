package ciudadano.consciente.acceso;

import ciudadano.consciente.modelo.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.Optional;

@RequestScoped
public class AccesoUsuario implements PanacheRepositoryBase<Usuario, Integer> {

    @Inject
    Logger auditor;

    public Optional<Usuario> obtener(Integer identificador) {
        auditor.debug("Intentando recuperar user del identificador " + identificador);
        return findByIdOptional(identificador);
    }

}
