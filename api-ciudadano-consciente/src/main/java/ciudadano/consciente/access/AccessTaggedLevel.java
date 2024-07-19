package ciudadano.consciente.access;

import ciudadano.consciente.model.Taggable;
import ciudadano.consciente.model.TaggedLevel;
import ciudadano.consciente.model.TaggedOrganization;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class AccessTaggedLevel implements PanacheRepositoryBase<TaggedLevel, Integer> {

    @Inject
    Logger audit;

    public List<TaggedLevel> getAllTags() {

        audit.debug("Trying to retrieve all Tags.");
        return findAll().stream().toList();

    }

    public List<TaggedLevel> getTags(Taggable taggable) {

        audit.debug("Trying to retrieve all Tags of Level " + taggable.getId() + ".");
        return find("entityId", taggable.getId()).stream().toList();

    }

}

