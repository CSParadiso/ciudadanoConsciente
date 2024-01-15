package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessConcern;
import ciudadano.consciente.access.AccessUser;
import ciudadano.consciente.dto.DTOConcern;
import ciudadano.consciente.dto.DTOCreateConcern;
import ciudadano.consciente.dto.DTOUpdateConcern;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.mapper.MapperConcern;
import ciudadano.consciente.model.Concern;
import ciudadano.consciente.model.User;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.time.LocalDate;
import java.util.List;

@RequestScoped
public class ServiceConcern {

    @Inject
    Logger audit;

    @Inject
    MapperConcern mapperConcern;

    @Inject
    AccessConcern accessConcern;

    @Inject
    AccessUser accessUser;

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    public List<DTOConcern> getAll() {

        audit.debug("Retrieving all Concerns.");
        return mapperConcern.entityToDto(accessConcern.getAll());

    }

    public DTOConcern get(Integer id) {

        audit.debug("Getting Concern " + id + ".");
        Concern concern = accessConcern.get(id)
                .orElseThrow( () -> new HttpNoContentException("Concern not found."));

        audit.debug("Mapping EntityType into DTO.");
        return  mapperConcern.entityToDto(concern);
        
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOConcern create(DTOCreateConcern dtoCreateConcern) {

        String description = dtoCreateConcern.getDescription();
        Integer userDto  = dtoCreateConcern.getUser();

        User user = accessUser.get(userDto)
                .orElseThrow( ()-> new HttpNotFoundException("User not found."));

        audit.debug("Creating Concern.");
        Concern concern = new Concern(description, LocalDate.now(), user);

        String explanation = dtoCreateConcern.getExplanation();
        if(utilityVerifyRequestField.isValidField(explanation)) {
            concern.setExplanation(explanation);
        }

        audit.debug("Saving Concern " + concern.getConcernId() + ".");
        accessConcern.save(concern)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new Concern.") );

        audit.debug("Mapping EntityType into DTO.");
        return mapperConcern.entityToDto(concern);
        
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOConcern update(Integer id, DTOUpdateConcern dtoUpdateConcern) {

        audit.debug("Retrieving Concern.");
        Concern concern = accessConcern.get(id)
                .orElseThrow( () -> new HttpNotFoundException("Concern not found."));

        audit.debug("Retrieving User.");
        User user = accessUser.get(dtoUpdateConcern.getUser())
                        .orElseThrow( ()-> new HttpNotFoundException("User not found.") );

        audit.debug("Verifying if user updating is the same in DB");
        if(user != concern.getUser()) {
            throw new HttpBadRequestException("Only User related to Concern can update it.");
        }

        audit.debug("Updating Concern " + id + ".");
        String description = dtoUpdateConcern.getDescription();;
        if(utilityVerifyRequestField.isValidField(description)) {
            concern.setDescription(description);
        };

        String explanation = dtoUpdateConcern.getExplanation();;
        if(utilityVerifyRequestField.isValidField(explanation)) {
            concern.setExplanation(explanation);
        };

        audit.debug("Saving Concern " + concern.getConcernId() + ".");
        accessConcern.save(concern)
                .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated Concern."));

        audit.debug("Mapping EntityType into DTO.");
        return mapperConcern.entityToDto(concern);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOConcern delete(Integer id) {

        audit.debug("Deleting Concern " + id + ".");
        Concern loncern = accessConcern.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Concern not found."));

        if(!accessConcern.remove(loncern.getConcernId())) {
            throw new HttpInternalServerException("Failed to delete Concern");
        }

        audit.debug("Mapping EntityType into DTO.");
        return mapperConcern.entityToDto(loncern);
        
    }
}
