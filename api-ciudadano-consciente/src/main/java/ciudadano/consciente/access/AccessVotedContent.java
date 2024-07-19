package ciudadano.consciente.access;

import ciudadano.consciente.model.Votable;
import ciudadano.consciente.model.VotedConcern;
import ciudadano.consciente.model.VotedContent;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class AccessVotedContent implements PanacheRepositoryBase<VotedContent, Integer> {

    @Inject
    Logger audit;

    public List<VotedContent> getAllVotes() {

        audit.debug("Trying to retrieve all Votes.");
        return findAll().stream().toList();

    }

    public List<VotedContent> getVotes(Votable votable) {

        audit.debug("Trying to retrieve all Votes of Content " + votable.getId() + ".");
        return find("entityId", votable.getId()).stream().toList();

    }

}

