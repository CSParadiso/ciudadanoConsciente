package ciudadano.consciente.servicio;

import ciudadano.consciente.acceso.AccesoRol;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.modelo.Rol;
import ciudadano.consciente.transferible.TransferibleActualizarRol;
import ciudadano.consciente.transferible.TransferibleCrearRol;
import ciudadano.consciente.transferible.TransferibleRol;
import ciudadano.consciente.transformador.TransformadorRol;
import ciudadano.consciente.utilidad.UtilidadCamposRequest;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class ServicioRol {

    @Inject
    UtilidadCamposRequest utilidadCamposRequest;

    @Inject
    TransformadorRol transformadorRol;

    @Inject
    AccesoRol accesoRol;

    public List<TransferibleRol> obtenerTodos() {

        return transformadorRol.entidadATransferible(accesoRol.obtenerTodos());

    }

    public TransferibleRol obtener(Integer identificador) {

        Rol rol = accesoRol.obtener(identificador)
                .orElseThrow( ()-> new HttpNoContentException("El Rol no existe."));

        return transformadorRol.entidadATransferible(rol);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleRol crear(TransferibleCrearRol transferibleCrearRol) {

        String name = transferibleCrearRol.getName();
        if(!utilidadCamposRequest.isCampoValido(name)) {
            throw new HttpBadRequestException("Campo requerido sin completar.");
        }

        if(accesoRol.existeNombre(name)) {
            throw new HttpBadRequestException("El rol ya existe");
        }

        Rol rol = transformadorRol.transferibleAEntidad(name);

        rol = accesoRol.persistir(rol)
                .orElseThrow(()-> new HttpInternalServerException("Problemas al persistir nuevo Rol."));

        return transformadorRol.entidadATransferible(rol);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void eliminar(Integer identificador) {

        if(identificador == null) {
            throw new HttpBadRequestException("El identificador es requerido.");
        }

        if(!accesoRol.eliminar(identificador)) {
            throw new HttpNoContentException("Rol a eliminar no existe.");
        }

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleRol actualizar(TransferibleActualizarRol transferibleActualizarRol) {

        Integer roleId = transferibleActualizarRol.getId();
        if(!utilidadCamposRequest.isCampoValido(roleId)) {
            throw new HttpBadRequestException("El campo identificador es requerido.");
        }

        Rol rol = accesoRol.obtener(roleId)
                .orElseThrow( ()-> new HttpNoContentException("El Rol no existe."));

        String name = transferibleActualizarRol.getName();
        if(!utilidadCamposRequest.isCampoValido(name)) {
            throw new HttpBadRequestException("Sin campos que actualizar");
        }

        if(accesoRol.existeNombre(name)) {
            throw new HttpBadRequestException("El nombre del Rol ya existe");
        }

        rol.setName(name);

        rol = accesoRol.persistir(rol)
                .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir Rol actualizado.") );

        return transformadorRol.entidadATransferible(rol);

    }

}
