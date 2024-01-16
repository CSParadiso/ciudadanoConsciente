package ciudadano.consciente.service;

import ciudadano.consciente.access.*;
import ciudadano.consciente.dto.DTOCreateVote;
import ciudadano.consciente.dto.DTOVote;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.mapper.MapperVote;
import ciudadano.consciente.model.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.time.LocalDate;
import java.util.List;

@RequestScoped
public class ServiceVote {

    @Inject
    Logger audit;

    @Inject
    AccessVote accessVote;

    @Inject
    MapperVote mapperVote;

    public List<DTOVote> getAll() {

        audit.debug("Getting all Answers.");
        return mapperVote.entityToDto(accessVote.getAll());

    }

    public DTOVote get(Integer id) {

        audit.debug("Getting Vote " + id + ".");
        Vote vote = accessVote.get(id)
                .orElseThrow( () -> new HttpNotFoundException("Vote not found."));

        audit.debug("Mapping Vote into DTO.");
        return  mapperVote.entityToDto(vote);
        
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOVote updateStatus(Integer id) {

        Vote vote = accessVote.get(id)
                .orElseThrow( () -> new HttpNotFoundException("Vote not found."));

        audit.debug("Updating Vote " + id + ".");
        vote.setActive(false);

        audit.debug("Saving Vote " + vote.getVoteId() + ".");
        accessVote.save(vote)
                .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated Vote."));

        audit.debug("Mapping EntityType into DTO.");
        return mapperVote.entityToDto(vote);
    
    }

}
