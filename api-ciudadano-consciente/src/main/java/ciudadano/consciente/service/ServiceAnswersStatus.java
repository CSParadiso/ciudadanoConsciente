package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessAnswersStatus;
import ciudadano.consciente.dto.DTOAnswersStatus;
import ciudadano.consciente.dto.DTOCreateAnswersStatus;
import ciudadano.consciente.dto.DTOUpdateAnswersStatus;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.mapper.MapperAnswersStatus;
import ciudadano.consciente.model.AnswersStatus;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceAnswersStatus {

    @Inject
    Logger audit;

    @Inject
    MapperAnswersStatus mapperAnswersStatus;

    @Inject
    AccessAnswersStatus accessAnswersStatus;

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    public List<DTOAnswersStatus> getAll() {

        audit.debug("Retrieving all categories of Answers Status...");
        return mapperAnswersStatus.entityToDto(accessAnswersStatus.getAll());

    }

    public DTOAnswersStatus get(Integer id) {

        audit.debug("Getting AnswersStatus " + id + ".");
        AnswersStatus answersStatus = accessAnswersStatus.get(id)
                .orElseThrow( () -> new HttpNotFoundException("AnswersStatus not found."));

        audit.debug("Mapping Entity into DTO.");
        return  mapperAnswersStatus.entityToDto(answersStatus);
        
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOAnswersStatus create(DTOCreateAnswersStatus dtoCreateAnswersStatus) {

        String title = dtoCreateAnswersStatus.getTitle();
        if(accessAnswersStatus.existTitle(title)) {
            throw new HttpBadRequestException("The title already exists.");
        }

        audit.debug("Creating category of Answer Status.");
        AnswersStatus answersStatus = mapperAnswersStatus.dtoToEntity(dtoCreateAnswersStatus);

        audit.debug("Saving category of Answer Status " + answersStatus.getAnswersStatusId() + ".");
        accessAnswersStatus.save(answersStatus)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new category of AnswersStatus") );

        audit.debug("Mapping Entity into DTO.");
        return mapperAnswersStatus.entityToDto(answersStatus);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOAnswersStatus update(Integer id, DTOUpdateAnswersStatus dtoUpdateAnswersStatus) {

        audit.debug("Updating category of Answer Status " + id + ".");
        String title = dtoUpdateAnswersStatus.getTitle();
        String description = dtoUpdateAnswersStatus.getDescription();

        AnswersStatus answersStatus = accessAnswersStatus.get(id)
                .orElseThrow( () -> new HttpNotFoundException("Category of Answer Status not found."));

        if(utilityVerifyRequestField.isValidField(title)) {
            if(accessAnswersStatus.existTitle(title)) {
                throw new HttpBadRequestException("The title already exists.");
            }
            answersStatus.setTitle(title);
        }

        if(utilityVerifyRequestField.isValidField(description)) {
            answersStatus.setDescription(description);
        }

        audit.debug("Saving updated category of Answer Status " + answersStatus.getAnswersStatusId() + ".");
        accessAnswersStatus.save(answersStatus)
                .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated category of Answer Status."));

        audit.debug("Mapping Entity into DTO.");
        return mapperAnswersStatus.entityToDto(answersStatus);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOAnswersStatus delete(Integer id) {

        audit.debug("Deleting category of Answer Status " + id + ".");
        AnswersStatus answersStatus = accessAnswersStatus.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Answer Status not found."));

        if(!accessAnswersStatus.remove(answersStatus.getAnswersStatusId())) {
            throw new HttpInternalServerException("Failed to delete Answer Status");
        }

        audit.debug("Mapping Entity into DTO.");
        return mapperAnswersStatus.entityToDto(answersStatus);

    }

}
