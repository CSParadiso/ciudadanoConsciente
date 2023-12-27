package ciudadano.consciente.access;

import ciudadano.consciente.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessUser implements PanacheRepositoryBase<User, Integer> {

    @Inject
    Logger auditor;

    public Optional<User> obtener(Integer identificador) {
        auditor.debug("Intentando recuperar user del identificador " + identificador);
        return findByIdOptional(identificador);
    }

    public List<User> obtenerTodos() {

        auditor.debug("Intentando recuperar todos los usuarios");
        return findAll().stream().toList();

    }

    public Optional<User> persistir(User user) {

        auditor.debug("Intentando persistir usuario");
        persist(user);
        return findByIdOptional(user.getUserId());

    }

    public boolean eliminar(Integer identificador) {

        auditor.debug("Intentando eliminar usuario " + identificador);
        return deleteById(identificador);

    }

    public boolean existeUsername(String username) {

        return count("username", username) > 0;

    }

    public boolean existeEmail(String email) {

        return count("email", email) > 0;

    }
}
