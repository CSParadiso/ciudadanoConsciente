package ciudadano.consciente.access;

import ciudadano.consciente.model.VersionServer;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.Optional;

@RequestScoped
public class AccessVersionServer implements PanacheRepositoryBase<VersionServer, Integer> {

    @Inject
    Logger audit;


    public Optional<VersionServer> getByName(String serverProvider) {

        audit.debug("Trying to retrieve Version Server: " + serverProvider);
        return find("name", serverProvider).firstResultOptional();

    }

}
