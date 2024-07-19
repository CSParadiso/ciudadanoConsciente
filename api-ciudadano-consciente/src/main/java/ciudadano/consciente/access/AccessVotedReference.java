package ciudadano.consciente.access;

import ciudadano.consciente.model.Votable;
import ciudadano.consciente.model.VotedOrganization;
import ciudadano.consciente.model.VotedReference;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class AccessVotedReference implements PanacheRepositoryBase<VotedReference, Integer> {

    @Inject
    Logger audit;

    public List<VotedReference> getAllVotes() {

        audit.debug("Trying to retrieve all Votes.");
        return findAll().stream().toList();

    }

    public List<VotedReference> getVotes(Votable votable) {

        audit.debug("Trying to retrieve all Votes of Reference " + votable.getId() + ".");
        return find("entityId", votable.getId()).stream().toList();

    }

}

