package ciudadano.consciente.servicio;

import ciudadano.consciente.acceso.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.modelo.Nivel;
import ciudadano.consciente.modelo.UsuarioRolNivel;
import ciudadano.consciente.transferible.*;
import ciudadano.consciente.transformador.TransformadorNivel;
import ciudadano.consciente.transformador.TransformadorUsuarioRolNivel;
import ciudadano.consciente.utilidad.UtilidadCamposRequest;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServicioNivel {

    @Inject
    UtilidadCamposRequest utilidadCamposRequest;

    @Inject
    AccesoNivel accesoNivel;

    @Inject
    AccesoOrganizacion accesoOrganizacion;

    @Inject
    TransformadorNivel transformadorNivel;

    @Inject
    AccesoUsuario accesoUsuario;

    @Inject
    AccesoRol accesoRol;

    @Inject
    TransformadorUsuarioRolNivel transformadorUsuarioRolNivel;

    @Inject
    AccesoUsuarioRolNivel accesoUsuarioRolNivel;

    public List<TransferibleNivel> obtenerTodos() {

        return transformadorNivel.entidadATransferible(accesoNivel.obtenerTodos());

    }

    public TransferibleNivel obtener(Integer identificador) {

        Nivel nivel = accesoNivel.obtener(identificador)
                .orElseThrow( () -> new HttpNoContentException("El Nivel no existe."));

        return  transformadorNivel.entidadATransferible(nivel);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleNivel crear(TransferibleCrearNivel transferibleCrearNivel) {

        String name = transferibleCrearNivel.getName();
        if(!utilidadCamposRequest.isCampoValido(name)) {
            throw new HttpBadRequestException("El nombre del nivel es requerido.");
        }
        if(accesoNivel.existeNombre(name)) { //TODO Se debería poder tener niveles con el mismo nombre. Lo que lo diferenciaría sería el padre.
            throw new HttpBadRequestException("El nombre de Nivel ya existe");
        }

        Nivel nivel = transformadorNivel.transferibleAEntidad(name);

        Integer organization = transferibleCrearNivel.getOrganization();
        if(utilidadCamposRequest.isCampoValido(organization)) {
            nivel.setOrganization(accesoOrganizacion.obtener(transferibleCrearNivel.getOrganization())
                    .orElse(null));
        }

        Integer parent =transferibleCrearNivel.getParent();
        if(utilidadCamposRequest.isCampoValido(parent)) {
            nivel.setParent(accesoNivel.obtener(parent)
                    .orElse(null));
        }

        String description = transferibleCrearNivel.getDescription();
        if(utilidadCamposRequest.isCampoValido(description)) {
            nivel.setDescription(transferibleCrearNivel.getDescription());
        }

        nivel = accesoNivel.persistir(nivel)
                .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir actualización de Nivel.") );

        return transformadorNivel.entidadATransferible(nivel);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleNivel actualizar(TransferibleActualizarNivel transferibleActualizarNivel) {

        Integer levelId = transferibleActualizarNivel.getLevelId();
        if(!utilidadCamposRequest.isCampoValido(levelId)) {
            throw new HttpBadRequestException("El campo identificador de Nivel es requerido.");
        }

        Nivel nivel = accesoNivel.obtener(levelId)
                .orElseThrow( () -> new HttpNoContentException("El Nivel no existe."));

        String name = transferibleActualizarNivel.getName();
        Integer organization = transferibleActualizarNivel.getOrganization();
        Integer parent =transferibleActualizarNivel.getParent();
        String description = transferibleActualizarNivel.getDescription();
        if(!utilidadCamposRequest.isCampoValido(name) &&
                !utilidadCamposRequest.isCampoValido(parent) &&
                !utilidadCamposRequest.isCampoValido(organization) &&
                !utilidadCamposRequest.isCampoValido(description)) {
            throw new HttpBadRequestException("Sin campos que actualizar.");
        }

        if(utilidadCamposRequest.isCampoValido(name)) {
            if(!accesoNivel.existeNombre(name)) {
                nivel.setName(name);
            } else {
                throw new HttpBadRequestException("El nombre de Nivel ya existe.");
            }
        }

        if (utilidadCamposRequest.isCampoValido(organization)) {
            nivel.setOrganization(accesoOrganizacion.obtener(organization)
                    .orElse(null));
        }

        if(utilidadCamposRequest.isCampoValido(parent)) {
            if(nivel.getLevelId() == parent) {
                throw new HttpBadRequestException("Un nivel no puede ser padre de sí mismo.");
            }
            nivel.setParent(accesoNivel.obtener(parent).orElse(null));
        }

        if(utilidadCamposRequest.isCampoValido(description)) {
            nivel.setDescription(transferibleActualizarNivel.getDescription());
        }

        nivel = accesoNivel.persistir(nivel)
                .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir actualización de Nivel.") );

        return transformadorNivel.entidadATransferible(nivel);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void eliminar(Integer identificador) {

        if(!accesoNivel.eliminar(identificador)) {
            throw new HttpNoContentException("Nivel no existe.");
        }

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleUsuarioRolNivel asignar(TransferibleAsignarRolUsuario transferibleAsignarRolUsuario) {

    Integer user = transferibleAsignarRolUsuario.getUser();
    Integer level = transferibleAsignarRolUsuario.getLevel();
    Integer role = transferibleAsignarRolUsuario.getRole();
    if(!utilidadCamposRequest.isCampoValido(user) ||
            !utilidadCamposRequest.isCampoValido(level) ||
            !utilidadCamposRequest.isCampoValido(role)) {
        throw new HttpBadRequestException("Todos los campos son requeridos");
    }

    UsuarioRolNivel usuarioRolNivel = new UsuarioRolNivel();

    usuarioRolNivel.setUser(accesoUsuario.obtener(user)
            .orElseThrow( ()-> new HttpNotFoundException("Usuario no existe.")));

    usuarioRolNivel.setLevel(accesoNivel.obtener(level)
            .orElseThrow( ()-> new HttpNotFoundException("Nivel no existe.")));

    usuarioRolNivel.setRole(accesoRol.obtener(role)
            .orElseThrow( ()-> new HttpNotFoundException("Rol no existe.")));

    usuarioRolNivel = accesoUsuarioRolNivel.persistir(usuarioRolNivel)
            .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir asignación de Rol de Usuario en Nivel"));

    return transformadorUsuarioRolNivel.entidadATransferible(usuarioRolNivel);

    }

}
