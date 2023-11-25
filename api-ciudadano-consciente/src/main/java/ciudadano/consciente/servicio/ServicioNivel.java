package ciudadano.consciente.servicio;

import ciudadano.consciente.acceso.AccesoNivel;
import ciudadano.consciente.acceso.AccesoOrganizacion;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.modelo.Nivel;
import ciudadano.consciente.modelo.Organizacion;
import ciudadano.consciente.transferible.TransferibleNivel;
import ciudadano.consciente.transformador.TransformadorNivel;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class ServicioNivel {

    @Inject
    AccesoNivel accesoNivel;

    @Inject
    AccesoOrganizacion accesoOrganizacion;

    @Inject
    TransformadorNivel transformadorNivel;

    public List<TransferibleNivel> obtenerTodos() {

        return transformadorNivel.entidadATransferible(accesoNivel.obtenerTodos());

    }

    public TransferibleNivel obtener(Integer identificador) {

        Nivel nivel = accesoNivel.obtener(identificador)
                .orElseThrow( () -> new HttpNoContentException("El Nivel no existe."));

        return  transformadorNivel.entidadATransferible(nivel);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void eliminar(Integer identificador) {

        if(identificador == null) {
            throw new HttpBadRequestException("Identificador requerido.");
        }

        if(!accesoNivel.eliminar(identificador)) {
            throw new HttpNoContentException("Organización a eliminar no existe.");
        }

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleNivel crear(String name, String description, Integer organization, Integer parent) {

        if(name != null && !name.trim().isEmpty() && !accesoNivel.existeNombre(name)) {
            Nivel nivel = transformadorNivel.transferibleAEntidad(name);

            if (description != null && !description.trim().isEmpty()) {
                nivel.setDescription(description);
            }

            if(organization != null) {
                Organizacion organizacion = accesoOrganizacion.obtener(organization).orElse(null);
                nivel.setOrganization(organizacion);
            }

            if(parent != null) {
                Nivel padre = accesoNivel.obtener(parent).orElse(null);
                nivel.setParent(padre);
            }

            nivel = accesoNivel.persistir(nivel)
                    .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir nuevo Nivel.") );

            return transformadorNivel.entidadATransferible(nivel);

        } else {
            throw new HttpBadRequestException("El nombre de Nivel ya existe o no es válido.");
        }

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleNivel actualizar(Integer identificador, String name, String description, Integer organization, Integer parent) {

        if((name == null || name.trim().isEmpty()) &&
                (description == null || description.trim().isEmpty()) &&
                (organization == null) &&
                (parent == null))
        {
            throw new HttpBadRequestException("Sin campos que actualizar.");
        }

        Nivel nivel = accesoNivel.obtener(identificador)
                .orElseThrow( () -> new HttpNoContentException("El Nivel no existe."));

        if(name != null && !name.trim().isEmpty()) {
            if(!accesoNivel.existeNombre(name)) {
                nivel.setName(name);
            } else {
                throw new HttpBadRequestException("El nombre de Nivel ya existe o no es válido.");
            }
        }

        if(description != null && !description.trim().isEmpty()) {
            nivel.setDescription(description);
        }

        if(organization != null) {
            Organizacion organizacion = accesoOrganizacion.obtener(organization).orElse(null);
            nivel.setOrganization(organizacion);
        }

        if(parent != null) {
            Nivel padre = accesoNivel.obtener(parent).orElse(null);
            nivel.setParent(padre);
        }

        nivel = accesoNivel.persistir(nivel)
                .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir actualización de Nivel.") );

        return transformadorNivel.entidadATransferible(nivel);

    }




}
