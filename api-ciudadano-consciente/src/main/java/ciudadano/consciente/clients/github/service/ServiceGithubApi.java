package ciudadano.consciente.clients.github.service;

import ciudadano.consciente.access.AccessFileNameRequiredVersionServer;
import ciudadano.consciente.clients.dto.DTOResponseContent;
import ciudadano.consciente.clients.github.dto.DTOResponseCommitInfo;
import ciudadano.consciente.clients.github.dto.DTOResponseDownloadUrl;
import ciudadano.consciente.clients.github.dto.DTOResponseMetadata;
import ciudadano.consciente.clients.github.interfaces.APIGithubMetadata;
import ciudadano.consciente.clients.github.interfaces.APIGithubRawContent;
import ciudadano.consciente.dto.DTOCreateActivityTypeVersion;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.model.ActivityTypeVersion;
import ciudadano.consciente.model.FileNameRequired;
import ciudadano.consciente.model.VersionServer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequestScoped
public class ServiceGithubApi {

    @Inject
    Logger audit;

    @Inject
    AccessFileNameRequiredVersionServer accessFileNameRequiredVersionServer;

    @Inject
    @RestClient
    APIGithubMetadata apiGithubMetadata;

    @Inject
    @RestClient
    APIGithubRawContent apiGithubRawContent;

    public ActivityTypeVersion createVersion(VersionServer versionServer, DTOCreateActivityTypeVersion dtoCreateActivityTypeVersion) {

        audit.debug("Getting files names required for Version Server.");
        Set<String> fileNamesRequired = getFileNamesRequiredByVersionServer(versionServer);

        audit.debug("Fetching metadata from Version Server.");
        List<DTOResponseMetadata> metadata = fetchMetadata(versionServer, dtoCreateActivityTypeVersion);

        audit.debug("Validating metadata retrieved from VersionServer");
        validateRequiredFiles(metadata, fileNamesRequired);

        audit.debug("Retrieving sha of commit from VersionServer");
        DTOResponseCommitInfo commitInfo = fetchCommitSha(versionServer, dtoCreateActivityTypeVersion);

        audit.debug("Retrieving download url of the committed files from VersionServer");
        List<DTOResponseDownloadUrl> dtoResponseDownloadURL = fetchDownloadUrl(commitInfo, metadata, dtoCreateActivityTypeVersion, versionServer);

        audit.debug("Creating new Version of Activity Type.");
        return createActivityTypeVersion(dtoCreateActivityTypeVersion, versionServer, commitInfo, dtoResponseDownloadURL);

    }

    public DTOResponseContent fetchVersionContent(ActivityTypeVersion activityTypeVersion) {

        audit.debug("Recovering version content");
        return fetchContent(activityTypeVersion);

    }

    private List<DTOResponseDownloadUrl> fetchDownloadUrl(DTOResponseCommitInfo shaCommitInfo, List<DTOResponseMetadata> metadata, DTOCreateActivityTypeVersion dtoCreateActivityTypeVersion, VersionServer versionServer) {

        String user = dtoCreateActivityTypeVersion.getUser();
        String repo = dtoCreateActivityTypeVersion.getRepo();
        String path = dtoCreateActivityTypeVersion.getPath();
        String shaCommit = shaCommitInfo.getCommitInfo().getShaCommit();

        List<DTOResponseDownloadUrl> responseDownloadUrls = new ArrayList<>();
        for(DTOResponseMetadata file : metadata) {

            try (Response response = apiGithubMetadata.fetchDownloadUrl(user, repo, path, file.getName(), shaCommit)) {
                if (response.getStatus() == 200) {
                    audit.debug("Response successfully retrieved from " + file.getName() + ".");
                    responseDownloadUrls.add(response.readEntity(new GenericType<>() {}));
                } else {
                    throw new HttpInternalServerException("Failed to fetch download url from " + file.getName() + ".");
                }
            } catch (Exception e) {
                throw new HttpInternalServerException("Failed to fetch download url from " + file.getName() + ".: " + e);
            }
        }

        return responseDownloadUrls;

    }

    private DTOResponseCommitInfo fetchCommitSha(VersionServer versionServer, DTOCreateActivityTypeVersion dtoCreateActivityTypeVersion) {

        String user = dtoCreateActivityTypeVersion.getUser();
        String repo = dtoCreateActivityTypeVersion.getRepo();
        String branch = dtoCreateActivityTypeVersion.getBranch();

        try (Response response = apiGithubMetadata.fetchCommitInfo(user, repo, branch)) {
            if (response.getStatus() == 200) {
                audit.debug("Response successfully retrieved from " + versionServer.getName() + ".");
                return response.readEntity(new GenericType<>() {});
            } else {
                throw new HttpInternalServerException("Failed to fetch commit info from " + versionServer.getName() + ".");
            }
        } catch (Exception e) {
            throw new HttpInternalServerException("Failed to fetch commit info from " + versionServer.getName() + ".: " + e);
        }

    }

    private Set<String> getFileNamesRequiredByVersionServer(VersionServer versionServer) {
        List<FileNameRequired> fileNameRequiredList = accessFileNameRequiredVersionServer.getByVersionServer(versionServer);
        return fileNameRequiredList.stream()
                .map(FileNameRequired::getFileName)
                .collect(Collectors.toSet());
    }

    private List<DTOResponseMetadata> fetchMetadata(VersionServer versionServer, DTOCreateActivityTypeVersion dtoCreateActivityTypeVersion) {
        String user = dtoCreateActivityTypeVersion.getUser();
        String repo = dtoCreateActivityTypeVersion.getRepo();
        String path = dtoCreateActivityTypeVersion.getPath();

        try (Response response = apiGithubMetadata.fetchMetadata(user, repo, path)) {
            if (response.getStatus() == 200) {
                audit.debug("Response successfully retrieved from " + versionServer.getName() + ".");
                return response.readEntity(new GenericType<>() {});
            } else {
                throw new HttpInternalServerException("Failed to fetch metadata from " + versionServer.getName() + ".");
            }
        } catch (Exception e) {
            throw new HttpInternalServerException("Failed to fetch metadata from " + versionServer.getName() + ".: " + e);
        }
    }

    private void validateRequiredFiles(List<DTOResponseMetadata> metadata, Set<String> fileNamesRequired) {
        Set<String> existingFileNames = metadata.stream()
                .map(DTOResponseMetadata::getName)
                .collect(Collectors.toSet());

        if (!existingFileNames.containsAll(fileNamesRequired)) {
            Set<String> missingFiles = new HashSet<>(fileNamesRequired);
            missingFiles.removeAll(existingFileNames);
            String errorMessage = "The files of the version server are not the required ones. Missing files: " + missingFiles + ".";
            audit.debug(errorMessage);
            throw new HttpBadRequestException(errorMessage);
        }
    }

    private ActivityTypeVersion createActivityTypeVersion(DTOCreateActivityTypeVersion dtoCreateActivityTypeVersion, VersionServer versionServer, DTOResponseCommitInfo shaCommit, List<DTOResponseDownloadUrl> downloadUrlList) {
        ActivityTypeVersion activityTypeVersion = new ActivityTypeVersion();
        try {

            Map<String, DTOResponseDownloadUrl> downloadUrlMap = downloadUrlList.stream()
                    .collect(Collectors.toMap(DTOResponseDownloadUrl::getName, Function.identity()));

            activityTypeVersion.setModelDownloadUrl(downloadUrlMap.get("model.json").getDownloadUrl());
            activityTypeVersion.setTemplateDownloadUrl(downloadUrlMap.get("template.js").getDownloadUrl());
            activityTypeVersion.setReadmeDownloadUrl(downloadUrlMap.get("README.md").getDownloadUrl());
            activityTypeVersion.setThumbnailDownloadUrl(downloadUrlMap.get("thumbnail.png").getDownloadUrl());

            activityTypeVersion.setUser(dtoCreateActivityTypeVersion.getUser());
            activityTypeVersion.setPath(dtoCreateActivityTypeVersion.getPath());
            activityTypeVersion.setBranch(dtoCreateActivityTypeVersion.getBranch());
            activityTypeVersion.setRepo(dtoCreateActivityTypeVersion.getRepo());
            activityTypeVersion.setShaCommit(shaCommit.getCommitInfo().getShaCommit());

            audit.debug("Setting versionServer into ActivityTypeVersion" + versionServer.getName());
            activityTypeVersion.setVersionServer(versionServer);

            return activityTypeVersion;
        } catch (Exception e) {
            throw new HttpInternalServerException("Failed to create ActivityTypeVersion." + e);
        }
    }

    private DTOResponseContent fetchContent(ActivityTypeVersion activityTypeVersion) {

        String user = activityTypeVersion.getUser();
        String repo = activityTypeVersion.getRepo();
        String shaCommit = activityTypeVersion.getShaCommit();
        String path = activityTypeVersion.getPath();

        // {user}/{repo}/{shaCommit}/{path}/{filename}")
        DTOResponseContent responseContent = new DTOResponseContent();
        try {
            // Fetch model.json
                String modelContent = fetchStringContent(user, repo, shaCommit, path, "model.json");
                responseContent.setModel(new DTOResponseContent.DTOResponseStringContent(modelContent));

            // Fetch template.js
                String templateContent = fetchStringContent(user, repo, shaCommit, path, "template.js");
                responseContent.setTemplate(new DTOResponseContent.DTOResponseStringContent(templateContent));

            // Fetch README.md
                String readmeContent = fetchStringContent(user, repo, shaCommit, path, "README.md");
                responseContent.setReadme(new DTOResponseContent.DTOResponseStringContent(readmeContent));

            // Fetch thumbnail.png
                byte[] thumbnailContent = fetchBinaryContent(user, repo, shaCommit, path, "thumbnail.png");
                responseContent.setThumbnail(thumbnailContent);

            return responseContent;
        } catch (Exception e) {
            throw new HttpInternalServerException("Failed to fetch content from " + activityTypeVersion.getVersionServer().getName() + ": " + e.getMessage() + e);
        }
    }

    private String fetchStringContent(String user, String repo, String shaCommit, String path, String filename) {

        try (Response response = apiGithubRawContent.fetchFile(user, repo, shaCommit, path, filename)) {
            if (response.getStatus() == 200) {
                return response.readEntity(String.class);
            } else {
                throw new HttpInternalServerException("Failed to fetch content from " + filename + ". Status: " + response.getStatus());
            }
        } catch (Exception e) {
            throw new HttpInternalServerException("Failed to fetch content from " + filename + ": " + e.getMessage() + e);
        }
    }

    private byte[] fetchBinaryContent(String user, String repo, String shaCommit, String path, String filename) {

        try (Response response = apiGithubRawContent.fetchFile(user, repo, shaCommit, path, filename)) {
            if (response.getStatus() == 200) {
                return response.readEntity(byte[].class);
            } else {
                throw new HttpInternalServerException("Failed to fetch content from " + filename + ". Status: " + response.getStatus());
            }
        } catch (Exception e) {
            throw new HttpInternalServerException("Failed to fetch content from " + filename + ": " + e.getMessage() + e);
        }
    }

}
