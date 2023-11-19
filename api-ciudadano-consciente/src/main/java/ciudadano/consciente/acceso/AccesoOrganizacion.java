package ciudadano.consciente.acceso;

import ciudadano.consciente.modelo.Organizacion;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccesoOrganizacion implements PanacheRepositoryBase<Organizacion, Integer> {

    @Inject
    Logger auditor;

    public List<Organizacion> obtenerTodos() {

        auditor.debug("Intentando recuperar todas las organizaciones.");
        return findAll().stream().toList();

    }

    public Optional<Organizacion> obtener(Integer identificador) {

        auditor.debug("Intentando recuparar organización " + identificador);
        return findByIdOptional(identificador);

    }
}
