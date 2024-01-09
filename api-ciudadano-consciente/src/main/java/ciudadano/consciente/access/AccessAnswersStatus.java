package ciudadano.consciente.access;

import ciudadano.consciente.model.AnswersStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessAnswersStatus implements PanacheRepositoryBase<AnswersStatus, Integer> {

    @Inject
    Logger audit;

    public List<AnswersStatus> getAll() {

        audit.debug("Trying to retrieve all categories of Answers Status");
        return findAll().stream().toList();

    }

    public Optional<AnswersStatus> get(Integer id) {

        audit.debug("Trying to retrieve category of Answer Status " + id + ".");
        return findByIdOptional(id);

    }

    public boolean existTitle(String title) {

        audit.debug("Verifying if title " + title + "already exists.");
        return count("title", title) > 0;

    }

    public Optional<AnswersStatus> save(AnswersStatus answersStatus) {

        audit.debug("Trying to persist AnswersStatus" + answersStatus.getAnswersStatusId() + ".");
        persist(answersStatus);
        return findByIdOptional(answersStatus.getAnswersStatusId());

    }

    public boolean remove(Integer answersStatusId) {

        audit.debug("Trying to delete Answer Status  " + answersStatusId  + ".");
        return deleteById(answersStatusId);

    }
}
