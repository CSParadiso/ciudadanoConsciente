package ciudadano.consciente.access;

import ciudadano.consciente.model.Content;
import ciudadano.consciente.model.Organization;
import ciudadano.consciente.model.User;
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

    public List<Content> getAllPublic() {

        audit.debug("Trying to retrieve all Contents.");
        return find("publicContent", true).stream().toList();

    }

    public List<Content> getAllByOrganization(Organization organization, Boolean isPublic) {

        if (isPublic == null) {
            return find("organization = ?1", organization).stream().toList();
        }
        return find("organization = ?1 and publicContent = ?2", organization, isPublic).stream().toList();

    }

    public List<Content> getAllByUser(User user, Boolean isPublic) {

        if (isPublic == null) {
            return find("creator = ?1", user).stream().toList();
        }
        return find("creator = ?1 and publicContent = ?2", user, isPublic).stream().toList();

    }

    public boolean remove(Integer contentId) {

        audit.debug("Trying to delete Content");
        return deleteById(contentId);

    }

}
