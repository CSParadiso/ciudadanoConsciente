package ciudadano.consciente.servicio;

import ciudadano.consciente.acceso.AccesoUsuario;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.modelo.Usuario;
import ciudadano.consciente.transferible.TransferibleRequestCrearUsuario;
import ciudadano.consciente.transferible.TransferibleUsuario;
import ciudadano.consciente.transformador.TransformadorUsuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;


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
    public TransferibleUsuario crear(TransferibleRequestCrearUsuario transferibleRequestCrearUsuario) {

        Usuario usuario = transformadorUsuario.transferibleAEntidad(transferibleRequestCrearUsuario);

        usuario = accesoUsuario.persistir(usuario)
                .orElseThrow(() -> new HttpInternalServerException("Error al persistir Usuario"));

        return transformadorUsuario.entidadATransferible(usuario);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void eliminar(Integer identificador) {

        if (!accesoUsuario.eliminar(identificador)) {
            throw new HttpNoContentException("Usuario a eliminar no existe");
        };

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleUsuario editar(Integer identificador, String email, String username, String password) {

        Usuario usuario = accesoUsuario.findByIdOptional(identificador)
                .orElseThrow( () -> new HttpNoContentException("El usuario no existe.") );

        if(email != null && !email.trim().isEmpty()) { usuario.setEmail(email); }
        if(username != null && !username.trim().isEmpty()) {usuario.setUsername(username);}
        if(password != null && !password.trim().isEmpty()) {usuario.setPassword(password);}

        Usuario usuarioActualizado = accesoUsuario.persistir(usuario)
                .orElseThrow( () -> new HttpInternalServerException("Problemas al persistir actualizació del usuario.") );

        return transformadorUsuario.entidadATransferible(usuarioActualizado);

    }
}
