package ciudadano.consciente.access;

import ciudadano.consciente.model.EntityType;
import ciudadano.consciente.model.User;
import ciudadano.consciente.model.Vote;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessVote implements PanacheRepositoryBase<Vote, Integer> {

    @Inject
    Logger audit;

    public List<Vote> getAll() {

        audit.debug("Trying to retrieve all Votes.");
        return findAll(Sort.by("voteId")).stream().toList();

    }

    public Optional<Vote> get(Integer id) {

        audit.debug("Trying to retrieve Vote " + id + ".");
        return findByIdOptional(id);

    }
    
    public Optional<Vote> save(Vote vote) {

        audit.debug("Trying to persist vote" + vote.getVoteId() + ".");
        persist(vote);
        return findByIdOptional(vote.getVoteId());
        
    }

    public Optional<Vote> getByKeys(User user, Integer entity, EntityType entityType) {

        audit.debug("Trying to retrieve Vote.");
        return find("user = ?1 and entity = ?2 and entityType = ?3",
                user, entity, entityType).firstResultOptional();

    }

    public List<Vote> getByKeys(EntityType entityType, Integer id) {

        audit.debug("Trying to retrieve Votes of Entity " + entityType.getTitle() + ".");
        return find("entityType = ?1 and entity = ?2", entityType, id).stream().toList();

    }

}
