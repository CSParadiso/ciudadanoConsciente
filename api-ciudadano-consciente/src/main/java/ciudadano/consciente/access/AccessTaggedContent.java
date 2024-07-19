package ciudadano.consciente.access;

import ciudadano.consciente.model.Taggable;
import ciudadano.consciente.model.TaggedConcern;
import ciudadano.consciente.model.TaggedContent;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class AccessTaggedContent implements PanacheRepositoryBase<TaggedContent, Integer> {

    @Inject
    Logger audit;

    public List<TaggedContent> getAllTags() {

        audit.debug("Trying to retrieve all Tags.");
        return findAll().stream().toList();

    }

    public List<TaggedContent> getTags(Taggable taggable) {

        audit.debug("Trying to retrieve all Tags of Content " + taggable.getId() + ".");
        return find("entityId", taggable.getId()).stream().toList();

    }

}

