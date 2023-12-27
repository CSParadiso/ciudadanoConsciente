package ciudadano.consciente.access;

import ciudadano.consciente.model.Level;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessLevel implements PanacheRepositoryBase<Level, Integer> {

    @Inject
    Logger auditor;

    public List<Level> obtenerTodos() {

        auditor.debug("Intentando recuparar todos los niveles.");
        return findAll(Sort.by("levelId")).stream().toList();

    }

    public Optional<Level> obtener(Integer identificador) {

        auditor.debug("Intentando recuperar Nivel " + identificador);
        return findByIdOptional(identificador);

    }

    public boolean eliminar(Integer identificador) {

        auditor.debug("Intentando eliminar Nivel " + identificador);
        return deleteById(identificador);

    }

    public Optional<Level> persistir(Level level) {

        auditor.debug("Intentando persistir Nivel");
        persist(level);
        return findByIdOptional(level.getLevelId());

    }

    public boolean existeNombre(String name) {

        auditor.debug("Verificando si existe el Nivel de nombre: " + name);
        return count("name", name) > 0;

    }
}
