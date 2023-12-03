package ciudadano.consciente.acceso;

import ciudadano.consciente.modelo.Nivel;
import ciudadano.consciente.modelo.Referencia;
import ciudadano.consciente.transferible.TransferibleReferencia;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccesoReferencia implements PanacheRepositoryBase<Referencia, Integer> {

    @Inject
    Logger auditor;

    public boolean existeTituloEnNivel(Nivel level, String title) {

        return count("levelId = ?1 and title = ?2", level, title) > 0;

    }

    public Optional<Referencia> persistir(Referencia referencia) {

        auditor.debug("Intentando persistir Referencia.");
        persist(referencia);
        return  findByIdOptional(referencia.getReferenceId());

    }

    public List<Referencia> obtenerTodos() {

        auditor.debug("Intentando recuperar todas las referencias.");
        return findAll().stream().toList();

    }

    public Optional<Referencia> obtener(Integer identificador) {

        auditor.debug("Intentando recuperar Referencia.");
        return findByIdOptional(identificador);

    }

    public boolean eliminar(Integer identificador) {

        auditor.debug("Intentando eliminar Referencia.");
        return deleteById(identificador);

    }
}
