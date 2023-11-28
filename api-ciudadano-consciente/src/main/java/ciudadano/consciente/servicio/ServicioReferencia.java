package ciudadano.consciente.servicio;

import ciudadano.consciente.acceso.AccesoNivel;
import ciudadano.consciente.acceso.AccesoReferencia;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.modelo.Nivel;
import ciudadano.consciente.modelo.Referencia;
import ciudadano.consciente.transferible.TransferibleReferencia;
import ciudadano.consciente.transformador.TransformadorReferencia;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.net.URL;

@RequestScoped
public class ServicioReferencia {

    @Inject
    AccesoNivel accesoNivel;

    @Inject
    AccesoReferencia accesoReferencia;

    @Inject
    TransformadorReferencia transformadorReferencia;

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleReferencia crear(Integer level, String title, String url, String description) {

        if(level == null ||
                title == null || title.trim().isEmpty() ||
                url == null || url.trim().isEmpty()
        ) {
            throw new HttpBadRequestException("Los campos son requeridos");
        }

        Nivel nivel = accesoNivel.obtener(level)
                .orElseThrow( ()-> new HttpNoContentException("No existe el Nivel a referenciar."));

        if(accesoReferencia.existeTituloEnNivel(nivel, title)) {
            throw new HttpBadRequestException("El nivel ya tiene una referencia con ese título.");
        } else {

            Referencia referencia = transformadorReferencia.transferibleAEntidad(title);
            referencia.setLevelId(nivel);
            referencia.setUrl(url);
            referencia.setDescription(description);

            referencia = accesoReferencia.persistir(referencia)
                    .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir nueva Referencia"));

            return transformadorReferencia.entidadATransferible(referencia);

        }

    }
}
