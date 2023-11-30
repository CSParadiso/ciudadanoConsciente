package ciudadano.consciente.servicio;

import ciudadano.consciente.acceso.AccesoUsuario;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.modelo.Usuario;
import ciudadano.consciente.transferible.TransferibleActualizarUsuario;
import ciudadano.consciente.transferible.TransferibleCrearUsuario;
import ciudadano.consciente.transferible.TransferibleUsuario;
import ciudadano.consciente.transformador.TransformadorUsuario;
import ciudadano.consciente.utilidad.UtilidadCamposRequest;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;


@RequestScoped
public class ServicioUsuario {

    @Inject
    UtilidadCamposRequest utilidadCamposRequest;

    @Inject
    AccesoUsuario accesoUsuario;

    @Inject
    TransformadorUsuario transformadorUsuario;

    public TransferibleUsuario obtener(Integer identificador) {

        Usuario usuario = accesoUsuario.obtener(identificador) // Si obtiene nulo, lanza excepción
                .orElseThrow(() -> new HttpNoContentException("No existe el usuario con el identificador " + identificador));

        return transformadorUsuario.entidadATransferible(usuario);

    }

    public List<TransferibleUsuario> obtenerTodos() {

        List<Usuario> usuarioList = accesoUsuario.obtenerTodos();

        return transformadorUsuario.entidadATransferible(usuarioList);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleUsuario crear(TransferibleCrearUsuario transferibleCrearUsuario) {

        String email = transferibleCrearUsuario.getEmail();
        String username = transferibleCrearUsuario.getUsername();
        String password = transferibleCrearUsuario.getPassword();
        if(!utilidadCamposRequest.isCampoValido(email) ||
                !utilidadCamposRequest.isCampoValido(username) ||
                !utilidadCamposRequest.isCampoValido(password)) {
            throw new HttpBadRequestException("Todos los campos son requeridos.");
        }

        if(accesoUsuario.existeEmail(email)) {
            throw new HttpBadRequestException("El email ya existe.");
        }

        if(accesoUsuario.existeUsername(username)) {
            throw new HttpBadRequestException("El nombre de usuario ya existe.");
        }

        Usuario usuario = transformadorUsuario.transferibleAEntidad(email, username);

        if(utilidadCamposRequest.isCampoValido(password)) {
            usuario.setPassword(password);
        }

        usuario = accesoUsuario.persistir(usuario)
                .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir nuevo usuario."));

        return transformadorUsuario.entidadATransferible(usuario);

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
    public TransferibleUsuario actualizar(TransferibleActualizarUsuario transferibleActualizarUsuario) {

        Integer userId = transferibleActualizarUsuario.getIdentificador();
        if(!utilidadCamposRequest.isCampoValido(userId)) {
            throw new HttpBadRequestException("El campo identificador es requerido");
        }

        Usuario usuario = accesoUsuario.obtener(userId)
                .orElseThrow(()-> new HttpNoContentException("El Usuario no existe."));

        String email = transferibleActualizarUsuario.getEmail();
        String username = transferibleActualizarUsuario.getUsername();
        String password = transferibleActualizarUsuario.getPassword();
        if(!utilidadCamposRequest.isCampoValido(email) &&
                !utilidadCamposRequest.isCampoValido(username) &&
                !utilidadCamposRequest.isCampoValido(password)) {
            throw new HttpBadRequestException("Sin campos que actualizar");
        }

        if(utilidadCamposRequest.isCampoValido(email)) {
            if (!accesoUsuario.existeEmail(email)) {
                usuario.setEmail(email);
            } else {
                throw new HttpBadRequestException("El email ya existe.");
            }
        }

        if(utilidadCamposRequest.isCampoValido(username)) {
            if(!accesoUsuario.existeUsername(username)) {
                usuario.setUsername(username);
            } else {
                throw new HttpBadRequestException("El nombre de Usuario ya existe.");
            }
        }

        if(utilidadCamposRequest.isCampoValido(password)) {
            usuario.setPassword(password);
        }

        usuario = accesoUsuario.persistir(usuario)
                .orElseThrow( () -> new HttpInternalServerException("Problemas al persistir actualización de Usuario."));

        return transformadorUsuario.entidadATransferible(usuario);

    }

}
