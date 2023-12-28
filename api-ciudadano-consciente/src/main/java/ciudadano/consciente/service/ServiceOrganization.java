package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessOrganization;
import ciudadano.consciente.access.AccessRole;
import ciudadano.consciente.access.AccessUser;
import ciudadano.consciente.access.AccessUserRoleOrganization;
import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.mapper.MapperUserRoleOrganization;
import ciudadano.consciente.model.Organization;
import ciudadano.consciente.mapper.MapperOrganization;
import ciudadano.consciente.model.UserRolOrganization;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceOrganization {

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    @Inject
    AccessOrganization accessOrganization;

    @Inject
    AccessUser accessUser;

    @Inject
    AccessRole accessRole;

    @Inject
    AccessUserRoleOrganization accessUserRoleOrganization;

    @Inject
    MapperOrganization mapperOrganization;

    @Inject
    MapperUserRoleOrganization mapperUserRoleOrganization;

    @Inject
    Logger audit;

    public List<DTOOrganization> getAll() {

        audit.debug("Getting all Organizations.");
        List<Organization> organizations = accessOrganization.getAll();
        return mapperOrganization.entityToDto(organizations);

    }

    public DTOOrganization get(Integer id) {

        audit.debug("Getting Oganization " + id + ".");
        Organization organization = accessOrganization.get(id)
                .orElseThrow( () -> new HttpNoContentException( "Organization not found." ));

        audit.debug("Mapping Entity into DTO.");
        return mapperOrganization.entityToDto(organization);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOOrganization create(DTOCreateOrganization dtoCreateOrganization) {

        audit.debug("Creating Organization.");

        String email = dtoCreateOrganization.getEmail();
        String name = dtoCreateOrganization.getName();
        if(!utilityVerifyRequestField.isValidField(email) ||
                !utilityVerifyRequestField.isValidField(name)) {
            throw new HttpBadRequestException("Email and name required.");
        }

        if(accessOrganization.existEmail(email)) {
            throw new HttpBadRequestException("Email already exists.");
        }

        if(accessOrganization.existName(name)) {
            throw new HttpBadRequestException("Name already exists.");
        }

        audit.debug("Mapping DTO into Entity.");
        Organization organization = mapperOrganization.dtoToEntity(email, name);

        String description = dtoCreateOrganization.getDescription();
        if(utilityVerifyRequestField.isValidField(description)) {
            organization.setDescription(description);
        }

        audit.debug("Saving Organization " + organization.getOrganizationId() + ".");
        accessOrganization.save(organization)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new Organization.") );

        audit.debug("Mapping Entity into DTO.");
        return mapperOrganization.entityToDto(organization);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOOrganization update(Integer id, DTOUpdateOrganization dtoUpdateOrganization) {

        audit.debug("Updating Organization " + id + ".");
        Organization organization = accessOrganization.get(id)
                .orElseThrow( () -> new HttpNotFoundException("Organization not found") );

        String email = dtoUpdateOrganization.getEmail();
        String name = dtoUpdateOrganization.getName();
        String description = dtoUpdateOrganization.getDescription();
        if(!utilityVerifyRequestField.isValidField(email) &&
                !utilityVerifyRequestField.isValidField(name) &&
                !utilityVerifyRequestField.isValidField(description)) {
            throw new HttpBadRequestException("No updates to make.");
        }

        if (utilityVerifyRequestField.isValidField(name)) {
            if(accessOrganization.existName(name)) {
                throw new HttpBadRequestException("The name already exists.");
            }
            organization.setName(name);
        }

        if (utilityVerifyRequestField.isValidField(email)) {
            if(accessOrganization.existEmail(email)) {
                throw new HttpBadRequestException("The email already exists.");
            }
            organization.setEmail(email);
        }

        if (utilityVerifyRequestField.isValidField(description)) {
            organization.setDescription(description);
        }

        audit.debug("Saving Organization " + organization.getOrganizationId() + ".");
        organization = accessOrganization.save(organization)
                .orElseThrow(()-> new HttpInternalServerException("Failed to persist updated Organization."));

        audit.debug("Mapping Entity into DTO.");
        return mapperOrganization.entityToDto(organization);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void delete(Integer id) {

        audit.debug("Deleting Organization " + id);
        if(!accessOrganization.remove(id)) {
            throw new HttpInternalServerException("Failed to delete Organization.");
        }

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOUserRoleOrganization assignRole(DTOAssingRoleToUserOrganization dtoAssingRoleToUserOrganization) {

        audit.debug("Assigning Role to User in Organization.");
        Integer user = dtoAssingRoleToUserOrganization.getUser();
        Integer organization = dtoAssingRoleToUserOrganization.getOrganization();
        Integer role = dtoAssingRoleToUserOrganization.getRole();
        if(!utilityVerifyRequestField.isValidField(user) ||
                !utilityVerifyRequestField.isValidField(organization) ||
                !utilityVerifyRequestField.isValidField(role)) {
            throw new HttpBadRequestException("All fields required.");
        }

        audit.debug("Creating Role of User in Organization.");
        UserRolOrganization userRolOrganization = new UserRolOrganization();

        userRolOrganization.setUser(accessUser.get(user)
                .orElseThrow( ()-> new HttpNotFoundException("User not found.")));

        userRolOrganization.setOrganization(accessOrganization.get(organization)
                .orElseThrow( ()-> new HttpNotFoundException("Organization not found.")));

        userRolOrganization.setRole(accessRole.get(role)
                .orElseThrow( ()-> new HttpNotFoundException("Role not found.")));

        audit.debug("Saving UserRoleOrganization " + userRolOrganization.getUroId() + ".");
        try {
            accessUserRoleOrganization.save(userRolOrganization)
                    .orElseThrow(() -> new HttpInternalServerException("Failed to persist UserRoleLevel."));
        } catch (ConstraintViolationException e) {
            throw new HttpBadRequestException("Already exists Role for User in Organization.");
        }

        audit.debug("Mapping Entity into DTO.");
        return mapperUserRoleOrganization.entityToDto(userRolOrganization);

    }
}
