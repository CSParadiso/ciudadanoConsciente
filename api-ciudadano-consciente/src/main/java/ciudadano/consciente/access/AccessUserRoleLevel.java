package ciudadano.consciente.access;

import ciudadano.consciente.model.UserRoleLevel;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.Optional;

@RequestScoped
public class AccessUserRoleLevel implements PanacheRepositoryBase<UserRoleLevel, Integer> {

    @Inject
    Logger auditor;


    public Optional<UserRoleLevel> persistir(UserRoleLevel userRoleLevel) {

        auditor.debug("Intentando persistir UsuarioRolNivel.");
        persist(userRoleLevel);
        return findByIdOptional(userRoleLevel.getUrlId());

    }



}
