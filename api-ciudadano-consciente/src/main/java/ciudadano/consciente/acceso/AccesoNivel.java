package ciudadano.consciente.acceso;

import ciudadano.consciente.modelo.Nivel;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccesoNivel implements PanacheRepositoryBase<Nivel, Integer> {

    @Inject
    Logger auditor;

    public List<Nivel> obtenerTodos() {

        auditor.debug("Intentando recuparar todos los niveles.");
        return findAll(Sort.by("levelId")).stream().toList();

    }

    public Optional<Nivel> obtener(Integer identificador) {

        auditor.debug("Intentando recuperar Nivel " + identificador);
        return findByIdOptional(identificador);

    }

    public boolean eliminar(Integer identificador) {

        auditor.debug("Intentando eliminar Nivel " + identificador);
        return deleteById(identificador);

    }

    public Optional<Nivel> persistir(Nivel nivel) {

        auditor.debug("Intentando persistir Nivel");
        persist(nivel);
        return findByIdOptional(nivel.getLevelId());

    }

    public boolean existeNombre(String name) {

        return count("name", name) > 0;

    }
}
