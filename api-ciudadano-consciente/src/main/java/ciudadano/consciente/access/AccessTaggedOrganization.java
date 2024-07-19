package ciudadano.consciente.access;

import ciudadano.consciente.model.Taggable;
import ciudadano.consciente.model.TaggedOrganization;
import ciudadano.consciente.model.Votable;
import ciudadano.consciente.model.VotedOrganization;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class AccessTaggedOrganization implements PanacheRepositoryBase<TaggedOrganization, Integer> {

    @Inject
    Logger audit;

    public List<TaggedOrganization> getAllTags() {

        audit.debug("Trying to retrieve all Tags.");
        return findAll().stream().toList();

    }

    public List<TaggedOrganization> getTags(Taggable taggable) {

        audit.debug("Trying to retrieve all Tags of Organization " + taggable.getId() + ".");
        return find("entityId", taggable.getId()).stream().toList();

    }

}

