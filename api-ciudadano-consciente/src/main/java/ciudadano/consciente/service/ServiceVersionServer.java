package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessVersionServer;
import ciudadano.consciente.clients.dto.DTOResponseContent;
import ciudadano.consciente.clients.github.service.ServiceGithubApi;
import ciudadano.consciente.dto.DTOCreateActivityTypeVersion;
import ciudadano.consciente.dto.DTOVersionContent;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.mapper.MapperActivityTypeVersion;
import ciudadano.consciente.model.ActivityTypeVersion;
import ciudadano.consciente.model.VersionServer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.jboss.logging.Logger;

import java.net.URI;

@RequestScoped
public class ServiceVersionServer {

    @Inject
    Logger audit;

    @Inject
    AccessVersionServer accessVersionServer;

    @Inject
    MapperActivityTypeVersion mapperActivityTypeVersion;

    @Inject
    ServiceGithubApi serviceGithubApi;

    public ActivityTypeVersion createVersion(String serverProvider, DTOCreateActivityTypeVersion dtoCreateActivityTypeVersion) {
        audit.debug("Retrieving Version Server " + serverProvider + ".");
        VersionServer versionServer = accessVersionServer.getByName(serverProvider)
                .orElseThrow(() -> new HttpNotFoundException("Version Server Not Found or Not Supported yet."));

        if(versionServer.getName().equals("github")) {
            return serviceGithubApi.createVersion(versionServer, dtoCreateActivityTypeVersion);
        }

        throw new HttpInternalServerException("Failed to create new version from server other than github.");

    }

    @Deprecated
    public DTOVersionContent getContent(ActivityTypeVersion activityTypeVersion) {

        audit.debug("Retrieving file contents from " + activityTypeVersion.getRepo());
        DTOResponseContent responseContent = null;
        if (activityTypeVersion.getVersionServer().getName().equals("github")) {
            responseContent = serviceGithubApi.fetchVersionContent(activityTypeVersion);
        } else {
            throw new HttpInternalServerException("Failed to retrieve content of version from server other than github.");
        }

        return mapperActivityTypeVersion.responseToVersionContent(responseContent);

    }

}
