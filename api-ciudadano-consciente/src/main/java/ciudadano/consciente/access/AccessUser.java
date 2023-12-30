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
    Logger audit;

    public Optional<User> get(Integer id) {
        audit.debug("Trying to retrieve User " + id);
        return findByIdOptional(id);
    }

    public List<User> getAll() {

        audit.debug("Trying to retrieve all Users.");
        return findAll().stream().toList();

    }

    public Optional<User> save(User user) {

        audit.debug("Trying to persist User " + user.getUserId() + ".");
        persist(user);
        return findByIdOptional(user.getUserId());

    }

    public boolean delete(Integer id) {

        audit.debug("Trying to delete User " + id + ".");
        return deleteById(id);

    }

    public boolean existsUsername(String username) {

        audit.debug("Verifying if username exists.");
        return count("username", username) > 0;

    }

    public boolean existsEmail(String email) {

        audit.debug("Verifying if email exists.");
        return count("email", email) > 0;

    }
}
