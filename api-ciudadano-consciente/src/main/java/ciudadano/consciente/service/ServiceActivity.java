package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessActivity;
import ciudadano.consciente.access.AccessActivityType;
import ciudadano.consciente.access.AccessLevel;
import ciudadano.consciente.dto.DTOActivity;
import ciudadano.consciente.dto.DTOCreateActivity;
import ciudadano.consciente.dto.DTOUpdateActivity;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.mapper.MapperActivity;
import ciudadano.consciente.model.Activity;
import ciudadano.consciente.model.ActivityType;
import ciudadano.consciente.model.Level;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceActivity {

    @Inject
    Logger audit;

    @Inject
    MapperActivity mapperActivity;

    @Inject
    AccessActivity accessActivity;

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    @Inject
    AccessActivityType accessActivityType;

    @Inject
    AccessLevel accessLevel;


   public List<DTOActivity> getAll() {

        audit.debug("Retrieving all Activities.");
        return mapperActivity.entityToDto(accessActivity.getAll());

   }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivity create(DTOCreateActivity dtoCreateActivity) {

        audit.debug("Creating Activity.");
        String description = dtoCreateActivity.getDescription();
        Integer levelDTO = dtoCreateActivity.getLevel();
        Level level = accessLevel.get(levelDTO)
                .orElseThrow( ()-> new HttpNotFoundException("Level not found.") );

        audit.debug("Mapping DTO into Entity.");
        Activity activity = mapperActivity.dtoToEntity(level, description);

        Integer activityTypeDTO = dtoCreateActivity.getActivityType();
        if(utilityVerifyRequestField.isValidField(activityTypeDTO)) {
            ActivityType activityType = accessActivityType.get(activityTypeDTO)
                    .orElseThrow( ()-> new HttpNotFoundException("Activity Type not found"));
            activity.setActivityType(activityType);
        }

        audit.debug("Saving Activity " + activity.getActivityId() + ".");
        accessActivity.save(activity)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new Activity."));

        audit.debug("Mapping Entity into DTO.");
        return mapperActivity.entityToDto(activity);

    }

    public DTOActivity get(Integer id) {

        audit.debug("Getting Activity " + id + ".");
        Activity activity = accessActivity.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Activity not found."));

        audit.debug("Mapping Entity into DTO.");
        return mapperActivity.entityToDto(activity);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivity update(Integer id, DTOUpdateActivity dtoUpdateActivity) {

        audit.debug("Updating Activity.");
        Activity activity = accessActivity.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Activity not found."));

        Integer levelDTO = dtoUpdateActivity.getLevel();
        String description = dtoUpdateActivity.getDescription();
        Integer activityTypeDTO = dtoUpdateActivity.getActivityType();
        if(utilityVerifyRequestField.isValidField(levelDTO)) {
            Level level = accessLevel.get(levelDTO)
                    .orElseThrow( ()-> new HttpNotFoundException("Level not found.") );
            activity.setLevel(level);
        }

        if(utilityVerifyRequestField.isValidField(description)) {
            activity.setDescription(description);
        }

        if(utilityVerifyRequestField.isValidField(activityTypeDTO)) {
            ActivityType activityType = accessActivityType.get(activityTypeDTO)
                    .orElseThrow( ()-> new HttpNotFoundException("Activity Type not found."));
            activity.setActivityType(activityType);
        }

        audit.debug("Saving Activity " + activity.getActivityId() + ".");
        accessActivity.save(activity)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist updated Activity.") );

        audit.debug("Mapping Entity into DTO.");
        return mapperActivity.entityToDto(activity);
       
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivity delete(Integer id) {

        audit.debug("Deleting Activity " + id + ".");
        Activity activity = accessActivity.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Activity not found."));

        if(!accessActivity.remove(activity.getActivityId())) {
            throw new HttpInternalServerException("Failed to remove Activity");
        };

        audit.debug("Mapping Entity into DTO.");
        return mapperActivity.entityToDto(activity);

    }
}
