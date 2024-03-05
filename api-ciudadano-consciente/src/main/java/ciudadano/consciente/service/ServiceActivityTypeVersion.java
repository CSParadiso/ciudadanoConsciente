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
import ciudadano.consciente.utility.UtilityFileSignature;
import ciudadano.consciente.utility.UtilityFileSystem;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.jboss.logging.Logger;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestScoped
public class ServiceActivityTypeVersion {

    final String ENTITY_NAME = "ActivityTypeVersion";

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

    @Inject
    AccessVersionServer accessVersionServer;

    @Inject
    UtilityFileSignature utilityFileSignature;

    @Inject
    UtilityFileSystem utilityFileSystem;

    @Inject
    AccessFileNameRequired accessFileNameRequired;

    public List<DTOActivityTypeVersion> getAllByStatus(Integer status) {

        audit.debug("Retrieving all Version...");
        ActivityTypeVersionStatus activityTypeVersionStatus = accessActivityTypeVersionStatus.get(status)
                .orElseThrow( ()-> new HttpNoContentException("Status of Version not found.") );

        List<ActivityTypeVersion> activityTypeVersion = accessActivityTypeVersion.getAllByStatus(activityTypeVersionStatus);
        if(activityTypeVersion.isEmpty()) {
            throw new HttpNoContentException("Activity Type Version not found.");
        }

        return mapperActivityTypeVersion.entityToDto(activityTypeVersion);

    }

    public List<DTOActivityTypeVersion> getAll() {

        List<ActivityTypeVersion> activityTypeVersion = accessActivityTypeVersion.getAll();
        if(activityTypeVersion.isEmpty()) {
            throw new HttpNoContentException("Activity Type Version not found.");
        }
        return mapperActivityTypeVersion.entityToDto(activityTypeVersion);

    }

    public List<DTOActivityTypeVersion> getAllByActivityType(Integer activityTypeSearched) {

        ActivityType activityType = accessActivityType.get(activityTypeSearched)
                .orElseThrow( ()-> new HttpNoContentException("Activity Type not found"));

        audit.debug("Getting all Activity Type Versions");
        List<ActivityTypeVersion> activityTypeVersionList = accessActivityTypeVersion.getAllByActivityType(activityType);

        return  mapperActivityTypeVersion.entityToDto(activityTypeVersionList);

    }

    public List<DTOActivityTypeVersion> getAllByActivityTypeAndStatus(Integer activityTypeSearched, Integer status) {

        ActivityType activityType = accessActivityType.get(activityTypeSearched)
                .orElseThrow( ()-> new HttpNoContentException("Activity Type not found"));

        ActivityTypeVersionStatus activityTypeVersionStatus = accessActivityTypeVersionStatus.get(status)
                .orElseThrow( ()-> new HttpNoContentException("Status of Activity Type Version not found."));

        audit.debug("Getting all Activity Type Versions");
        List<ActivityTypeVersion> activityTypeVersionList = accessActivityTypeVersion.getAllByActivityTypeAndStatus(activityType, activityTypeVersionStatus);

        return  mapperActivityTypeVersion.entityToDto(activityTypeVersionList);

    }

    public DTOActivityTypeVersion get(Integer id) {

        audit.debug("Getting Activity Type Version " + id + ".");
        ActivityTypeVersion activityTypeVersion = accessActivityTypeVersion.get(id)
                .orElseThrow( ()-> new HttpNoContentException("Activity Type Version not found.") );

        audit.debug("Mapping entity into DTO.");
        return mapperActivityTypeVersion.entityToDto(activityTypeVersion);

    }

    public Object getContent(Integer id, String filename) {

        audit.debug("Getting Activity Type Version " + id + ".");
        ActivityTypeVersion activityTypeVersion = accessActivityTypeVersion.get(id)
                .orElseThrow( ()-> new HttpNoContentException("Activity Type Version not found.") );

       audit.debug("Getting Filenames allowed");
       List<FileNameRequired> fileNamesRequiredList = accessFileNameRequired.getFileNames();
       Map<String, FileNameRequired> fileNamesRequiredMap = fileNamesRequiredList.stream()
                .collect(Collectors.toMap(FileNameRequired::getFileName, // Key mapper
                        fileNameRequired -> fileNameRequired)); // Value mapper

       // If the filename exists in db
       if(!fileNamesRequiredMap.containsKey(filename)) {
           throw new HttpNoContentException("Name of file (" + filename + ") not found in files allowed.");
       }

       // If the file is persisted in db
       if(fileNamesRequiredMap.get(filename).getInDb()) {
           // TODO Hacer esto genérico (mapear función a nombre de método get)
           switch (filename) {
               case "model" : return activityTypeVersion.getModel();
               case "template" : return activityTypeVersion.getTemplate();
               case "README" : return activityTypeVersion.getReadme();
           }
       } else {
           return utilityFileSystem.getFile(activityTypeVersion.getActivityTypeVersionId().toString());
       }

       throw new HttpInternalServerException("Failed to retrieve File.");

    }

    @Deprecated
    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivityTypeVersion create(String versionServerProvider, DTOCreateActivityTypeVersionFromServer dtoCreateActivityTypeVersionFromServer) {

        audit.debug("Retrieving Activity Type.");
        Integer activityTypeId = dtoCreateActivityTypeVersionFromServer.getActivityTypeId();
        ActivityType activityType = accessActivityType.get(activityTypeId)
                .orElseThrow(() -> new HttpNoContentException("Activity Type not found."));

        audit.debug("Retrieving Version Server " + versionServerProvider + ".");
        VersionServer versionServer = accessVersionServer.getByName(versionServerProvider)
                .orElseThrow(() -> new HttpNotFoundException("Version Server Not Found or Not Supported yet."));

        audit.debug("Sending parameters to version server.");
        ActivityTypeVersion activityTypeVersion = serviceVersionServer.createVersion(versionServer, dtoCreateActivityTypeVersionFromServer);

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

    // PREFERRED
    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivityTypeVersion create(DTOCreateActivityTypeVersion dtoCreateActivityTypeVersion) {

        audit.debug("Retrieving Activity Type.");
        Integer activityTypeId = dtoCreateActivityTypeVersion.getActivityTypeId();
        ActivityType activityType = accessActivityType.get(activityTypeId)
                .orElseThrow(() -> new HttpNoContentException("Activity Type not found."));

        audit.debug("Verifying extension and size of thumbnail file.");
        List<String> allowedImagesExtensions = Arrays.asList("png", "webp", "gif", "jpg", "jpeg"); // Allowed images
        byte[] thumbnail = dtoCreateActivityTypeVersion.getThumbnail();
        String fileType = utilityFileSignature.detectFileType(thumbnail);
        if(!allowedImagesExtensions.contains(fileType)) {
            throw new HttpBadRequestException("Extension of thumbnail file (." + fileType + ") not allowed.");
        }
        if(!utilityFileSystem.smallerThanMaxMbAllowed(thumbnail.length)) {
            throw new HttpBadRequestException("Thumbnail file size is larger than allowed (" + utilityFileSystem.getImageMaxMbFileSize() + "MB).");
        }

        // TODO Esto deja convertir pero salta cuando se quiere persistir ese tipo de dato en la bd (si no es del tipo indicado)
        // Se podr[ia usar el detectSignature de fileSignatureUtility para verdificar
        String model = new String(dtoCreateActivityTypeVersion.getModel());
        String template = new String(dtoCreateActivityTypeVersion.getTemplate());
        String readme = new String(dtoCreateActivityTypeVersion.getReadme());

        audit.debug("Creating new Version.");
        ActivityTypeVersion activityTypeVersion =  new ActivityTypeVersion(model, template, readme);
        activityTypeVersion.setActivityTypeVersionStatusId(accessActivityTypeVersionStatus.get(1) // By default STAGED
                .orElseThrow(()-> new HttpNoContentException("Status of Activity Type Version not found.")));
        activityTypeVersion.setActivityTypeId(activityType);

        audit.debug("Saving new Activity Type Version.");
        try {
            accessActivityTypeVersion.save(activityTypeVersion)
                    .orElseThrow( ()-> new HttpInternalServerException("Failed to create new Activity Type Version.") );
        } catch (ConstraintViolationException e) {
            audit.debug("Version of Activity Type already exists. (Hint: Commit and push changes before create a new version).");
            throw new HttpBadRequestException("Version of Activity Type already exists. (Hint: Commit and push changes before create a new version).");
        } catch (DataException e) {
            audit.debug("Invalid of files uploaded. " + e);
            throw new HttpBadRequestException("Uploaded file content is not correct." + e);
        }

        audit.debug("Save thumbnail to file system.");
        utilityFileSystem.saveThumbnailToFileSystem(activityTypeVersion.getActivityTypeVersionId().toString(), thumbnail);
        activityTypeVersion.setThumbnail(thumbnail);

        audit.debug("Mapping Entity into DTO.");
        return mapperActivityTypeVersion.entityToDto(activityTypeVersion);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOActivityTypeVersion update(Integer id, DTOUpdateActivityTypeVersion dtoUpdateActivityTypeVersion) {

        Integer activityTypeVersionId = dtoUpdateActivityTypeVersion.getActivityTypeVersionId();
        Integer activityTypeVersionStatusId = dtoUpdateActivityTypeVersion.getActivityTypeVersionStatusId();

        ActivityTypeVersion activityTypeVersion = accessActivityTypeVersion.get(activityTypeVersionId)
                .orElseThrow( ()-> new HttpNoContentException("Activity Type Version not found.") );

        // TODO Perhaps we should deny the update if the status is 'DELETED'

        ActivityTypeVersionStatus activityTypeVersionStatus = accessActivityTypeVersionStatus.get(activityTypeVersionStatusId)
                .orElseThrow( ()-> new HttpNoContentException("Status of Activity Type Version not found.") );

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

        // The version already has been deleted
        if(activityTypeVersion.getActivityTypeVersionStatusId().getTitle().equals("DELETED")) {
            throw new HttpNoContentException("The ActivityTypeVersion already has been deleted.");
        }

        // The activityTypeVersion is logically removed, not physically
        activityTypeVersion.setActivityTypeVersionStatusId(accessActivityTypeVersionStatus.get(10) // DELETED Status
                .orElseThrow(()-> new HttpNoContentException("Status of Activity Type Version not found.")));

        audit.debug("Deleting Thumbnail File from FileSystem");
        utilityFileSystem.deleteThumbnailFromFileSystem(activityTypeVersion.getActivityTypeVersionId().toString());

        audit.debug("Mapping EntityType into DTO.");
        return mapperActivityTypeVersion.entityToDto(activityTypeVersion);

    }

    // VOTE HANDLING IN ACTIVITY TYPE VERSION

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOVote vote(Integer idActivityTypeVersion, Integer idUser) {

        audit.debug("Retrieving Entity Type");
        EntityType entityType = accessEntityType.getByName(ENTITY_NAME)
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
