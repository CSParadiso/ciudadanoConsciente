package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessOrganization;
import ciudadano.consciente.dto.DTOCreateOrganization;
import ciudadano.consciente.dto.DTOOrganization;
import ciudadano.consciente.dto.DTOUpdateOrganization;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.model.Organization;
import ciudadano.consciente.mapper.MapperOrganization;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class ServiceOrganization {

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    @Inject
    AccessOrganization accessOrganization;

    @Inject
    MapperOrganization mapperOrganization;



    public List<DTOOrganization> obtenerTodos() {

        List<Organization> organizaciones = accessOrganization.obtenerTodos();
        return mapperOrganization.entidadATransferible(organizaciones);

    }

    public DTOOrganization obtener(Integer identificador) {

        Organization organization = accessOrganization.obtener(identificador)
                .orElseThrow( () -> new HttpNoContentException( "La organización no existe" ));

        return mapperOrganization.entidadATransferible(organization);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOOrganization create(DTOCreateOrganization DTOCreateOrganization) {

        String email = DTOCreateOrganization.getEmail();
        if(!utilityVerifyRequestField.isCampoValido(email)) {
            throw new HttpBadRequestException("El email es campo requerido.");
        }

        if(accessOrganization.existeEmail(email)) {
            throw new HttpBadRequestException("El email ya existe");
        }

        Organization organization = mapperOrganization.transferibleAEntidad(email);

        String name = DTOCreateOrganization.getName();
        if(utilityVerifyRequestField.isCampoValido(name)) {
            organization.setName(name);
        } else {
            throw new HttpBadRequestException("El nombre de la organización es requerido");
        }

        String description = DTOCreateOrganization.getDescription();
        if(utilityVerifyRequestField.isCampoValido(description)) {
            organization.setDescription(description);
        }

        organization = accessOrganization.persistir(organization)
                .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir nueva Organización.") );

        return mapperOrganization.entidadATransferible(organization);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOOrganization update(DTOUpdateOrganization DTOUpdateOrganization) {

        Integer organizationId = DTOUpdateOrganization.getOrganizationId();
        if(!utilityVerifyRequestField.isCampoValido(organizationId)) {
            throw new HttpBadRequestException("El identificador de la Organización es requerido.");
        }

        Organization organization = accessOrganization.obtener(organizationId)
                .orElseThrow( () -> new HttpNoContentException("La organización no existe.") );

        String email = DTOUpdateOrganization.getEmail();
        String name = DTOUpdateOrganization.getName();
        String description = DTOUpdateOrganization.getDescription();
        if(!utilityVerifyRequestField.isCampoValido(email) &&
                !utilityVerifyRequestField.isCampoValido(name) &&
                !utilityVerifyRequestField.isCampoValido(description)) {
            throw new HttpBadRequestException("Sin campos que update.");
        }

        if (utilityVerifyRequestField.isCampoValido(name)) {
            if(!accessOrganization.existeNombre(name)) {
                organization.setName(name);
            } else {
                throw new HttpBadRequestException("El nombre de la Organización ya existe.");
            }
        }

        if (utilityVerifyRequestField.isCampoValido(email)) {
            if(!accessOrganization.existeEmail(email)) {
                organization.setEmail(email);
            } else {
                throw new HttpBadRequestException("El email ya existe.");
            }
        }

        if (utilityVerifyRequestField.isCampoValido(description)) {
            organization.setDescription(description);
        }

        organization = accessOrganization.persistir(organization)
                .orElseThrow(()-> new HttpInternalServerException("Problemas al persistir actualización de Organización."));

        return mapperOrganization.entidadATransferible(organization);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void eliminar(Integer identificador) {

        if(!accessOrganization.eliminar(identificador)) {
            throw new HttpNoContentException("Organización a eliminar no existe");
        }

    }

}
