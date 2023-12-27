package ciudadano.consciente.access;

import ciudadano.consciente.model.Level;
import ciudadano.consciente.model.Reference;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessReference implements PanacheRepositoryBase<Reference, Integer> {

    @Inject
    Logger auditor;

    public boolean existeTituloEnNivel(Level level, String title) {

        return count("levelId = ?1 and title = ?2", level, title) > 0;

    }

    public Optional<Reference> persistir(Reference reference) {

        auditor.debug("Intentando persistir Referencia.");
        persist(reference);
        return  findByIdOptional(reference.getReferenceId());

    }

    public List<Reference> obtenerTodos() {

        auditor.debug("Intentando recuperar todas las referencias.");
        return findAll().stream().toList();

    }

    public Optional<Reference> obtener(Integer identificador) {

        auditor.debug("Intentando recuperar Referencia.");
        return findByIdOptional(identificador);

    }

    public boolean eliminar(Integer identificador) {

        auditor.debug("Intentando eliminar Referencia.");
        return deleteById(identificador);

    }
}
