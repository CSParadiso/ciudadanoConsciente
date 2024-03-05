package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessActivityType;
import ciudadano.consciente.access.AccessEntityType;
import ciudadano.consciente.access.AccessUser;
import ciudadano.consciente.access.AccessVote;
import ciudadano.consciente.dto.DTOActivityType;
import ciudadano.consciente.dto.DTOUpdateActivityType;
import ciudadano.consciente.dto.DTOVote;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.mapper.MapperVote;
import ciudadano.consciente.model.*;
import ciudadano.consciente.dto.DTOCreateActivityType;
import ciudadano.consciente.mapper.MapperActivityType;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceActivityType {

    final String ENTITY_NAME = "ActivityType";

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

    @Inject
    AccessEntityType accessEntityType;

    @Inject
    AccessVote accessVote;

    @Inject
    MapperVote mapperVote;

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivityType create(DTOCreateActivityType dtoCreateActivityType) {

        audit.debug("Creating Activity Type.");
        String name = dtoCreateActivityType.getName();
        Integer creator = dtoCreateActivityType.getCreator();

        if(accessActivityType.existsName(name)) {
            throw new HttpBadRequestException("The name of the Activity Type already exists.");
        }

        // Verify if user exists
        accessUser.get(creator)
                .orElseThrow( ()-> new HttpNotFoundException("User not found.") );

        audit.debug("Mapping DTO into EntityType.");
        ActivityType activityType = mapperActivityType.dtoToEntity(dtoCreateActivityType);

        audit.debug("Saving Activity Type " + activityType.getActivityTypeId() + ".");
        accessActivityType.save(activityType)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new Activity Type."));

        audit.debug("Mapping EntityType into DTO.");
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

        audit.debug("Mapping EntityType into DTO.");
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

        audit.debug("Mapping EntityType into DTO.");
        return mapperActivityType.entityToDto(activityType);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivityType update(Integer id, DTOUpdateActivityType dtoUpdateActivityType) {

        ActivityType activityType = accessActivityType.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Activity Type not found."));

        audit.debug("Updating Activity Type.");
        String name = dtoUpdateActivityType.getName();
        String description = dtoUpdateActivityType.getDescription();

        if(utilityVerifyRequestField.isValidField(name)) {
            if(accessActivityType.existsName(name)) {
                throw new HttpBadRequestException("The name of the Activity Type already exists.");
            }
           activityType.setName(name);
        }

        if(utilityVerifyRequestField.isValidField(description)) {
            activityType.setDescription(description);
        }

        audit.debug("Saving Activity Type " + activityType.getActivityTypeId() + ".");
        accessActivityType.save(activityType)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist updated Activity Type.") );

        audit.debug("Mapping EntityType into DTO.");
        return mapperActivityType.entityToDto(activityType);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOVote vote(Integer idActivityType, Integer idUser) {

        audit.debug("Retrieving Entity Type");
        EntityType entityType = accessEntityType.getByName(ENTITY_NAME)
                .orElseThrow( ()-> new HttpNotFoundException("Entity Type not found.") );

        ActivityType activityType = accessActivityType.get(idActivityType)
                .orElseThrow( ()-> new HttpNotFoundException("ActivityType not found."));

        User user = accessUser.get(idUser)
                .orElseThrow( ()-> new HttpNotFoundException("User not found."));

        audit.debug("Verify if Vote already exists.");
        if(accessVote.getByKeys(user, activityType.getActivityTypeId(), entityType).isPresent()) {
            throw new HttpBadRequestException("Vote already exists.");
        }

        audit.debug("Creating Vote for ActivityType.");
        Vote vote = new Vote(user, activityType.getActivityTypeId(), entityType);

        audit.debug("Saving Vote.");
        accessVote.save(vote)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new Vote.") );

        audit.debug("Mapping EntityType into DTO.");
        return mapperVote.entityToDto(vote);

    }

}
