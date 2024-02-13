package ciudadano.consciente.service;

import ciudadano.consciente.access.*;
import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.mapper.MapperActivityTypeVersion;
import ciudadano.consciente.mapper.MapperVote;
import ciudadano.consciente.model.*;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

@RequestScoped
public class ServiceActivityTypeVersion {

    @Inject
    Logger audit;

    @Inject
    AccessActivityTypeVersion accessActivityTypeVersion;

    @Inject
    MapperActivityTypeVersion mapperActivityTypeVersion;

    @Inject
    AccessActivityType accessActivityType;

    @Inject
    AccessActivityTypeVersionStatus accessActivityTypeVersionStatus;

    @Inject
    AccessEntityType accessEntityType;

    @Inject
    AccessUser accessUser;

    @Inject
    AccessVote accessVote;

    @Inject
    MapperVote mapperVote;

    @Inject
    ServiceVersionServer serviceVersionServer;

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    public List<DTOActivityTypeVersion> getAll(Integer status) {

        audit.debug("Retrieving all Version...");
        ActivityTypeVersionStatus activityTypeVersionStatus = null;
        if(utilityVerifyRequestField.isValidField(status)) {
            activityTypeVersionStatus = accessActivityTypeVersionStatus.get(status)
                    .orElseThrow( ()-> new HttpNoContentException("Status of Version not found.") );
        }


        return (activityTypeVersionStatus == null)  ?
                mapperActivityTypeVersion.entityToDto(accessActivityTypeVersion.getAll())
                : mapperActivityTypeVersion.entityToDto(accessActivityTypeVersion.getAllByStatus(activityTypeVersionStatus));

    }

    public List<DTOActivityTypeVersion> getAllByActivityType(Integer activityTypeSearched, Integer status) {

        ActivityTypeVersionStatus activityTypeVersionStatus = null;
        if(utilityVerifyRequestField.isValidField(status)) {
             activityTypeVersionStatus = accessActivityTypeVersionStatus.get(status)
                    .orElseThrow( ()-> new HttpNoContentException("Status of Activity Type Version not found."));
        }

        ActivityType activityType = accessActivityType.get(activityTypeSearched)
                .orElseThrow( ()-> new HttpNoContentException("Activity Type not found"));

        audit.debug("Getting all Activity Type Versions");
        return (activityTypeVersionStatus == null)  ?
                mapperActivityTypeVersion.entityToDto(accessActivityTypeVersion.getAllByActivityType(activityType))
                : mapperActivityTypeVersion.entityToDto(accessActivityTypeVersion.getAllByActivityTypeAndStatus(activityType, activityTypeVersionStatus));

    }

    public DTOActivityTypeVersion get(Integer id) {

        audit.debug("Getting Activity Type Version " + id + ".");
        ActivityTypeVersion activityTypeVersion = accessActivityTypeVersion.get(id)
                .orElseThrow( ()-> new HttpNoContentException("Activity Type Version not found.") );

        audit.debug("Mapping entity into DTO.");
        return mapperActivityTypeVersion.entityToDto(activityTypeVersion);

    }

    public DTOVersionContent getContent(Integer id) {

        audit.debug("Getting Activity Type Version " + id + ".");
        ActivityTypeVersion activityTypeVersion = accessActivityTypeVersion.get(id)
                .orElseThrow( ()-> new HttpNoContentException("Activity Type Version not found.") );

        // Recuperar archivos desde la api
        audit.debug("Sending parameters to Version Server.");
        return serviceVersionServer.getContent(activityTypeVersion);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivityTypeVersion update(Integer id, DTOUpdateActivityTypeVersion dtoUpdateActivityTypeVersion) {

        Integer activityTypeVersionId = dtoUpdateActivityTypeVersion.getActivityTypeVersionId();
        Integer activityTypeVersionStatusId = dtoUpdateActivityTypeVersion.getActivityTypeVersionStatusId();

        ActivityTypeVersion activityTypeVersion = accessActivityTypeVersion.get(activityTypeVersionId)
                .orElseThrow( ()-> new HttpNotFoundException("Activity Type Version not found.") );

        ActivityTypeVersionStatus activityTypeVersionStatus = accessActivityTypeVersionStatus.get(activityTypeVersionStatusId)
                .orElseThrow( ()-> new HttpNotFoundException("Status of Activity Type Version not found.") );

        audit.debug("Updating Activity Type Version " + id);
        activityTypeVersion.setActivityTypeVersionStatusId(activityTypeVersionStatus);
        activityTypeVersion.setLastModifiedStatusDate(LocalDate.now());

        audit.debug("Trying to persist updated Status of Activity Type Version.");
        accessActivityTypeVersion.save(activityTypeVersion)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist updated Status of Activity Type Version.") );

        audit.debug("Mapping entity into DTO.");
        return mapperActivityTypeVersion.entityToDto(activityTypeVersion);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivityTypeVersion delete(Integer id) {

        audit.debug("Deleting ActivityTypeVersion " + id + ".");
        ActivityTypeVersion activityTypeVersion = accessActivityTypeVersion.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("ActivityTypeVersion not found."));

        if(!accessActivityTypeVersion.remove(activityTypeVersion.getActivityTypeVersionId())) {
            throw new HttpInternalServerException("Failed to delete ActivityTypeVersion");
        }

        audit.debug("Mapping EntityType into DTO.");
        return mapperActivityTypeVersion.entityToDto(activityTypeVersion);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOVote vote(Integer idActivityTypeVersion, Integer idUser) {

        audit.debug("Retrieving Entity Type");
        EntityType entityType = accessEntityType.getByName("ActivityTypeVersion")
                .orElseThrow( ()-> new HttpNotFoundException("Entity Type not found.") );

        ActivityTypeVersion ActivityTypeVersion = accessActivityTypeVersion.get(idActivityTypeVersion)
                .orElseThrow( ()-> new HttpNotFoundException("ActivityTypeVersion not found."));

        User user = accessUser.get(idUser)
                .orElseThrow( ()-> new HttpNotFoundException("User not found."));

        audit.debug("Verify if Vote already exists.");
        if(accessVote.getByKeys(user, ActivityTypeVersion.getActivityTypeVersionId(), entityType).isPresent()) {
            throw new HttpBadRequestException("Vote already exists.");
        }

        audit.debug("Creating Vote for ActivityTypeVersion.");
        Vote vote = new Vote(user, ActivityTypeVersion.getActivityTypeVersionId(), entityType);

        audit.debug("Saving Vote.");
        accessVote.save(vote)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new Vote.") );

        audit.debug("Mapping EntityType into DTO.");
        return mapperVote.entityToDto(vote);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivityTypeVersion create(String serverProvider, DTOCreateActivityTypeVersion dtoCreateActivityTypeVersion) {

        audit.debug("Retrieving Activity Type.");
        Integer activityTypeId = dtoCreateActivityTypeVersion.getActivityTypeId();
        ActivityType activityType = accessActivityType.get(activityTypeId)
                    .orElseThrow(() -> new HttpNoContentException("Activity Type not found."));

        audit.debug("Sending parameters to version server.");
        ActivityTypeVersion activityTypeVersion = serviceVersionServer.createVersion(serverProvider, dtoCreateActivityTypeVersion);

        audit.debug("Setting version values not related to server.");
        activityTypeVersion.setActivityTypeVersionStatusId(accessActivityTypeVersionStatus.get(1) // By default STAGED
                .orElseThrow(()-> new HttpNoContentException("Status of Activity Type Version not found.")));
        activityTypeVersion.setActivityTypeId(activityType);

        audit.debug("Saving new Activity Type Version");
        try {
            accessActivityTypeVersion.save(activityTypeVersion)
                    .orElseThrow( ()-> new HttpInternalServerException("Failed to create new Activity Type Version.") );
        } catch (ConstraintViolationException e) {
            audit.debug("Version of Activity Type already exists. (Hint: Commit and push changes before create a new version).");
            throw new HttpBadRequestException("Version of Activity Type already exists. (Hint: Commit and push changes before create a new version).");
        }

        audit.debug("Mapping Entity into DTO.");
        return mapperActivityTypeVersion.entityToDto(activityTypeVersion);

    }

}
