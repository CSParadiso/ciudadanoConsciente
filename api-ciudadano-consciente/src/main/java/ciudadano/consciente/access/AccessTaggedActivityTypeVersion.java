package ciudadano.consciente.access;

import ciudadano.consciente.model.Taggable;
import ciudadano.consciente.model.TaggedActivityType;
import ciudadano.consciente.model.TaggedActivityTypeVersion;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class AccessTaggedActivityTypeVersion implements PanacheRepositoryBase<TaggedActivityTypeVersion, Integer> {

    @Inject
    Logger audit;

    public List<TaggedActivityTypeVersion> getAllTags() {

        audit.debug("Trying to retrieve all Tags.");
        return findAll().stream().toList();

    }

    public List<TaggedActivityTypeVersion> getTags(Taggable taggable) {

        audit.debug("Trying to retrieve all Tags of Activity Type Version" + taggable.getId() + ".");
        return find("entityId", taggable.getId()).stream().toList();

    }

}

