package ciudadano.consciente.acceso;

import ciudadano.consciente.modelo.TipoActividad;
import ciudadano.consciente.transferible.TransferibleTipoActividad;
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

        auditor.debug("Intentando persistir Tipo de Actividad");
        persist(tipoActividad);
        return findByIdOptional(tipoActividad.getActivityTypeId());

    }

    public List<TipoActividad> obtenerTodos() {

        auditor.debug("Intentando recuparar todos los Tipos de Actividad");
        return findAll().stream().toList();

    }
}
