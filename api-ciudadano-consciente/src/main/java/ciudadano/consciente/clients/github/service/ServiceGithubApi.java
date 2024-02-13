package ciudadano.consciente.clients.github.service;

import ciudadano.consciente.access.AccessFileNameRequiredVersionServer;
import ciudadano.consciente.clients.github.interfaces.APIGithubRawContent;
import ciudadano.consciente.dto.DTOCreateActivityTypeVersion;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.model.ActivityTypeVersion;
import ciudadano.consciente.model.FileNameRequired;
import ciudadano.consciente.model.VersionServer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.*;

@RequestScoped
public class ServiceGithubApi {

    @Inject
    Logger audit;

    @Inject
    AccessFileNameRequiredVersionServer accessFileNameRequiredVersionServer;

    @Inject
    @RestClient
    APIGithubRawContent apiGithubRawContent;

    public ActivityTypeVersion createVersion(VersionServer versionServer, DTOCreateActivityTypeVersion dtoCreateActivityTypeVersion) {

        // Retrieve path values
        String user = dtoCreateActivityTypeVersion.getUser();
        String repo = dtoCreateActivityTypeVersion.getRepo();
        String path = dtoCreateActivityTypeVersion.getPath();
        String commit = dtoCreateActivityTypeVersion.getCommit();

        // Create uri for the downloadUrl attribute
        String uri = versionServer.getContentUrl()
                .replace("{user}", user)
                .replace("{repo}", repo)
                .replace("{commit}", commit)
                .replace("{path}", path);

        // Retrieve filenames required
        List<FileNameRequired> fileNameRequiredList = accessFileNameRequiredVersionServer.getByVersionServer(versionServer);

        ActivityTypeVersion activityTypeVersion = new ActivityTypeVersion();
        for(FileNameRequired filenameRequired : fileNameRequiredList) {

            String filename = filenameRequired.getFileName();
            try (Response response = apiGithubRawContent.fetchFile(user, repo, commit, path, filename)) {
                if(response.getStatus() == 200 ) {
                    switch(filename) {
                        case "model.json" : activityTypeVersion.setModelDownloadUrl(uri.replace("{filename}", filename)); break;
                        case "template.js" : activityTypeVersion.setTemplateDownloadUrl(uri.replace("{filename}", filename)); break;
                        case "README.md" : activityTypeVersion.setReadmeDownloadUrl(uri.replace("{filename}", filename)); break;
                        case "thumbnail.png" : activityTypeVersion.setThumbnailDownloadUrl(uri.replace("{filename}", filename)); break;
                    }
                } else {
                    throw new HttpBadRequestException("Failde to retrive file " + filename + " from " + versionServer.getName() + " version server.");
                }
            } catch (Exception e) {
                throw new HttpInternalServerException("Failed to fecth content from version server " + e);
            }

        }

        activityTypeVersion.setUser(dtoCreateActivityTypeVersion.getUser());
        activityTypeVersion.setPath(dtoCreateActivityTypeVersion.getPath());
        activityTypeVersion.setRepo(dtoCreateActivityTypeVersion.getRepo());
        activityTypeVersion.setCommit(dtoCreateActivityTypeVersion.getCommit());
        activityTypeVersion.setVersionServer(versionServer);

        return activityTypeVersion;

    }

}
