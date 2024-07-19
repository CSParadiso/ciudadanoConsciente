package ciudadano.consciente.access;

import ciudadano.consciente.model.Taggable;
import ciudadano.consciente.model.TaggedOrganization;
import ciudadano.consciente.model.TaggedReference;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class AccessTaggedReference implements PanacheRepositoryBase<TaggedReference, Integer> {

    @Inject
    Logger audit;

    public List<TaggedReference> getAllTags() {

        audit.debug("Trying to retrieve all Tags.");
        return findAll().stream().toList();

    }

    public List<TaggedReference> getTags(Taggable taggable) {

        audit.debug("Trying to retrieve all Tags of Reference " + taggable.getId() + ".");
        return find("entityId", taggable.getId()).stream().toList();

    }

}

