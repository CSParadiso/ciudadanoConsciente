package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessFileNameRequiredVersionServer;
import ciudadano.consciente.access.AccessVersionServer;
import ciudadano.consciente.dto.DTOActivityTypeVersion;
import ciudadano.consciente.dto.DTOCreateActivityTypeVersion;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.model.ActivityTypeVersion;
import ciudadano.consciente.model.FileNameRequired;
import ciudadano.consciente.model.VersionServer;
import ciudadano.consciente.usedAPIs.dto.DTOResponseGithubMetadata;
import ciudadano.consciente.usedAPIs.interfaces.ServiceGithubMetadata;
import com.arjuna.ats.internal.jdbc.drivers.modifiers.list;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequestScoped
public class ServiceVersionServer {

    @Inject
    Logger audit;

    @Inject
    AccessVersionServer accessVersionServer;

    @Inject
    AccessFileNameRequiredVersionServer accessFileNameRequiredVersionServer;

    @RestClient
    ServiceGithubMetadata serviceGithubMetadata;

    public ActivityTypeVersion create(String serverProvider, DTOCreateActivityTypeVersion dtoCreateActivityTypeVersion) {

        audit.debug("Verifying if Version Server is supported.");
        VersionServer versionServer = accessVersionServer.getByName(serverProvider)
                .orElseThrow( ()-> new HttpNotFoundException("Version Server Not Found."));

        audit.debug("Retrieving files names required by version server");
        List<String> fileNamesRequired = retrieveFileNamesRequiredByVersionServer(versionServer);

        audit.debug("Fetching metadata from version server");
        List<DTOResponseGithubMetadata>  metadata = fecthMetadata(versionServer, dtoCreateActivityTypeVersion);

        audit.debug("Reading file names from Response from versionServer");
        List<String> existingFileNames = new ArrayList<>();
        for(DTOResponseGithubMetadata file : metadata) {
            if (fileNamesRequired.contains(file.getName())) {
                existingFileNames.add(file.getName());
            }
        }

        ActivityTypeVersion activityTypeVersion = new ActivityTypeVersion();
        if (fileNamesRequired.size() != existingFileNames.size()) {

            // Return the missing files
            List<String> missingFiles = new ArrayList<>(fileNamesRequired);
            missingFiles.removeAll(existingFileNames);

            audit.debug("The files of the version server are not the required the required ones.");
            throw new HttpBadRequestException("The files of the version server are not the required ones. " +
                    "Missing files: " + missingFiles + ".");

        } else {

            DTOResponseGithubMetadata model = null;
            DTOResponseGithubMetadata template = null;
            DTOResponseGithubMetadata readme = null;
            DTOResponseGithubMetadata thumbnail = null;

            for(DTOResponseGithubMetadata file : metadata) {
                switch (file.getName()) {
                    case "model.json" : model = file; break;
                    case "template.js" : template = file; break;
                    case "README.md" : readme = file; break;
                    case "thumbnail.png" : thumbnail = file; break;
                    default: return null;
                }
            }

            audit.debug("Creating version");
            try {
                activityTypeVersion.setUser(dtoCreateActivityTypeVersion.getUser());
                activityTypeVersion.setPath(dtoCreateActivityTypeVersion.getPath());
                activityTypeVersion.setRepo(dtoCreateActivityTypeVersion.getRepo());
                //audit.debug("Name: " + model.getName() + ", SHA: " + model.getSha());
                //audit.debug("Name: " + readme.getName() + ", SHA: " + readme.getSha());
                //audit.debug("Name: " + template.getName() + ", SHA: " + template.getSha());
                //audit.debug("Name: " + thumbnail.getName() + ", SHA: " + thumbnail.getSha());
                activityTypeVersion.setShaModel(model.getSha());
                activityTypeVersion.setShaTemplate(template.getSha());
                activityTypeVersion.setShaReadme(readme.getSha());
                activityTypeVersion.setShaThumbnail(thumbnail.getSha());

            } catch (Exception e) {
                throw new HttpInternalServerException("Failed to map/create new version from response retrieved from version server. " + e);
            }

        }

        return activityTypeVersion;

    }

    private List<DTOResponseGithubMetadata> fecthMetadata(VersionServer versionServer, DTOCreateActivityTypeVersion dtoCreateActivityTypeVersion) {

        audit.debug("Fetching metadata from versionServer " + versionServer.getName());
        String user = dtoCreateActivityTypeVersion.getUser();
        String repo = dtoCreateActivityTypeVersion.getRepo();
        String path = dtoCreateActivityTypeVersion.getPath();

        try (Response response = serviceGithubMetadata.verifyFiles(user, repo, path);) {
            if(response.getStatus() == 200) {
                audit.debug(response);
                return response.readEntity(new GenericType<>() {});

            }
            audit.debug(response);
            throw new HttpInternalServerException("Failed to retrieve Metadata from version server.");
        } catch (Exception e) {
            throw new HttpInternalServerException("Failed to retrieve Metadata from version server: " + e);
        }

    }

    private List<String> retrieveFileNamesRequiredByVersionServer(VersionServer versionServer) {

        List<FileNameRequired> fileNameRequiredList = accessFileNameRequiredVersionServer.getByVersionServer(versionServer);

        return fileNameRequiredList.stream()
                .map(FileNameRequired::getFileName)
                .toList();

    }

}
