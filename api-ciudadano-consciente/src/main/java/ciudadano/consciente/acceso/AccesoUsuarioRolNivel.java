package ciudadano.consciente.acceso;

import ciudadano.consciente.modelo.UsuarioRolNivel;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.Optional;

@RequestScoped
public class AccesoUsuarioRolNivel implements PanacheRepositoryBase<UsuarioRolNivel, Integer> {

    @Inject
    Logger auditor;


    public Optional<UsuarioRolNivel> persistir(UsuarioRolNivel usuarioRolNivel) {

        auditor.debug("Intentando persistir UsuarioRolNivel.");
        persist(usuarioRolNivel);
        return findByIdOptional(usuarioRolNivel.getUrlId());

    }
}
