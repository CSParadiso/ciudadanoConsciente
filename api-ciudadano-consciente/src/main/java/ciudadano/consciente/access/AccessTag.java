package ciudadano.consciente.access;

import ciudadano.consciente.model.Tag;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessTag implements PanacheRepositoryBase<Tag, Integer> {

    @Inject
    Logger audit;

    public List<Tag> getAll() {

        audit.debug("Trying to retrieve all Tags.");
        return findAll().stream().toList();

    }

    public Optional<Tag> get(Integer id) {

        audit.debug("Trying to retrieve Reference " + id + ".");
        return findByIdOptional(id);

    }

    public Optional<Tag> save(Tag tag) {

        audit.debug("Trying to persist Tag " + tag.getTagId() + ".");
        persist(tag);
        return findByIdOptional(tag.getTagId());

    }

    public boolean remove(Integer tagId) {

        audit.debug("Trying to delete Tag  " + tagId  + ".");
        return deleteById(tagId);

    }

}
