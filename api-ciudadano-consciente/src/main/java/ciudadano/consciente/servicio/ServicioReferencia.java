package ciudadano.consciente.servicio;

import ciudadano.consciente.acceso.AccesoNivel;
import ciudadano.consciente.acceso.AccesoReferencia;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.modelo.Referencia;
import ciudadano.consciente.transferible.TransferibleActualizarReferencia;
import ciudadano.consciente.transferible.TransferibleCrearReferencia;
import ciudadano.consciente.transferible.TransferibleReferencia;
import ciudadano.consciente.transformador.TransformadorReferencia;
import ciudadano.consciente.utilidad.UtilidadCamposRequest;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class ServicioReferencia {

    @Inject
    UtilidadCamposRequest utilidadCamposRequest;

    @Inject
    AccesoNivel accesoNivel;

    @Inject
    AccesoReferencia accesoReferencia;

    @Inject
    TransformadorReferencia transformadorReferencia;

    public List<TransferibleReferencia> obtenerTodos() {

        return transformadorReferencia.entidadATransferible(accesoReferencia.obtenerTodos());

    }

    public TransferibleReferencia obtener(Integer identificador) {

        Referencia referencia = accesoReferencia.obtener(identificador)
                .orElseThrow( ()-> new HttpNotFoundException("La Referencia no existe.") );

        return transformadorReferencia.entidadATransferible(referencia);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleReferencia crear(TransferibleCrearReferencia transferibleCrearReferencia) {

        String title = transferibleCrearReferencia.getTitle();
        String url = transferibleCrearReferencia.getUrl();
        Integer level = transferibleCrearReferencia.getLevel();
        if(!utilidadCamposRequest.isCampoValido(title) ||
                !utilidadCamposRequest.isCampoValido(url) ||
                !utilidadCamposRequest.isCampoValido(level)) {
            throw new HttpBadRequestException("Los campos titulo, url y nivel son requeridos");
        }

        Referencia referencia = transformadorReferencia.transferibleAEntidad(title, url);

        referencia.setLevelId(accesoNivel.obtener(level)
                .orElseThrow(()-> new HttpNotFoundException("El Nivel no existe.")));

        if(accesoReferencia.existeTituloEnNivel(referencia.getLevelId(), referencia.getTitle())) {
            throw new HttpBadRequestException("Ya existe una referencia con ese título en este nivel.");
        }

        String description = transferibleCrearReferencia.getDescription();
        if(utilidadCamposRequest.isCampoValido(description)) {
            referencia.setDescription(description);
        }

        referencia = accesoReferencia.persistir(referencia)
                .orElseThrow(()-> new HttpInternalServerException("Problemas al persistir nueva Referencia."));

        return transformadorReferencia.entidadATransferible(referencia);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleReferencia actualizar(TransferibleActualizarReferencia transferibleActualizarReferencia) {

        Integer referenceID = transferibleActualizarReferencia.getReferenceId();
        if(!utilidadCamposRequest.isCampoValido(referenceID)) {
            throw new HttpBadRequestException("El campo identificador es requerido.");
        }

        Referencia referencia = accesoReferencia.obtener(referenceID)
                .orElseThrow(()-> new HttpNotFoundException("La Referencia no existe"));

        Integer levelId = transferibleActualizarReferencia.getLevel();
        String title = transferibleActualizarReferencia.getTitle();
        String url = transferibleActualizarReferencia.getUrl();
        String description = transferibleActualizarReferencia.getDescription();
        if(!utilidadCamposRequest.isCampoValido(levelId) &&
                !utilidadCamposRequest.isCampoValido(title) &&
                !utilidadCamposRequest.isCampoValido(url) &&
                !utilidadCamposRequest.isCampoValido(description)) {
            throw new HttpBadRequestException("Sin campos que actualizar");
        }

        if(utilidadCamposRequest.isCampoValido(levelId)) {
            referencia.setLevelId(accesoNivel.obtener(levelId)
                    .orElseThrow( ()-> new HttpNotFoundException("El nivel no existe.") ));
        }

        if(utilidadCamposRequest.isCampoValido(title)) {
            if(accesoReferencia.existeTituloEnNivel(referencia.getLevelId(), title)) {
                throw new HttpBadRequestException("Ya existe una referencia con ese título en este nivel.");
            } else {
                referencia.setTitle(title);
            };
        }

        if(utilidadCamposRequest.isCampoValido(url)) {
            referencia.setUrl(url);
        }

        if(utilidadCamposRequest.isCampoValido(description)) {
            referencia.setDescription(description);
        }

        referencia = accesoReferencia.persistir(referencia)
                .orElseThrow(()-> new HttpInternalServerException("Problemas al persistir actualización de Referencia."));

        return transformadorReferencia.entidadATransferible(referencia);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void eliminar(Integer identificador) {

        if(!accesoReferencia.eliminar(identificador)) {
            throw new HttpNotFoundException("Referencia a eliminar no existe");
        };

    }

}
