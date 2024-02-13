package ciudadano.consciente.access;

import ciudadano.consciente.model.FileNameRequired;
import ciudadano.consciente.model.FileNameRequiredVersionServer;
import ciudadano.consciente.model.VersionServer;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class AccessFileNameRequiredVersionServer implements PanacheRepositoryBase<FileNameRequiredVersionServer, Integer> {

    @Inject
    Logger audit;

    public List<FileNameRequired> getByVersionServer(VersionServer versionServer) {

        audit.debug("Trying to retrieve al File Names Required for Version Server " + versionServer.getName());
        return find("versionServer", versionServer).stream()
                .map(FileNameRequiredVersionServer::getFileNameRequired)
                .toList();
    }

}
