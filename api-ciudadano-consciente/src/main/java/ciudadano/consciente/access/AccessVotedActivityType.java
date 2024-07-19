package ciudadano.consciente.access;

import ciudadano.consciente.model.Votable;
import ciudadano.consciente.model.VotedActivityType;
import ciudadano.consciente.model.VotedOrganization;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class AccessVotedActivityType implements PanacheRepositoryBase<VotedActivityType, Integer> {

    @Inject
    Logger audit;

    public List<VotedActivityType> getAllVotes() {

        audit.debug("Trying to retrieve all Votes.");
        return findAll().stream().toList();

    }

    public List<VotedActivityType> getVotes(Votable votable) {

        audit.debug("Trying to retrieve all Votes of Activity Type " + votable.getId() + ".");
        return find("entityId", votable.getId()).stream().toList();

    }

}

