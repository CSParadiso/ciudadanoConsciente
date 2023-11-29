package ciudadano.consciente.servicio;

import ciudadano.consciente.acceso.AccesoNivel;
import ciudadano.consciente.acceso.AccesoOrganizacion;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.modelo.Nivel;
import ciudadano.consciente.modelo.Organizacion;
import ciudadano.consciente.transferible.TransferibleActualizarNivel;
import ciudadano.consciente.transferible.TransferibleCrearNivel;
import ciudadano.consciente.transferible.TransferibleNivel;
import ciudadano.consciente.transformador.TransformadorNivel;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServicioNivel {

    @Inject
    Logger auditor;

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
    public TransferibleNivel crear(TransferibleCrearNivel transferibleCrearNivel) {

        auditor.debug(transferibleCrearNivel.getName());

        if(accesoNivel.existeNombre(transferibleCrearNivel.getName())) {
            throw new HttpBadRequestException("El nombre de Nivel ya existe o no es válido.");
        }
        Nivel nivel = transformadorNivel.transferibleAEntidad(transferibleCrearNivel.getName());

        if(transferibleCrearNivel.getOrganization() != null) {
            nivel.setOrganization(accesoOrganizacion.obtener(transferibleCrearNivel.getOrganization())
                    .orElse(null));
        }

        if(transferibleCrearNivel.getParent() != null) {
            nivel.setParent(accesoNivel.obtener(transferibleCrearNivel.getParent())
                    .orElse(null));
        }

        nivel.setDescription(transferibleCrearNivel.getDescription());

        nivel = accesoNivel.persistir(nivel)
                .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir nuevo Nivel.") );

        return transformadorNivel.entidadATransferible(nivel);

    }

    /*@Transactional(Transactional.TxType.REQUIRED)
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

    }*/

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleNivel actualizar(TransferibleActualizarNivel transferibleActualizarNivel) {

        if(transferibleActualizarNivel.getLevelId() == null) {
            throw new HttpBadRequestException("El campo identificador de Nivel es requerido.");
        }

        Nivel nivel = accesoNivel.obtener(transferibleActualizarNivel.getLevelId())
                .orElseThrow( () -> new HttpNoContentException("El Nivel no existe."));

        String name = transferibleActualizarNivel.getName();
        if(name != null && !"null".equals(name) && !name.trim().isEmpty()) {
            auditor.debug("Nombre: " + name);
            if(!accesoNivel.existeNombre(name)) {
                nivel.setName(name);
            } else {
                throw new HttpBadRequestException("El nombre de Nivel ya existe o no es válido.");
            }
        }

        Integer organization = transferibleActualizarNivel.getOrganization();
        if(organization != null) {
            nivel.setOrganization(accesoOrganizacion.obtener(transferibleActualizarNivel.getOrganization())
                    .orElse(null));
        }

        Integer parent =transferibleActualizarNivel.getParent();
        if(parent != null) {
            if(nivel.getLevelId() == parent) {
                throw new HttpBadRequestException("Un nivel no puede ser padre de sí mismo.");
            }
            nivel.setParent(accesoNivel.obtener(parent)
                    .orElse(null));
        }

        String description = transferibleActualizarNivel.getDescription();
        if(description != null && !"null".equals(description) && !description.trim().isEmpty()) {
            nivel.setDescription(transferibleActualizarNivel.getDescription());
        }

        nivel = accesoNivel.persistir(nivel)
                .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir actualización de Nivel.") );

        return transformadorNivel.entidadATransferible(nivel);

    }



}
