package ciudadano.consciente.access;

import ciudadano.consciente.model.*;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class AccessVotedLevel implements PanacheRepositoryBase<VotedLevel, Integer> {

    @Inject
    Logger audit;

    public List<VotedLevel> getAllVotes() {

        audit.debug("Trying to retrieve all Votes.");
        return findAll().stream().toList();

    }

    public List<VotedLevel> getVotes(Votable votable) {

        audit.debug("Trying to retrieve all Votes of Level " + votable.getId() + ".");
        return find("entityId", votable.getId()).stream().toList();

    }

}

