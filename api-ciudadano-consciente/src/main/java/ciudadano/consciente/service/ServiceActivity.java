package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessActivity;
import ciudadano.consciente.access.AccessContent;
import ciudadano.consciente.access.AccessLevel;
import ciudadano.consciente.dto.DTOActivity;
import ciudadano.consciente.dto.DTOCreateActivity;
import ciudadano.consciente.dto.DTOUpdateActivity;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.mapper.MapperActivity;
import ciudadano.consciente.model.*;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
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
    AccessLevel accessLevel;

    @Inject
    AccessContent accessContent;


   public List<DTOActivity> getAll() {

        audit.debug("Retrieving all Activities.");
        return mapperActivity.entityToDto(accessActivity.getAll());

   }

    public DTOActivity get(Integer id) {

        audit.debug("Getting Activity " + id + ".");
        Activity activity = accessActivity.get(id)
                .orElseThrow( ()-> new HttpNoContentException("Activity not found."));

        audit.debug("Mapping EntityType into DTO.");
        return mapperActivity.entityToDto(activity);

    }

    public DTOActivity getByLevel(Integer levelId) {

        audit.debug("Getting Level.");
        Level level = accessLevel.get(levelId)
                .orElseThrow( ()-> new HttpNoContentException("Level not found.") );

        Activity activity = accessActivity.getByLevel(level)
                .orElseThrow( ()-> new HttpNoContentException("Activity not found.") );

        return mapperActivity.entityToDto(activity);


    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivity create(DTOCreateActivity dtoCreateActivity) {

        audit.debug("Creating Activity.");
        Integer levelDTO = dtoCreateActivity.getLevel();
        accessLevel.get(levelDTO)
                .orElseThrow( ()-> new HttpNoContentException("Level not found.") );

        Integer contentDto = dtoCreateActivity.getContent();
        accessContent.get(contentDto)
                .orElseThrow( ()-> new HttpNoContentException("Content not found.") );

        audit.debug("Mapping DTO into EntityType.");
        Activity activity = mapperActivity.dtoToEntity(dtoCreateActivity);

        audit.debug("Saving Activity.");
        try {
            accessActivity.save(activity)
                    .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new Activity."));
        } catch (ConstraintViolationException e) {
            audit.debug("Level already has an Activity: " + e.getErrorMessage());
            throw new HttpBadRequestException("Level already has an Activity: " + e.getErrorMessage());
        }

        audit.debug("Mapping EntityType into DTO.");
        return mapperActivity.entityToDto(activity);


    }

    public String getTemplate(Integer id) {

        audit.debug("Getting Activity " + id + ".");
        Activity activity = accessActivity.get(id)
                .orElseThrow( ()-> new HttpNoContentException("Activity not found."));

        audit.debug("Mapping EntityType into DTO.");
        return accessActivity.getTemplate(activity.getActivityId());

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivity update(Integer id, DTOUpdateActivity dtoUpdateActivity) {

        audit.debug("Updating Activity.");
        Activity activity = accessActivity.get(id)
                .orElseThrow( ()-> new HttpNoContentException("Activity not found."));

        Integer levelDTO = dtoUpdateActivity.getLevel();
        String description = dtoUpdateActivity.getDescription();
        Integer contentDTO = dtoUpdateActivity.getContent();
        if(utilityVerifyRequestField.isValidField(levelDTO)) {
            Level level = accessLevel.get(levelDTO)
                    .orElseThrow( ()-> new HttpNoContentException("Level not found.") );
            if(accessActivity.getByLevel(level).isEmpty()) {
                activity.setLevel(level);
            } else {
                throw new HttpBadRequestException("Level already has an Activity.");
            }

        }

        if(utilityVerifyRequestField.isValidField(description)) {
            activity.setDescription(description);
        }

        if(utilityVerifyRequestField.isValidField(contentDTO)) {
            Content content = accessContent.get(contentDTO)
                    .orElseThrow( ()-> new HttpNoContentException("Content not found."));
            activity.setContent(content);
        }

        audit.debug("Saving Activity " + activity.getActivityId() + ".");
        try {
            accessActivity.save(activity)
                    .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new Activity."));
        } catch (ConstraintViolationException e) {
            audit.debug("Level already has an Activity: " + e.getErrorMessage());
            throw new HttpBadRequestException("Level already has an Activity: " + e.getErrorMessage());
        }

        audit.debug("Mapping EntityType into DTO.");
        return mapperActivity.entityToDto(activity);
       
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivity delete(Integer id) {

        audit.debug("Deleting Activity " + id + ".");
        Activity activity = accessActivity.get(id)
                .orElseThrow( ()-> new HttpNoContentException("Activity not found."));

        if(!accessActivity.remove(activity.getActivityId())) {
            throw new HttpInternalServerException("Failed to remove Activity");
        };

        audit.debug("Mapping EntityType into DTO.");
        return mapperActivity.entityToDto(activity);

    }

}
