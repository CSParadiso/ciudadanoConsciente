package ciudadano.consciente.servicio;

import ciudadano.consciente.acceso.AccesoRol;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.modelo.Rol;
import ciudadano.consciente.transferible.TransferibleRol;
import ciudadano.consciente.transformador.TransformadorRol;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class ServicioRol {

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
    public TransferibleRol crear(String name) {

        if(name != null && !name.trim().isEmpty()) {

            if(accesoRol.existeNombre(name)) {

                Rol rol = transformadorRol.transferibleAEntidad(name);
                rol = accesoRol.persistir(rol)
                        .orElseThrow( ()-> new HttpInternalServerException("Problemas al crear Rol."));
                return transformadorRol.entidadATransferible(rol);

            } else {
                throw new HttpBadRequestException("El Rol ya existe");
            }

        } else {
            throw new HttpBadRequestException("Campo requerido sin completar.");
        }

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
    public TransferibleRol actualizar(Integer id, String name) {

        if(id == null || name == null || name.trim().isEmpty()) {
            throw new HttpBadRequestException("Los campos son requeridos son obligatorios.");
        }

        Rol rol = accesoRol.obtener(id)
                .orElseThrow( ()-> new HttpNoContentException("El Rol no existe."));

        if(accesoRol.existeNombre(name)) {

            rol.setName(name);
            rol = accesoRol.persistir(rol)
                    .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir Rol actualizado.") );
            return transformadorRol.entidadATransferible(rol);

        } else {
            throw new HttpBadRequestException("El nombre del Rol ya existe");
        }

    }

}
