package ciudadano.consciente.access;

import ciudadano.consciente.model.Taggable;
import ciudadano.consciente.model.TaggedActivityType;
import ciudadano.consciente.model.TaggedOrganization;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class AccessTaggedActivityType implements PanacheRepositoryBase<TaggedActivityType, Integer> {

    @Inject
    Logger audit;

    public List<TaggedActivityType> getAllTags() {

        audit.debug("Trying to retrieve all Tags.");
        return findAll().stream().toList();

    }

    public List<TaggedActivityType> getTags(Taggable taggable) {

        audit.debug("Trying to retrieve all Tags of Activity Type " + taggable.getId() + ".");
        return find("entityId", taggable.getId()).stream().toList();

    }

}

