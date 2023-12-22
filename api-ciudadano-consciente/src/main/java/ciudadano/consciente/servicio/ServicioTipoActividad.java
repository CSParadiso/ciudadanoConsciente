package ciudadano.consciente.servicio;

import ciudadano.consciente.acceso.AccesoTipoActividad;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.modelo.TipoActividad;
import ciudadano.consciente.transferible.TransferibleActualizarTipoActividad;
import ciudadano.consciente.transferible.TransferibleCrearTipoActividad;
import ciudadano.consciente.transferible.TransferibleTipoActividad;
import ciudadano.consciente.transformador.TransformadorTipoActividad;
import ciudadano.consciente.utilidad.UtilidadCamposRequest;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServicioTipoActividad {

    @Inject
    Logger auditor;

    @Inject
    UtilidadCamposRequest utilidadCamposRequest;

    @Inject
    TransformadorTipoActividad transformadorTipoActividad;

    @Inject
    AccesoTipoActividad accesoTipoActividad;

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleTipoActividad crear(TransferibleCrearTipoActividad transferibleCrearTipoActividad) {

        String name = transferibleCrearTipoActividad.getName();
        String description = transferibleCrearTipoActividad.getDescription();
        String functionalTemplateUrl = transferibleCrearTipoActividad.getFunctionalTemplateUrl();
        if(!utilidadCamposRequest.isCampoValido(name) ||
                !utilidadCamposRequest.isCampoValido(description) ||
                !utilidadCamposRequest.isCampoValido(functionalTemplateUrl)) {
            throw new HttpBadRequestException("Todos los campos son requeridos");
        }

        TipoActividad tipoActividad = transformadorTipoActividad.transferibleAEntidad(name, description, functionalTemplateUrl);

        tipoActividad = accesoTipoActividad.persistir(tipoActividad)
                .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir nuevo Tipo de Actividad."));

        return transformadorTipoActividad.entidadATransferible(tipoActividad);

    }

    public List<TransferibleTipoActividad> obtenerTodos() {

        return transformadorTipoActividad.entidadATransferible(accesoTipoActividad.obtenerTodos());

    }

    public TransferibleTipoActividad obtener(Integer identificador) {

        TipoActividad tipoActividad = accesoTipoActividad.obtener(identificador)
                .orElseThrow( ()-> new HttpNotFoundException("El Tipo de Actividad no existe."));

        return transformadorTipoActividad.entidadATransferible(tipoActividad);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void eliminar(Integer identificador) {

        if(!accesoTipoActividad.eliminar(identificador)) {
            throw new HttpNotFoundException("El Tipo de Actividad no existe.");
        }

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleTipoActividad actualizar(Integer identificador, TransferibleActualizarTipoActividad transferibleActualizarTipoActividad) {

        Integer activityTypeId = transferibleActualizarTipoActividad.getActivityTypeId();
        String name = transferibleActualizarTipoActividad.getName();
        String description = transferibleActualizarTipoActividad.getDescription();
        String functionalTemplate = transferibleActualizarTipoActividad.getFunctionalTemplateUrl();
        if(!utilidadCamposRequest.isCampoValido(activityTypeId) ||
            !utilidadCamposRequest.isCampoValido(name) ||
            !utilidadCamposRequest.isCampoValido(description) ||
            !utilidadCamposRequest.isCampoValido(functionalTemplate)) {
            throw new HttpBadRequestException("Todos los campos son requeridos.");
        }

        TipoActividad tipoActividad = accesoTipoActividad.obtener(identificador)
                .orElseThrow( ()-> new HttpNotFoundException("El Tipo de Actividad no existe."));

        transformadorTipoActividad.transferibleAEntidad(tipoActividad, transferibleActualizarTipoActividad);

        accesoTipoActividad.persistir(tipoActividad)
                .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir actualización de Tipo de Actividad.") );

        return transformadorTipoActividad.entidadATransferible(tipoActividad);

    }
}
