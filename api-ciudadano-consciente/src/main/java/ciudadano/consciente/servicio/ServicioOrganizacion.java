package ciudadano.consciente.servicio;

import ciudadano.consciente.acceso.AccesoOrganizacion;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.modelo.Organizacion;
import ciudadano.consciente.transferible.TransferibleOrganizacion;
import ciudadano.consciente.transformador.TransformadorOrganizacion;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServicioOrganizacion {

    @Inject
    Logger auditor;

    @Inject
    AccesoOrganizacion accesoOrganizacion;

    @Inject
    TransformadorOrganizacion transformadorOrganizacion;



    public List<TransferibleOrganizacion> obtenerTodos() {

        List<Organizacion> organizaciones = accesoOrganizacion.obtenerTodos();
        return transformadorOrganizacion.entidadATransferible(organizaciones);

    }

    public TransferibleOrganizacion obtener(Integer identificador) {

        Organizacion organizacion = accesoOrganizacion.obtener(identificador)
                .orElseThrow( () -> new HttpNoContentException( "La organización no existe" ));

        return transformadorOrganizacion.entidadATransferible(organizacion);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleOrganizacion crear(String name, String email, String description) {

        if(name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty())
        {
            throw new HttpBadRequestException("Se deben completar los campos requeridos para crear una Organización.");
        }

        if (!accesoOrganizacion.existeNombre(name)) {
            Organizacion organizacion = transformadorOrganizacion.transferibleAEntidad(name);

            if (!accesoOrganizacion.existeEmail(email)) {
                organizacion.setEmail(email);
            } else {
                throw new HttpBadRequestException("El email de la Organización ya existe o no es válido.");
            }

            if (description != null && !description.trim().isEmpty()) {
                organizacion.setDescription(description);
            }

            organizacion = accesoOrganizacion.persisir(organizacion)
                    .orElseThrow(()-> new HttpInternalServerException("Problemas al persistir nueva Organización."));

            return transformadorOrganizacion.entidadATransferible(organizacion);

        } else {
            throw new HttpBadRequestException("El nombre de la Organización ya existe o no es válidos.");
        }

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void eliminar(Integer identificador) {

        if(identificador == null) {
            throw new HttpBadRequestException("Identificador requerido.");
        }

        if(!accesoOrganizacion.eliminar(identificador)) {
            throw new HttpNoContentException("Organización a eliminar no existe");
        };

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public TransferibleOrganizacion actualizar(Integer identificador, String name, String email, String description) {

        if((name == null || name.trim().isEmpty()) &&
                (email == null || email.trim().isEmpty()) &&
                (description == null || description.trim().isEmpty())) {
            throw new HttpBadRequestException("Sin campos que actualizar.");
        }

        Organizacion organizacion = accesoOrganizacion.obtener(identificador)
                .orElseThrow( () -> new HttpNoContentException("La organización no existe.") );

        if (name != null && !name.trim().isEmpty()) {
            if(!accesoOrganizacion.existeNombre(name)) {
                organizacion.setName(name);
            } else {
                throw new HttpBadRequestException("El nombre de la Organización ya existe o no es válido.");
            }
        }

        if (email != null && !email.trim().isEmpty()) {
            if(!accesoOrganizacion.existeEmail(email)) {
                organizacion.setEmail(email);
            } else {
                throw new HttpBadRequestException("El email de la Organización ya existe o no es válido.");
            }
        }

        if (description != null && !description.trim().isEmpty()) {
            organizacion.setDescription(description);
        }

        organizacion = accesoOrganizacion.persisir(organizacion)
                .orElseThrow(()-> new HttpInternalServerException("Problemas al persistir actualización de Organización."));

        return transformadorOrganizacion.entidadATransferible(organizacion);

    }

}
