package ciudadano.consciente.access;

import ciudadano.consciente.model.Content;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessContent implements PanacheRepositoryBase<Content, Integer> {

    @Inject
    Logger audit;

    public Optional<Content> save(Content content) {

        audit.debug("Trying to persist Content.");
        persist(content);
        return  findByIdOptional(content.getContentId());

    }

    public Optional<Content> get(Integer contentId) {

        audit.debug("Trying to retrieve Content.");
        return findByIdOptional(contentId);

    }

    public List<Content> getAll() {

        audit.debug("Trying to retrieve all Contents.");
        return findAll().stream().toList();

    }

    public boolean remove(Integer contentId) {

        audit.debug("Trying to delete Content");
        return deleteById(contentId);

    }

}
