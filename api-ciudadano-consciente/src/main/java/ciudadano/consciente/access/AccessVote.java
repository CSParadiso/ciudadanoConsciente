package ciudadano.consciente.access;

import ciudadano.consciente.model.*;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.logging.Logger;

import java.util.*;

@RequestScoped
public class AccessVote implements PanacheRepositoryBase<Vote, Integer> {

    @Inject
    Logger audit;

    @Inject
    EntityManager entityManager;

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

    public List<Vote> getByUser(User user) {

        audit.debug("Trying to retrieve Votes of User " + user.getUserId() + ".");
        return find("user", user).stream().toList();

    }

    public List<Vote> getByEntityType(EntityType entityType) {

        audit.debug("Trying to retreive votes of EntityType " + entityType.getTitle() + ".");
        return find("entityType", entityType).stream().toList();

    }

    // Returns a Map of entityId and count
    public Map<Integer, Integer> getMostVotedEntitiesByEntityType(Integer entityTypeId) {

        audit.debug("Trying to retrieve most voted entities of " + entityTypeId + "...");
        List<Object[]> votes = entityManager
                .createNamedQuery("Vote.getMostVotedEntitiesByEntityType")
                .setParameter("entityType", entityTypeId)
                .getResultList();

        Map<Integer, Integer> resultMap = new HashMap<>();
        for (Object[] result : votes) { // Iterate through the result list
            Integer entityId = (Integer) result[0]; // Cast the first element to Integer (entity_id)
            Integer count = ((Number) result[1]).intValue(); // Cast the second element to Number and then Integer (votes)
            resultMap.put(entityId, count); // Add entity_id and count to map
        }

        audit.debug(resultMap.toString());

        return resultMap;

    }

    public List<Vote> getByEntityTypeAndUser(EntityType entityTypeId, User user) {

        audit.debug("Trying to retrieve all Votes of an Entity Type by User.");
        return find("entityType = ?1 and user = ?2 and active = true", entityTypeId, user).stream().toList();

    }

}
