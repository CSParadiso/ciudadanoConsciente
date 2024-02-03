package ciudadano.consciente.service;

import ciudadano.consciente.access.*;
import ciudadano.consciente.dto.DTOActivityTypeVersion;
import ciudadano.consciente.dto.DTOCreateActivityTypeVersion;
import ciudadano.consciente.dto.DTOUpdateActivityTypeVersion;
import ciudadano.consciente.dto.DTOVote;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.mapper.MapperActivityTypeVersion;
import ciudadano.consciente.mapper.MapperVote;
import ciudadano.consciente.model.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

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

    public List<DTOActivityTypeVersion> getAllByActivityType(Integer activityTypeSearched) {

        ActivityType activityType = accessActivityType.get(activityTypeSearched)
                .orElseThrow( ()-> new HttpNotFoundException("Activity Type not found"));

        audit.debug("Getting all Activity Type Versions");
        return mapperActivityTypeVersion.entityToDto(accessActivityTypeVersion.getAllByActivityType(activityType));

    }

    public DTOActivityTypeVersion get(Integer id) {

        audit.debug("Getting Activity Type Version " + id + ".");
        ActivityTypeVersion activityTypeVersion = accessActivityTypeVersion.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Activity Type Version not found.") );

        audit.debug("Mapping entity into DTO.");
        return mapperActivityTypeVersion.entityToDto(activityTypeVersion);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivityTypeVersion create(DTOCreateActivityTypeVersion dtoCreateActivityTypeVersion) {

        Integer activityTypeId = dtoCreateActivityTypeVersion.getActivityTypeId();
        Integer activityTypeVersionStatusId = dtoCreateActivityTypeVersion.getActivityTypeVersionStatusId();

        ActivityType activityType = accessActivityType.get(activityTypeId)
                .orElseThrow( ()-> new HttpNotFoundException("Activity Type not found.") );

        ActivityTypeVersionStatus activityTypeVersionStatus = accessActivityTypeVersionStatus.get(activityTypeVersionStatusId)
                .orElseThrow( ()-> new HttpNotFoundException("Activity Type Status not found.") );

        audit.debug("Creating Version of Activity Type.");
        ActivityTypeVersion activityTypeVersion = mapperActivityTypeVersion.dtoToEntity(dtoCreateActivityTypeVersion);

        audit.debug("Saving Version of Activity Type");
        try {
            accessActivityTypeVersion.save(activityTypeVersion);
        } catch (ConstraintViolationException e) {
            throw new HttpBadRequestException("Github version already exists.");
        } catch (Exception e) {
            throw new HttpInternalServerException("Failed to persist Version of Activity Type.");
        }

        audit.debug("Mapping DTO into Entity.");
        return mapperActivityTypeVersion.entityToDto(activityTypeVersion);

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




}
