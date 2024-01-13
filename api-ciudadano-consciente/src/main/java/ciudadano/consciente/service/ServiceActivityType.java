package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessActivityType;
import ciudadano.consciente.access.AccessUser;
import ciudadano.consciente.dto.DTOActivityType;
import ciudadano.consciente.dto.DTOUpdateActivityType;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.model.ActivityType;
import ciudadano.consciente.dto.DTOCreateActivityType;
import ciudadano.consciente.mapper.MapperActivityType;
import ciudadano.consciente.model.User;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceActivityType {

    @Inject
    Logger audit;

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    @Inject
    MapperActivityType mapperActivityType;

    @Inject
    AccessActivityType accessActivityType;

    @Inject
    AccessUser accessUser;

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivityType create(DTOCreateActivityType dtoCreateActivityType) {

        audit.debug("Creating Activity Type.");
        String name = dtoCreateActivityType.getName();
        String description = dtoCreateActivityType.getDescription();
        String functionalTemplateUrl = dtoCreateActivityType.getFunctionalTemplateUrl();
        Integer creator = dtoCreateActivityType.getCreator();

        if(accessActivityType.existsName(name)) {
            throw new HttpBadRequestException("The name of the Activity Type already exists.");
        }

        User user = accessUser.get(creator)
                .orElseThrow( ()-> new HttpNotFoundException("User not found.") );

        audit.debug("Mapping DTO into Entity.");
        ActivityType activityType = mapperActivityType.dtoToEntity(name, description, functionalTemplateUrl, user);

        audit.debug("Saving Activity Type " + activityType.getActivityTypeId() + ".");
        accessActivityType.save(activityType)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new Activity Type."));

        audit.debug("Mapping Entity into DTO.");
        return mapperActivityType.entityToDto(activityType);

    }

    public List<DTOActivityType> getAll() {

        audit.debug("Getting all Activity Types.");
        return mapperActivityType.entityToDto(accessActivityType.getAll());

    }

    public DTOActivityType get(Integer id) {

        audit.debug("Getting Activity Type " + id + ".");
        ActivityType activityType = accessActivityType.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Activity Type not found."));

        audit.debug("Mapping Entity into DTO.");
        return mapperActivityType.entityToDto(activityType);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivityType delete(Integer id) {

        audit.debug("Deleting Activity Type " + id + ".");
        ActivityType activityType = accessActivityType.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Activity Type not found.") );

        if(!accessActivityType.remove(activityType.getActivityTypeId())) {
            throw new HttpInternalServerException("Failed to delete Activity Type.");
        }

        audit.debug("Mapping Entity into DTO.");
        return mapperActivityType.entityToDto(activityType);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivityType update(Integer id, DTOUpdateActivityType dtoUpdateActivityType) {

        audit.debug("Updating Activity Type.");
        String name = dtoUpdateActivityType.getName();
        String description = dtoUpdateActivityType.getDescription();
        String functionalTemplate = dtoUpdateActivityType.getFunctionalTemplateUrl();

        ActivityType activityType = accessActivityType.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Activity Type not found."));

        if(utilityVerifyRequestField.isValidField(name)) {
            if(accessActivityType.existsName(name)) {
                throw new HttpBadRequestException("The name of the Activity Type already exists.");
            }
           activityType.setName(name);
        }

        if(utilityVerifyRequestField.isValidField(description)) {
            activityType.setDescription(description);
        }

        if(utilityVerifyRequestField.isValidField(functionalTemplate)) {
            activityType.setFunctionalTemplateUrl(functionalTemplate);
        }

        audit.debug("Saving Activity Type " + activityType.getActivityTypeId() + ".");
        accessActivityType.save(activityType)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist updated Activity Type.") );

        audit.debug("Mapping Entity into DTO.");
        return mapperActivityType.entityToDto(activityType);

    }
}
