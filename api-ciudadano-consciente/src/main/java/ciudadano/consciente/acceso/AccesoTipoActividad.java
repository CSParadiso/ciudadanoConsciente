package ciudadano.consciente.acceso;

import ciudadano.consciente.modelo.TipoActividad;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccesoTipoActividad implements PanacheRepositoryBase<TipoActividad, Integer> {

    @Inject
    Logger auditor;

    public Optional<TipoActividad> persistir(TipoActividad tipoActividad) {

        auditor.debug("Intentando persistir Tipo de Actividad " + tipoActividad.getName());
        persist(tipoActividad);
        return findByIdOptional(tipoActividad.getActivityTypeId());

    }

    public List<TipoActividad> obtenerTodos() {

        auditor.debug("Intentando recuparar todos los Tipos de Actividad");
        return findAll().stream().toList();

    }

    public Optional<TipoActividad> obtener(Integer identificador) {

        auditor.debug("Intentando recuperar el Tipo de Actividad " + identificador);
        return findByIdOptional(identificador);

    }

    public boolean eliminar(Integer identificador) {

        auditor.debug("Intentando eliminar el Tipo de Actividad " + identificador);
        return deleteById(identificador) ;

    }
}
