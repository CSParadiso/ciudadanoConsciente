package ciudadano.consciente.servicio;

import ciudadano.consciente.acceso.AccesoOrganizacion;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.modelo.Organizacion;
import ciudadano.consciente.transferible.TransferibleOrganizacion;
import ciudadano.consciente.transformador.TransformadorOrganizacion;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServicioOrganizacion {

    @Inject
    Logger auditor;

    @Inject
    AccesoOrganizacion accesoOrganizacion;

    @Inject
    TransformadorOrganizacion transformadorOrganizacion;



    public List<TransferibleOrganizacion> obtenerTodos() {

        List<Organizacion> organizaciones = accesoOrganizacion.obtenerTodos();
        return transformadorOrganizacion.entidadATransferible(organizaciones);

    }

    public TransferibleOrganizacion obtener(Integer identificador) {

        Organizacion organizacion = accesoOrganizacion.obtener(identificador)
                .orElseThrow( () -> new HttpNoContentException( "La organización no existe" ));

        return transformadorOrganizacion.entidadATransferible(organizacion);

    }
}
