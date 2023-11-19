package ciudadano.consciente.servicio;

import ciudadano.consciente.acceso.AccesoOrganizacion;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.modelo.Organizacion;
import ciudadano.consciente.transferible.TransferibleOrganizacion;
import ciudadano.consciente.transferible.TransferibleRequestCrearOrganizacion;
import ciudadano.consciente.transformador.TransformadorOrganizacion;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.net.URI;
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

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleOrganizacion crear(TransferibleRequestCrearOrganizacion transferibleRequestCrearOrganizacion) {

        Organizacion organizacion = transformadorOrganizacion.transferibleAEntidad(transferibleRequestCrearOrganizacion);

        organizacion = accesoOrganizacion.persisir(organizacion).
                orElseThrow( () -> new HttpInternalServerException("Problemas al persistir organización."));

        return transformadorOrganizacion.entidadATransferible(organizacion);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void eliminar(Integer identificador) {

        if(!accesoOrganizacion.eliminar(identificador)) {
            throw new HttpNoContentException("Organización a eliminar no existe");
        };

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleOrganizacion editar(Integer identificador, String name, String email, String description) {

        Organizacion organizacion = accesoOrganizacion.obtener(identificador)
                .orElseThrow( () -> new HttpNoContentException("La organización no existe.") );

        if(name != null && !name.trim().isEmpty()) { organizacion.setName(name); }
        auditor.debug(organizacion.getName());
        if(email != null && !email.trim().isEmpty()) { organizacion.setEmail(email); }
        auditor.debug(organizacion.getEmail());
        if(description != null && !description.trim().isEmpty()) { organizacion.setDescription(description); }
        auditor.debug(organizacion.getDescription());

        Organizacion organizacionActualizada = accesoOrganizacion.persisir(organizacion)
                .orElseThrow( () -> new HttpInternalServerException("Problemas al persistir actualización") );

        return transformadorOrganizacion.entidadATransferible(organizacionActualizada);

    }
}
