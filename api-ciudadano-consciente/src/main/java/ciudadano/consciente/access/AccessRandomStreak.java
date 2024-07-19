package ciudadano.consciente.access;

import ciudadano.consciente.model.RandomStreak;
import ciudadano.consciente.model.User;
import ciudadano.consciente.model.Votable;
import ciudadano.consciente.model.VotedReference;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessRandomStreak implements PanacheRepositoryBase<RandomStreak, Integer> {

    @Inject
    Logger audit;

    public Optional<RandomStreak> save(RandomStreak randomStreak) {

        audit.debug("Trying to persist new Random Streak for User " +
                randomStreak.getUser().getUserId() + "." );
        persist(randomStreak);
        return findByIdOptional(randomStreak.getStreakId());

    }

    public List<RandomStreak> getAllRandomStreaks() {

        audit.debug("Trying to retrieve all Random Streaks.");
        return findAll().stream().toList();

    }

    public Optional<RandomStreak> getRandomStreaksByUser(User user) {

        audit.debug("Trying to retrieve Random Streaks of" + user.getUserId() + ".");
        return find("user", user).stream().findFirst();

    }

    public Optional<RandomStreak> getRandomStreak(Integer id) {

        audit.debug("Trying to retrieve Random Streak " + id);
        return findByIdOptional(id);

    }

}

