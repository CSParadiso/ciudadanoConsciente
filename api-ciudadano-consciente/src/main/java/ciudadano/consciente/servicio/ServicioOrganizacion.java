package ciudadano.consciente.servicio;

import ciudadano.consciente.acceso.AccesoOrganizacion;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.modelo.Organizacion;
import ciudadano.consciente.transferible.TransferibleActualizarOrganizacion;
import ciudadano.consciente.transferible.TransferibleCrearOrganizacion;
import ciudadano.consciente.transferible.TransferibleOrganizacion;
import ciudadano.consciente.transformador.TransformadorOrganizacion;
import ciudadano.consciente.utilidad.UtilidadCamposRequest;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class ServicioOrganizacion {

    @Inject
    UtilidadCamposRequest utilidadCamposRequest;

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
    public TransferibleOrganizacion crear(TransferibleCrearOrganizacion transferibleCrearOrganizacion) {

        String email = transferibleCrearOrganizacion.getEmail();
        if(!utilidadCamposRequest.isCampoValido(email)) {
            throw new HttpBadRequestException("El email es campo requerido.");
        }

        if(accesoOrganizacion.existeEmail(email)) {
            throw new HttpBadRequestException("El email ya existe");
        }

        Organizacion organizacion = transformadorOrganizacion.transferibleAEntidad(email);

        String name = transferibleCrearOrganizacion.getName();
        if(utilidadCamposRequest.isCampoValido(name)) {
            organizacion.setName(name);
        } else {
            throw new HttpBadRequestException("El nombre de la organización es requerido");
        }

        String description = transferibleCrearOrganizacion.getDescription();
        if(utilidadCamposRequest.isCampoValido(description)) {
            organizacion.setDescription(description);
        }

        organizacion = accesoOrganizacion.persistir(organizacion)
                .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir nueva Organización.") );

        return transformadorOrganizacion.entidadATransferible(organizacion);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleOrganizacion actualizar(TransferibleActualizarOrganizacion transferibleActualizarOrganizacion) {

        Integer organizationId = transferibleActualizarOrganizacion.getOrganizationId();
        if(!utilidadCamposRequest.isCampoValido(organizationId)) {
            throw new HttpBadRequestException("El identificador de la Organización es requerido.");
        }

        Organizacion organizacion = accesoOrganizacion.obtener(organizationId)
                .orElseThrow( () -> new HttpNoContentException("La organización no existe.") );

        String email = transferibleActualizarOrganizacion.getEmail();
        String name = transferibleActualizarOrganizacion.getName();
        String description = transferibleActualizarOrganizacion.getDescription();
        if(!utilidadCamposRequest.isCampoValido(email) &&
                !utilidadCamposRequest.isCampoValido(name) &&
                !utilidadCamposRequest.isCampoValido(description)) {
            throw new HttpBadRequestException("Sin campos que actualizar.");
        }

        if (utilidadCamposRequest.isCampoValido(name)) {
            if(!accesoOrganizacion.existeNombre(name)) {
                organizacion.setName(name);
            } else {
                throw new HttpBadRequestException("El nombre de la Organización ya existe.");
            }
        }

        if (utilidadCamposRequest.isCampoValido(email)) {
            if(!accesoOrganizacion.existeEmail(email)) {
                organizacion.setEmail(email);
            } else {
                throw new HttpBadRequestException("El email ya existe.");
            }
        }

        if (utilidadCamposRequest.isCampoValido(description)) {
            organizacion.setDescription(description);
        }

        organizacion = accesoOrganizacion.persistir(organizacion)
                .orElseThrow(()-> new HttpInternalServerException("Problemas al persistir actualización de Organización."));

        return transformadorOrganizacion.entidadATransferible(organizacion);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void eliminar(Integer identificador) {

        if(identificador == null) {
            throw new HttpBadRequestException("Identificador requerido.");
        }

        if(!accesoOrganizacion.eliminar(identificador)) {
            throw new HttpNoContentException("Organización a eliminar no existe");
        }

    }

}
