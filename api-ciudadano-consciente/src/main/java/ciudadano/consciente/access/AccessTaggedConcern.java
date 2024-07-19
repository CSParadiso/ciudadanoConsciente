package ciudadano.consciente.access;

import ciudadano.consciente.model.Taggable;
import ciudadano.consciente.model.TaggedConcern;
import ciudadano.consciente.model.TaggedOrganization;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class AccessTaggedConcern implements PanacheRepositoryBase<TaggedConcern, Integer> {

    @Inject
    Logger audit;

    public List<TaggedConcern> getAllTags() {

        audit.debug("Trying to retrieve all Tags.");
        return findAll().stream().toList();

    }

    public List<TaggedConcern> getTags(Taggable taggable) {

        audit.debug("Trying to retrieve all Tags of Concern " + taggable.getId() + ".");
        return find("entityId", taggable.getId()).stream().toList();

    }

}

