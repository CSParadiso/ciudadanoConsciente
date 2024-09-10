package ciudadano.consciente.service;

import ciudadano.consciente.client.github.service.ServiceGithubAPI;
import ciudadano.consciente.dto.DTOCreateActivityTypeVersionFromServer;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.model.ActivityTypeVersion;
import ciudadano.consciente.model.VersionServer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@RequestScoped
public class ServiceVersionServer {

    @Inject
    Logger audit;

    @Inject
    ServiceGithubAPI serviceGithubApi;

    public ActivityTypeVersion createVersion(VersionServer versionServer, DTOCreateActivityTypeVersionFromServer dtoCreateActivityTypeVersionFromServer) {

        audit.debug("Trying to create new version from server " + versionServer.getName());
        if(versionServer.getName().equals("github")) {
            return serviceGithubApi.createVersion(versionServer, dtoCreateActivityTypeVersionFromServer);
        }

        throw new HttpInternalServerException("Failed to create new version from server other than github.");

    }

}
