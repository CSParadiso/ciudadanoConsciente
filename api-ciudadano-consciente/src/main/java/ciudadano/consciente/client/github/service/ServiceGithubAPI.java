package ciudadano.consciente.client.github.service;

import ciudadano.consciente.access.AccessFileNameRequiredVersionServer;
import ciudadano.consciente.client.github.interfaces.APIGithubRawContent;
import ciudadano.consciente.dto.DTOCreateActivityTypeVersionFromServer;
import ciudadano.consciente.model.ActivityTypeVersion;
import ciudadano.consciente.model.FileNameRequired;
import ciudadano.consciente.model.VersionServer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.*;

@RequestScoped
public class ServiceGithubAPI {

    @Inject
    Logger audit;

    @Inject
    AccessFileNameRequiredVersionServer accessFileNameRequiredVersionServer;

    @Inject
    @RestClient
    APIGithubRawContent apiGithubRawContent;

    public ActivityTypeVersion createVersion(VersionServer versionServer, DTOCreateActivityTypeVersionFromServer dtoCreateActivityTypeVersionFromServer) {

        // Retrieve path values
        String user = dtoCreateActivityTypeVersionFromServer.getUser();
        String repo = dtoCreateActivityTypeVersionFromServer.getRepo();
        String path = dtoCreateActivityTypeVersionFromServer.getPath();
        String commit = dtoCreateActivityTypeVersionFromServer.getCommit();

        // Create uri for the downloadUrl attribute
        String uri = versionServer.getContentUrl()
                .replace("{user}", user)
                .replace("{repo}", repo)
                .replace("{commit}", commit)
                .replace("{path}", path);

        // Retrieve filenames required
        List<FileNameRequired> fileNameRequiredList = accessFileNameRequiredVersionServer.getByVersionServer(versionServer);

        ActivityTypeVersion activityTypeVersion = new ActivityTypeVersion();
//        for(FileNameRequired filenameRequired : fileNameRequiredList) {
//
//            String filename = filenameRequired.getFileName();
//            try (Response response = apiGithubRawContent.fetchFile(user, repo, commit, path, filename)) {
//                if(response.getStatus() == 200 ) {
//                    switch(filename) {
//                        case "model.json" : activityTypeVersion.setModelDownloadUrl(uri.replace("{filename}", filename)); break;
//                        case "template.js" : activityTypeVersion.setTemplateDownloadUrl(uri.replace("{filename}", filename)); break;
//                        case "README.md" : activityTypeVersion.setReadmeDownloadUrl(uri.replace("{filename}", filename)); break;
//                        case "thumbnail.png" : activityTypeVersion.setThumbnailDownloadUrl(uri.replace("{filename}", filename)); break;
//                    }
//                } else {
//                    throw new HttpBadRequestException("Failde to retrive file " + filename + " from " + versionServer.getDescription() + " version server.");
//                }
//            } catch (Exception e) {
//                throw new HttpInternalServerException("Failed to fecth content from version server " + e);
//            }
//
//        }
//
//        activityTypeVersion.setUser(dtoCreateActivityTypeVersionFromServer.getUser());
//        activityTypeVersion.setPath(dtoCreateActivityTypeVersionFromServer.getPath());
//        activityTypeVersion.setRepo(dtoCreateActivityTypeVersionFromServer.getRepo());
//        activityTypeVersion.setCommit(dtoCreateActivityTypeVersionFromServer.getCommit());
//        activityTypeVersion.setVersionServer(versionServer);

        return activityTypeVersion;

    }

}
