package ciudadano.consciente.access;

import ciudadano.consciente.model.Votable;
import ciudadano.consciente.model.VotedConcern;
import ciudadano.consciente.model.VotedOrganization;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class AccessVotedConcern implements PanacheRepositoryBase<VotedConcern, Integer> {

    @Inject
    Logger audit;

    public List<VotedConcern> getAllVotes() {

        audit.debug("Trying to retrieve all Votes.");
        return findAll().stream().toList();

    }

    public List<VotedConcern> getVotes(Votable votable) {

        audit.debug("Trying to retrieve all Votes of Concern " + votable.getId() + ".");
        return find("entityId", votable.getId()).stream().toList();

    }

}

