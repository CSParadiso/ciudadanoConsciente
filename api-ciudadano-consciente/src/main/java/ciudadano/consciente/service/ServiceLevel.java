package ciudadano.consciente.service;

import ciudadano.consciente.access.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.model.Level;
import ciudadano.consciente.model.Role;
import ciudadano.consciente.model.UserRoleLevel;
import ciudadano.consciente.dto.*;
import ciudadano.consciente.mapper.MapperLevel;
import ciudadano.consciente.mapper.MapperUserRoleLevel;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class ServiceLevel {

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    @Inject
    AccessLevel accessLevel;

    @Inject
    AccessOrganization accessOrganization;

    @Inject
    MapperLevel mapperLevel;

    @Inject
    AccessUser accessUser;

    @Inject
    AccessRole accessRole;

    @Inject
    MapperUserRoleLevel mapperUserRoleLevel;

    @Inject
    AccessUserRoleLevel accessUserRoleLevel;

    @Inject
    Logger audit;

    public List<DTOLevel> getAll() {

        audit.debug("Getting all Levels.");
        return mapperLevel.entityToDto(accessLevel.getAll());

    }

    public DTOLevel get(Integer id) {

        audit.debug("Getting Level " + id + ".");
        Level level = accessLevel.get(id)
                .orElseThrow( () -> new HttpNoContentException("Level not found."));

        audit.debug("Mapping Entity into DTO.");
        return  mapperLevel.entityToDto(level);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOLevel create(DTOCreateLevel dtoCreateLevel) {

        String name = dtoCreateLevel.getName();
        if(!utilityVerifyRequestField.isValidField(name)) {
            throw new HttpBadRequestException("The name is required.");
        }

        if(accessLevel.existName(name)) { // TODO Se podría agregar un alias que no sea único, que se usaría en la app.
            throw new HttpBadRequestException("The name already exists.");
        }

        audit.debug("Creating Level.");
        Level level = mapperLevel.dtoToEntity(name);

        Integer organization = dtoCreateLevel.getOrganization();
        if(utilityVerifyRequestField.isValidField(organization)) {
            level.setOrganization(accessOrganization.get(dtoCreateLevel.getOrganization())
                    .orElse(null));
        }

        Integer parent = dtoCreateLevel.getParent();
        if(utilityVerifyRequestField.isValidField(parent)) {
            level.setParent(accessLevel.get(parent)
                    .orElse(null));
        }

        String description = dtoCreateLevel.getDescription();
        if(utilityVerifyRequestField.isValidField(description)) {
            level.setDescription(dtoCreateLevel.getDescription());
        }

        audit.debug("Saving Level " + level.getLevelId() + ".");
        level = accessLevel.save(level)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new Level.") );

        audit.debug("Mapping Entity into DTO.");
        return mapperLevel.entityToDto(level);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOLevel update(Integer id, DTOUpdateLevel dtoUpdateLevel) {

        audit.debug("Updating Level " + id + ".");
        String name = dtoUpdateLevel.getName();
        Integer organization = dtoUpdateLevel.getOrganization();
        Integer parent = dtoUpdateLevel.getParent();
        String description = dtoUpdateLevel.getDescription();
        if(!utilityVerifyRequestField.isValidField(name) &&
                !utilityVerifyRequestField.isValidField(parent) &&
                !utilityVerifyRequestField.isValidField(organization) &&
                !utilityVerifyRequestField.isValidField(description)) {
            throw new HttpBadRequestException("No updates to make.");
        }

        Level level = accessLevel.get(id)
                .orElseThrow( () -> new HttpNoContentException("Level not found."));

        if(utilityVerifyRequestField.isValidField(name)) {
            if(accessLevel.existName(name)) {
                throw new HttpBadRequestException("The name already exists.");
            }
            level.setName(name);
        }

        if(utilityVerifyRequestField.isValidField(organization)) {
            level.setOrganization(accessOrganization.get(dtoUpdateLevel.getOrganization())
                    .orElseThrow( ()-> new HttpNotFoundException("Organization not found.")) );
        }

        if(utilityVerifyRequestField.isValidField(parent)) {
            level.setParent(accessLevel.get(parent)
                    .orElseThrow( ()-> new HttpNotFoundException("Parent Level not found.")) );
        }

        audit.debug("Saving Level " + level.getLevelId() + ".");
        accessLevel.save(level)
                .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated Level."));

        audit.debug("Mapping Entity into DTO.");
        return mapperLevel.entityToDto(level);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOLevel delete(Integer id) {

        audit.debug("Deleting Level " + id + ".");
        Level level = accessLevel.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Level not found."));

        if(!accessLevel.remove(level.getLevelId())) {
            throw new HttpInternalServerException("Failed to delete Level");
        };

        audit.debug("Mapping Entity into DTO.");
        return mapperLevel.entityToDto(level);

    }

    // ROLE HANDLING IN LEVEL

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOUserRoleLevel assignRoleToUserInLevel(Integer level, Integer user, Integer role) {

    audit.debug("Verify if UserRoleLevel already exists.");
    if(accessUserRoleLevel.exists(user, role, level)) {
        throw new HttpBadRequestException("UserRoleLevel already exists.");
    };

    audit.debug("Creating Role of User in Level.");
    UserRoleLevel userRoleLevel = new UserRoleLevel();

    userRoleLevel.setUser(accessUser.get(user)
            .orElseThrow( ()-> new HttpNotFoundException("User not found.")));

    userRoleLevel.setLevel(accessLevel.get(level)
            .orElseThrow( ()-> new HttpNotFoundException("Level not found.")));

    userRoleLevel.setRole(accessRole.get(role)
            .orElseThrow( ()-> new HttpNotFoundException("Role not found.")));

    audit.debug("Saving UserRoleLevel " + userRoleLevel.getUrlId() + ".");
    accessUserRoleLevel.save(userRoleLevel)
            .orElseThrow( ()-> new HttpBadRequestException("Already exists Role for User in Level.") );

    audit.debug("Mapping Entity into DTO.");
    return mapperUserRoleLevel.entityToDto(userRoleLevel);

    }

    public List<DTOUserRoleLevel> getUserRoleLevel(Integer id) {

        audit.debug("Getting Level " + id + ".");
        Level level = accessLevel.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Level not found.") );

        audit.debug("Retrieving all UserRole of Level "  + level.getLevelId() + ".");
        List<UserRoleLevel> userRoleLevel = accessUserRoleLevel.getByLevel(level);

        if(userRoleLevel.isEmpty()) {
            throw new HttpNoContentException("No Roles assigned to User in Level.");
        }

        audit.debug("Mapping Entity into DTO.");
        return mapperUserRoleLevel.entityToDto(userRoleLevel);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<DTOUserRoleLevel> deleteAllRolesOfUserInLevel(Integer idLevel, Integer idUser) {

        audit.debug("Deleting User(" + idUser + ")RoleLevel(" + idUser + ") " + idLevel + ".");
        List<UserRoleLevel> userRoleLevelList = accessUserRoleLevel.getByLevelAndUser(idLevel, idUser);
        if(userRoleLevelList.isEmpty()) {
            throw new HttpNotFoundException("User don't have Roles in Level.");
        } else {
            for(UserRoleLevel userRoleLevel : userRoleLevelList) {
                accessUserRoleLevel.remove(userRoleLevel.getUrlId());
            }
        }
        audit.debug("Mapping Entity into DTO.");
        return mapperUserRoleLevel.entityToDto(userRoleLevelList);

    }

    public DTOUserRoleLevel getUserRoleLevel(Integer idLevel, Integer idUser, Integer idRole) {

        audit.debug("Retrieving UserRoleLevel");
        UserRoleLevel userRoleLevel = accessUserRoleLevel.get(idLevel, idUser, idRole)
                .orElseThrow( ()-> new HttpNotFoundException("UserRoleLevel not found") );

        audit.debug("Mapping Entity into DTO.");
        return mapperUserRoleLevel.entityToDto(userRoleLevel);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOUserRoleLevel deleteUserRoleLevel(Integer idLevel, Integer idUser, Integer idRole) {

        audit.debug("Deleting User(" + idUser + ")Role(" + idRole + ")Level(" + idLevel + ".");
        UserRoleLevel userRoleLevel = accessUserRoleLevel.get(idLevel, idUser, idRole)
                .orElseThrow( ()-> new HttpNotFoundException("UserRoleLevel not found."));

        if(!accessUserRoleLevel.remove(userRoleLevel.getUrlId())) {
            throw new HttpInternalServerException("Failed to remove UserRoleLevel.");
        };

        audit.debug("Mapping Entity into DTO.");
        return mapperUserRoleLevel.entityToDto(userRoleLevel);

    }

    public List<DTOUserRoleLevel> getAllRolesOfUserInLevel(Integer idLevel, Integer idUser) {

        audit.debug("Retrieving all Roles of User(" + idUser + ") in Level(" + idLevel + ").");
        List<UserRoleLevel> userRoleLevelList = accessUserRoleLevel.getByLevelAndUser(idLevel, idUser);

        if(userRoleLevelList.isEmpty()) {
            throw new HttpNotFoundException("User don't have Roles in Level.");
        }

        audit.debug("Mapping Entity into DTO.");
        return mapperUserRoleLevel.entityToDto(userRoleLevelList);

    }

    public List<DTOUserRoleLevel> getAllUsersWithRole(Integer idLevel, Integer idRole) {

        audit.debug("Retrieving all Users with Role(" + idRole + ") in Level(" + idLevel + ").");
        List<UserRoleLevel> userRoleLevelList = accessUserRoleLevel.getByLevelAndRole(idLevel, idRole);

        if(userRoleLevelList.isEmpty()) {
            throw new HttpNotFoundException("Role don't have Users in Level.");
        }

        audit.debug("Mapping Entity into DTO.");
        return mapperUserRoleLevel.entityToDto(userRoleLevelList);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOUserRoleLevel updateRoleOfUserInLevel(Integer idLevel, Integer idUser, Integer idRole, Integer newRole) {

        audit.debug("Verifying if UserRoleLevel intended exists.");
        if(accessUserRoleLevel.get(idLevel, idUser, newRole).isPresent()) {
            throw new HttpBadRequestException("UserRoleLevel already exists.");
        }

        audit.debug("Verifying if new Role exists.");
        Role role = accessRole.get(newRole)
                        .orElseThrow( ()-> new HttpNotFoundException("Role not found."));

        audit.debug("Verifying if UserRoleLevel original exists.");
        UserRoleLevel userRoleLevel = accessUserRoleLevel.get(idLevel, idUser, idRole)
                        .orElseThrow( ()-> new HttpNotFoundException("UserRoleLevel not found.") );

        audit.debug("Upating Rol of User in Level: from " + idRole + " to " + newRole);
        userRoleLevel.setRole(role);

        audit.debug("Saving updated UserRoleLevel " + userRoleLevel.getUrlId() + ".");
        accessUserRoleLevel.save(userRoleLevel)
                .orElseThrow(()-> new HttpNotFoundException("Failed to update Role of User in Level."));

        audit.debug("Mapping Entity into DTO.");
        return mapperUserRoleLevel.entityToDto(userRoleLevel);

    }
}
