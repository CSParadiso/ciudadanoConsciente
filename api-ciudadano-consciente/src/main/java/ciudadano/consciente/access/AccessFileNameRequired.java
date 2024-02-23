package ciudadano.consciente.access;

import ciudadano.consciente.model.FileNameRequired;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.io.File;
import java.util.Collection;
import java.util.List;

@RequestScoped
public class AccessFileNameRequired implements PanacheRepositoryBase<FileNameRequired, Integer> {

    @Inject
    Logger audit;

    public List<FileNameRequired> getFileNames() {

        audit.debug("Trying to retrieve all file names required.");
        return findAll().stream().toList();

    }

}
