package ciudadano.consciente.access;

import ciudadano.consciente.model.Answer;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessAnswer implements PanacheRepositoryBase<Answer, Integer> {

    @Inject
    Logger audit;

    @Inject
    EntityManager entityManager;

    public List<Answer> getAll() {

        audit.debug("Trying to retrieve all Answers.");
        return findAll(Sort.by("answerId")).stream().toList();
        
    }

    public Optional<Answer> get(Integer id) {

        audit.debug("Trying to retrieve Answer " + id + ".");
        return findByIdOptional(id);

    }

    public Optional<Answer> save(Answer answer) {

        audit.debug("Trying to persist Answer" + answer.getAnswerId() + ".");
        persist(answer);
        return findByIdOptional(answer.getAnswerId());

    }

    public List<Object[]> getAllChildrenLevelsAnswers(Integer levelId) {

        audit.debug("Trying to retrieve all children levels answers...");
        return entityManager.createNamedQuery("Answer.getAllChildrenLevelsAnswers")
                .setParameter("parentLevelId", levelId)
                .getResultList();

    }

    public List<Object[]> getAllChildrenLevelsAnswersOfUser(Integer levelId, Integer userId) {

        audit.debug("Trying to retrieve all children levels answers...");
        return entityManager.createNamedQuery("Answer.getAllChildrenLevelsAnswersOfUser")
                .setParameter("parentLevelId", levelId)
                .setParameter("userId", userId)
                .getResultList();

    }

}
