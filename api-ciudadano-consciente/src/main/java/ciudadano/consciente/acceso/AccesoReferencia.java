package ciudadano.consciente.acceso;

import ciudadano.consciente.modelo.Nivel;
import ciudadano.consciente.modelo.Referencia;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

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

}
