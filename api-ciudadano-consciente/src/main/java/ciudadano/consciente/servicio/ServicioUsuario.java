package ciudadano.consciente.servicio;

import ciudadano.consciente.acceso.AccesoUsuario;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.modelo.Usuario;
import ciudadano.consciente.transferible.TransferibleUsuario;
import ciudadano.consciente.transformador.TransformadorUsuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;


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

    public List<TransferibleUsuario> obtenerTodos() {

        List<Usuario> usuarioList = accesoUsuario.obtenerTodos();

        return transformadorUsuario.entidadATransferible(usuarioList);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleUsuario crear(String email, String username, String password) {

        if(username == null || username.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            throw new HttpBadRequestException("Todos los campos son requeridos para crear un Usuario.");
        }

        if(!accesoUsuario.existeUsername(username)) {
            Usuario usuario = transformadorUsuario.transferibleAEntidad(username);

            if(!accesoUsuario.existeEmail(email)) {
                usuario.setEmail(email);
            } else {
                throw new HttpBadRequestException("El email de Usuario ya existe o no es válido.");
            }

            usuario.setPassword(password);

            usuario = accesoUsuario.persistir(usuario)
                    .orElseThrow( () -> new HttpInternalServerException("Problemas al persistir nuevo Usuario."));

            return transformadorUsuario.entidadATransferible(usuario);

        } else {
            throw new HttpBadRequestException("El nombre de Usuario ya existe o no es válido.");
        }

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void eliminar(Integer identificador) {

        if(identificador == null) {
            throw new HttpBadRequestException("Identificador requerido.");
        }

        if (!accesoUsuario.eliminar(identificador)) {
            throw new HttpNoContentException("Usuario a eliminar no existe");
        };

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleUsuario actualizar(Integer identificador, String email, String username, String password) {

        if((username == null || username.trim().isEmpty()) &&
                (email == null || email.trim().isEmpty()) &&
                (password == null || password.trim().isEmpty())) {
            throw new HttpBadRequestException("Sin campos que actualizar.");
        }

        Usuario usuario = accesoUsuario.findByIdOptional(identificador)
                .orElseThrow( () -> new HttpNoContentException("El usuario no existe.") );

        if((username != null && !username.trim().isEmpty())) {
            if (!accesoUsuario.existeUsername(username)) {
                usuario.setUsername(username);
            } else {
                throw new HttpBadRequestException("El nombre de Usuario ya existe o no es válido.");
            }
        }

        if(email != null && !email.trim().isEmpty()) {
            if(!accesoUsuario.existeEmail(email)) {
                usuario.setEmail(email);
            } else {
                throw new HttpBadRequestException("El email de Usuario ya existe o no es válido.");
            }
        }

        if(password != null && !password.trim().isEmpty()) {
            usuario.setPassword(password);
        }

        usuario = accesoUsuario.persistir(usuario)
                .orElseThrow( () -> new HttpInternalServerException("Problemas al persistir actualización de Usuario."));

        return transformadorUsuario.entidadATransferible(usuario);

    }

}
