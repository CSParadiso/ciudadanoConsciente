package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessActivityTypeVersionStatus;
import ciudadano.consciente.dto.DTOActivityTypeVersionStatus;
import ciudadano.consciente.dto.DTOCreateActivityTypeVersionStatus;
import ciudadano.consciente.dto.DTOUpdateActivityTypeVersionStatus;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.mapper.MapperActivityTypeVersionStatus;
import ciudadano.consciente.model.ActivityTypeVersionStatus;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceActivityTypeVersionStatus {

    @Inject
    Logger audit;

    @Inject
    MapperActivityTypeVersionStatus mapperActivityTypeVersionStatus;

    @Inject
    AccessActivityTypeVersionStatus accessActivityTypeVersionStatus;

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    public List<DTOActivityTypeVersionStatus> getAll() {

        audit.debug("Retrieving all categories of Answers Status...");
        return mapperActivityTypeVersionStatus.entityToDto(accessActivityTypeVersionStatus.getAll());

    }

    public DTOActivityTypeVersionStatus get(Integer id) {

        audit.debug("Getting ActivityTypeVersionStatus " + id + ".");
        ActivityTypeVersionStatus activityTypeVersionStatus = accessActivityTypeVersionStatus.get(id)
                .orElseThrow( () -> new HttpNotFoundException("ActivityTypeVersionStatus not found."));

        audit.debug("Mapping EntityType into DTO.");
        return  mapperActivityTypeVersionStatus.entityToDto(activityTypeVersionStatus);
        
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivityTypeVersionStatus create(DTOCreateActivityTypeVersionStatus dtoCreateActivityTypeVersionStatus) {

        String title = dtoCreateActivityTypeVersionStatus.getTitle();
        if(accessActivityTypeVersionStatus.existTitle(title)) {
            throw new HttpBadRequestException("The title already exists.");
        }

        audit.debug("Creating category of Answer Status.");
        ActivityTypeVersionStatus activityTypeVersionStatus = mapperActivityTypeVersionStatus.dtoToEntity(dtoCreateActivityTypeVersionStatus);

        audit.debug("Saving category of Answer Status " + activityTypeVersionStatus.getActivityTypeVersionStatusId() + ".");
        accessActivityTypeVersionStatus.save(activityTypeVersionStatus)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new category of ActivityTypeVersionStatus") );

        audit.debug("Mapping EntityType into DTO.");
        return mapperActivityTypeVersionStatus.entityToDto(activityTypeVersionStatus);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivityTypeVersionStatus update(Integer id, DTOUpdateActivityTypeVersionStatus dtoUpdateActivityTypeVersionStatus) {

        audit.debug("Updating category of Answer Status " + id + ".");
        String title = dtoUpdateActivityTypeVersionStatus.getTitle();
        String description = dtoUpdateActivityTypeVersionStatus.getDescription();

        ActivityTypeVersionStatus activityTypeVersionStatus = accessActivityTypeVersionStatus.get(id)
                .orElseThrow( () -> new HttpNotFoundException("Category of Answer Status not found."));

        if(utilityVerifyRequestField.isValidField(title)) {
            if(accessActivityTypeVersionStatus.existTitle(title)) {
                throw new HttpBadRequestException("The title already exists.");
            }
            activityTypeVersionStatus.setTitle(title);
        }

        if(utilityVerifyRequestField.isValidField(description)) {
            activityTypeVersionStatus.setDescription(description);
        }

        audit.debug("Saving updated category of Answer Status " + activityTypeVersionStatus.getActivityTypeVersionStatusId() + ".");
        accessActivityTypeVersionStatus.save(activityTypeVersionStatus)
                .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated category of Answer Status."));

        audit.debug("Mapping EntityType into DTO.");
        return mapperActivityTypeVersionStatus.entityToDto(activityTypeVersionStatus);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivityTypeVersionStatus delete(Integer id) {

        audit.debug("Deleting category of Answer Status " + id + ".");
        ActivityTypeVersionStatus activityTypeVersionStatus = accessActivityTypeVersionStatus.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Answer Status not found."));

        if(!accessActivityTypeVersionStatus.remove(activityTypeVersionStatus.getActivityTypeVersionStatusId())) {
            throw new HttpInternalServerException("Failed to delete Answer Status");
        }

        audit.debug("Mapping EntityType into DTO.");
        return mapperActivityTypeVersionStatus.entityToDto(activityTypeVersionStatus);

    }

}
