package ciudadano.consciente.service;

import ciudadano.consciente.access.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.mapper.MapperVote;
import ciudadano.consciente.model.*;
import ciudadano.consciente.dto.*;
import ciudadano.consciente.mapper.MapperLevel;
import ciudadano.consciente.mapper.MapperUserRoleLevel;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceLevel {

    final String ENTITY_NAME = "Level";

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

    @Inject
    AccessVote accessVote;

    @Inject
    MapperVote mapperVote;

    @Inject
    AccessEntityType accessEntityType;

    public List<DTOLevel> getAll() {

        audit.debug("Getting all Levels.");
        return mapperLevel.entityToDto(accessLevel.getAll());

    }

    public DTOLevel get(Integer id) {

        audit.debug("Getting Level " + id + ".");
        Level level = accessLevel.get(id)
                .orElseThrow( () -> new HttpNoContentException("Level not found."));

        audit.debug("Mapping EntityType into DTO.");
        return  mapperLevel.entityToDto(level);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOLevel create(DTOCreateLevel dtoCreateLevel) {

        String name = dtoCreateLevel.getName();
        if(accessLevel.existName(name)) { // TODO Se podría agregar un alias que no sea único, que se usaría en la app.
            throw new HttpBadRequestException("The name already exists.");
        }

        Integer organizationDto = dtoCreateLevel.getOrganization();
        Organization organization = accessOrganization.get(organizationDto)
                        .orElseThrow( ()-> new HttpNotFoundException("Organization not found.") );

        audit.debug("Creating Level.");
        Level level = mapperLevel.dtoToEntity(name, organization);

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
        accessLevel.save(level)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new Level.") );

        audit.debug("Mapping EntityType into DTO.");
        return mapperLevel.entityToDto(level);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOLevel update(Integer id, DTOUpdateLevel dtoUpdateLevel) {

        audit.debug("Updating Level " + id + ".");
        String name = dtoUpdateLevel.getName();
        Integer organization = dtoUpdateLevel.getOrganization();
        Integer parent = dtoUpdateLevel.getParent();
        String description = dtoUpdateLevel.getDescription();

        Level level = accessLevel.get(id)
                .orElseThrow( () -> new HttpNotFoundException("Level not found."));

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

        if(utilityVerifyRequestField.isValidField(description)) {
            level.setDescription(description);
        }

        audit.debug("Saving Level " + level.getLevelId() + ".");
        accessLevel.save(level)
                .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated Level."));

        audit.debug("Mapping EntityType into DTO.");
        return mapperLevel.entityToDto(level);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOLevel delete(Integer id) {

        audit.debug("Deleting Level " + id + ".");
        Level level = accessLevel.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Level not found."));

        if(!accessLevel.remove(level.getLevelId())) {
            throw new HttpInternalServerException("Failed to delete Level");
        }

        audit.debug("Mapping EntityType into DTO.");
        return mapperLevel.entityToDto(level);

    }

    // ROLE HANDLING IN LEVEL

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOUserRoleLevel assignRoleToUserInLevel(Integer idLevel, Integer idUser, Integer idRole) {

        audit.debug("Verify if UserRoleLevel already exists.");
        if(accessUserRoleLevel.get(idLevel, idUser, idRole).isPresent()) {
            throw new HttpBadRequestException("UserRoleLevel already exists.");
        }

        User user = accessUser.get(idUser)
                .orElseThrow( ()-> new HttpNotFoundException("User not found."));

        Level level = accessLevel.get(idLevel)
                .orElseThrow( ()-> new HttpNotFoundException("Level not found."));

        Role role = accessRole.get(idRole)
                .orElseThrow( ()-> new HttpNotFoundException("Role not found."));

        audit.debug("Creating Role of User in Level.");
        UserRoleLevel userRoleLevel = new UserRoleLevel();
        userRoleLevel.setUser(user);
        userRoleLevel.setLevel(level);
        userRoleLevel.setRole(role);

        audit.debug("Saving UserRoleLevel " + userRoleLevel.getUrlId() + ".");
        accessUserRoleLevel.save(userRoleLevel)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new UserRoleLevel.") );

        audit.debug("Mapping EntityType into DTO.");
        return mapperUserRoleLevel.entityToDto(userRoleLevel);

    }

    @Deprecated
    public List<DTOUserRoleLevel> getUserRoleLevel(Integer id) {

        audit.debug("Getting Level " + id + ".");
        Level level = accessLevel.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Level not found.") );

        audit.debug("Retrieving all UserRole of Level "  + level.getLevelId() + ".");
        List<UserRoleLevel> userRoleLevel = accessUserRoleLevel.getByLevel(level);

        if(userRoleLevel.isEmpty()) {
            throw new HttpNoContentException("No Roles assigned to User in Level.");
        }

        audit.debug("Mapping EntityType into DTO.");
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
        audit.debug("Mapping EntityType into DTO.");
        return mapperUserRoleLevel.entityToDto(userRoleLevelList);

    }

    public DTOUserRoleLevel getUserRoleLevel(Integer idLevel, Integer idUser, Integer idRole) {

        audit.debug("Retrieving UserRoleLevel");
        UserRoleLevel userRoleLevel = accessUserRoleLevel.get(idLevel, idUser, idRole)
                .orElseThrow( ()-> new HttpNotFoundException("UserRoleLevel not found") );

        audit.debug("Mapping EntityType into DTO.");
        return mapperUserRoleLevel.entityToDto(userRoleLevel);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOUserRoleLevel deleteUserRoleLevel(Integer idLevel, Integer idUser, Integer idRole) {

        audit.debug("Deleting User(" + idUser + ")Role(" + idRole + ")Level(" + idLevel + ".");
        UserRoleLevel userRoleLevel = accessUserRoleLevel.get(idLevel, idUser, idRole)
                .orElseThrow( ()-> new HttpNotFoundException("UserRoleLevel not found."));

        if(!accessUserRoleLevel.remove(userRoleLevel.getUrlId())) {
            throw new HttpInternalServerException("Failed to remove UserRoleLevel.");
        }

        audit.debug("Mapping EntityType into DTO.");
        return mapperUserRoleLevel.entityToDto(userRoleLevel);

    }

    public List<DTOUserRoleLevel> getAllRolesInLevelByUser(Integer idLevel, Integer idUser) {

        audit.debug("Retrieving all Roles of User(" + idUser + ") in Level(" + idLevel + ").");
        List<UserRoleLevel> userRoleLevelList = accessUserRoleLevel.getByLevelAndUser(idLevel, idUser);

        if(userRoleLevelList.isEmpty()) {
            throw new HttpNotFoundException("User don't have Roles in Level.");
        }

        audit.debug("Mapping EntityType into DTO.");
        return mapperUserRoleLevel.entityToDto(userRoleLevelList);

    }

    public List<DTOUserRoleLevel> getAllUsersWithRoleByLevel(Integer idLevel) {

        Level level = accessLevel.get(idLevel)
                        .orElseThrow( ()-> new HttpNotFoundException("Level not found.") );

        audit.debug("Retrieving all Users with Roles in Level(" + idLevel + ").");
        List<UserRoleLevel> userRoleLevelList = accessUserRoleLevel.getByLevel(level);

        if(userRoleLevelList.isEmpty()) {
            throw new HttpNotFoundException("Level without Roles assigned.");
        }

        audit.debug("Mapping EntityType into DTO.");
        return mapperUserRoleLevel.entityToDto(userRoleLevelList);

    }

    public List<DTOUserRoleLevel> getAllUsersWithRoleInLevel(Integer idLevel, Integer idRole) {

        audit.debug("Retrieving all Users with Role(" + idRole + ") in Level(" + idLevel + ").");
        List<UserRoleLevel> userRoleLevelList = accessUserRoleLevel.getByLevelAndRole(idLevel, idRole);

        if(userRoleLevelList.isEmpty()) {
            throw new HttpNotFoundException("Role don't have Users in Level.");
        }

        audit.debug("Mapping EntityType into DTO.");
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

        audit.debug("Mapping EntityType into DTO.");
        return mapperUserRoleLevel.entityToDto(userRoleLevel);

    }

    // LEVEL HANDLING IN LEVEL

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOVote vote(Integer idLevel, Integer idUser) {

        audit.debug("Retrieving Entity Type");
        EntityType entityType = accessEntityType.getByName(ENTITY_NAME)
                .orElseThrow( ()-> new HttpNotFoundException("Entity Type not found.") );

        Level level = accessLevel.get(idLevel)
                .orElseThrow( ()-> new HttpNotFoundException("Level not found."));

        User user = accessUser.get(idUser)
                .orElseThrow( ()-> new HttpNotFoundException("User not found."));

        audit.debug("Verify if Vote already exists.");
        if(accessVote.getByKeys(user, level.getLevelId(), entityType).isPresent()) {
            throw new HttpBadRequestException("Vote already exists.");
        }

        audit.debug("Creating Vote for Level.");
        Vote vote = new Vote(user, level.getLevelId(), entityType);

        audit.debug("Saving Vote.");
        accessVote.save(vote)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new Vote.") );

        audit.debug("Mapping EntityType into DTO.");
        return mapperVote.entityToDto(vote);    
        
    }
    
}
