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
import ciudadano.consciente.model.*;
import ciudadano.consciente.mapper.MapperOrganization;
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

        if(accessOrganization.existEmail(email)) {
            throw new HttpBadRequestException("Email already exists.");
        }

        if(accessOrganization.existName(name)) {
            throw new HttpBadRequestException("Name already exists.");
        }

        audit.debug("Mapping DTO into Entity.");
        Organization organization = mapperOrganization.dtoToEntity(email, name);

        // TODO Quizás se pueda asignar directamente el DTO en las creaciones
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
    public DTOOrganization delete(Integer id) {

        audit.debug("Deleting Organization " + id);
        Organization organization = accessOrganization.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Organization not found") );

        if(!accessOrganization.remove(organization.getOrganizationId())) {
            throw new HttpInternalServerException("Failed to delete Organization.");
        }

        audit.debug("Mapping Entity into DTO.");
        return mapperOrganization.entityToDto(organization) ;

    }

    @Deprecated
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

    public List<DTOUserRoleOrganization> getUserRoleOrganization(Integer id) {

        audit.debug("Getting Organization " + id + ".");
        Organization organization = accessOrganization.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Organization not found.") );

        audit.debug("Retrieving all UserRole of Organization "  + organization.getOrganizationId() + ".");
        List<UserRolOrganization> userRolOrganizations = accessUserRoleOrganization.getByOrganization(organization);

        if(userRolOrganizations.isEmpty()) {
            throw new HttpNoContentException("No Roles assigned to User in Organization.");
        }

        audit.debug("Mapping Entity into DTO.");
        return mapperUserRoleOrganization.entityToDto(userRolOrganizations);

    }

    public List<DTOUserRoleOrganization> getAllUsersWithRoleByOrganization(Integer idOrganization) {

        Organization organization = accessOrganization.get(idOrganization)
                .orElseThrow( ()-> new HttpNotFoundException("Organization not found.") );

        audit.debug("Retrieving all Users with Roles in Organization(" + idOrganization + ").");
        List<UserRolOrganization> userRoleOrganizationList = accessUserRoleOrganization.getByOrganization(organization);

        if(userRoleOrganizationList.isEmpty()) {
            throw new HttpNotFoundException("Organization without Roles assigned.");
        }

        audit.debug("Mapping Entity into DTO.");
        return mapperUserRoleOrganization.entityToDto(userRoleOrganizationList);
        
    }

    public List<DTOUserRoleOrganization> getAllRolesInOrganizationByUser(Integer idOrganization, Integer idUser) {

        audit.debug("Retrieving all Roles of User(" + idUser + ") in Organization(" + idOrganization + ").");
        List<UserRolOrganization> userRoleOrganizationList = accessUserRoleOrganization.getByOrganizationAndUser(idOrganization, idUser);

        if(userRoleOrganizationList.isEmpty()) {
            throw new HttpNotFoundException("User don't have Roles in Organization.");
        }

        audit.debug("Mapping Entity into DTO.");
        return mapperUserRoleOrganization.entityToDto(userRoleOrganizationList);
        
    }

    public List<DTOUserRoleOrganization> getAllUsersWithRoleInOrganization(Integer idOrganization, Integer idRole) {

        audit.debug("Retrieving all Users with Role(" + idRole + ") in Organization(" + idOrganization + ").");
        List<UserRolOrganization> userRoleOrganizationList = accessUserRoleOrganization.getByOrganizationAndRole(idOrganization, idRole);

        if(userRoleOrganizationList.isEmpty()) {
            throw new HttpNotFoundException("Role don't have Users in Organization.");
        }

        audit.debug("Mapping Entity into DTO.");
        return mapperUserRoleOrganization.entityToDto(userRoleOrganizationList);
        
    }

    public DTOUserRoleOrganization getUserRoleOrganization(Integer idOrganization, Integer idUser, Integer idRole) {

        audit.debug("Retrieving UserRoleOrganization");
        UserRolOrganization userRoleorganization = accessUserRoleOrganization.get(idOrganization, idUser, idRole)
                .orElseThrow( ()-> new HttpNotFoundException("UserRoleOrganization not found") );

        audit.debug("Mapping Entity into DTO.");
        return mapperUserRoleOrganization.entityToDto(userRoleorganization);
        
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOUserRoleOrganization assignRoleToUserInOrganization(Integer idOrganization, Integer idUser, Integer idRole) {

        audit.debug("Verify if UserRoleOrganization already exists.");
        if(accessUserRoleOrganization.get(idOrganization, idUser, idRole).isPresent()) {
            throw new HttpBadRequestException("UserRoleOrganization already exists.");
        }

        User user = accessUser.get(idUser)
                .orElseThrow( ()-> new HttpNotFoundException("User not found."));

        Organization organization = accessOrganization.get(idOrganization)
                .orElseThrow( ()-> new HttpNotFoundException("Organization not found."));

        Role role = accessRole.get(idRole)
                .orElseThrow( ()-> new HttpNotFoundException("Role not found."));

        audit.debug("Creating Role of User in Organization.");
        UserRolOrganization userRoleOrganization = new UserRolOrganization();
        userRoleOrganization.setUser(user);
        userRoleOrganization.setOrganization(organization);
        userRoleOrganization.setRole(role);

        audit.debug("Saving UserRoleOrganization " + userRoleOrganization.getUroId() + ".");
        accessUserRoleOrganization.save(userRoleOrganization)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new UserRoleOrganization.") );

        audit.debug("Mapping Entity into DTO.");
        return mapperUserRoleOrganization.entityToDto(userRoleOrganization);
        
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOUserRoleOrganization updateRoleOfUserInOrganization(Integer idOrganization, Integer idUser, Integer idRole, Integer newRole) {

        audit.debug("Verifying if UserRoleOrganization intended exists.");
        if(accessUserRoleOrganization.get(idOrganization, idUser, newRole).isPresent()) {
            throw new HttpBadRequestException("UserRoleOrganization already exists.");
        }

        audit.debug("Verifying if new Role exists.");
        Role role = accessRole.get(newRole)
                .orElseThrow( ()-> new HttpNotFoundException("Role not found."));

        audit.debug("Verifying if UserRoleOrganization original exists.");
        UserRolOrganization userRoleOrganization = accessUserRoleOrganization.get(idOrganization, idUser, idRole)
                .orElseThrow( ()-> new HttpNotFoundException("UserRoleOrganization not found.") );

        audit.debug("Upating Rol of User in Organization: from " + idRole + " to " + newRole);
        userRoleOrganization.setRole(role);

        audit.debug("Saving updated UserRoleOrganization " + userRoleOrganization.getUroId() + ".");
        accessUserRoleOrganization.save(userRoleOrganization)
                .orElseThrow(()-> new HttpNotFoundException("Failed to update Role of User in Organization."));

        audit.debug("Mapping Entity into DTO.");
        return mapperUserRoleOrganization.entityToDto(userRoleOrganization);
        
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<DTOUserRoleOrganization> deleteAllRolesOfUserInOrganization(Integer idOrganization, Integer idUser) {

        audit.debug("Deleting User(" + idUser + ")RoleOrganization(" + idUser + ") " + idOrganization + ".");
        List<UserRolOrganization> userRoleOrganizationList = accessUserRoleOrganization.getByOrganizationAndUser(idOrganization, idUser);
        if(userRoleOrganizationList.isEmpty()) {
            throw new HttpNotFoundException("User don't have Roles in Organization.");
        } else {
            for(UserRolOrganization userRoleOrganization : userRoleOrganizationList) {
                accessUserRoleOrganization.remove(userRoleOrganization.getUroId());
            }
        }
        audit.debug("Mapping Entity into DTO.");
        return mapperUserRoleOrganization.entityToDto(userRoleOrganizationList);
        
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOUserRoleOrganization deleteUserRoleOrganization(Integer idOrganization, Integer idUser, Integer idRole) {

        audit.debug("Deleting User(" + idUser + ")Role(" + idRole + ")Organization(" + idOrganization + ".");
        UserRolOrganization userRoleOrganization = accessUserRoleOrganization.get(idOrganization, idUser, idRole)
                .orElseThrow( ()-> new HttpNotFoundException("UserRoleOrganization not found."));

        if(!accessUserRoleOrganization.remove(userRoleOrganization.getUroId())) {
            throw new HttpInternalServerException("Failed to remove UserRoleOrganization.");
        }

        audit.debug("Mapping Entity into DTO.");
        return mapperUserRoleOrganization.entityToDto(userRoleOrganization);
        
    }
}
