package ciudadano.consciente.servicio;

import ciudadano.consciente.acceso.AccesoUsuario;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.modelo.Usuario;
import ciudadano.consciente.transferible.TransferibleUsuario;
import ciudadano.consciente.transformador.TransformadorUsuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;


@RequestScoped
public class ServicioUsuario {

    @Inject
    Logger auditor;

    @Inject
    AccesoUsuario accesoUsuario;

    @Inject
    TransformadorUsuario transformadorUsuario;

    public TransferibleUsuario obtener(Integer identificador) {

        auditor.debug("Servicio: intentando obtener usuario.");

        Usuario usuario = accesoUsuario.obtener(identificador) // Si obtiene nulo, lanza excepción
                .orElseThrow(() -> new HttpNoContentException("No existe el usuario con el identificador " + identificador));

        return transformadorUsuario.entidadATransferible(usuario);

    }

}
