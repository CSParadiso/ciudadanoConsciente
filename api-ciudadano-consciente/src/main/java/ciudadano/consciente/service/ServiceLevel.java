package ciudadano.consciente.service;

import ciudadano.consciente.access.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.model.Level;
import ciudadano.consciente.model.UserRoleLevel;
import ciudadano.consciente.dto.*;
import ciudadano.consciente.mapper.MapperLevel;
import ciudadano.consciente.mapper.MapperUserRoleLevel;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

import java.util.List;

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

        if(accessLevel.existName(name)) { // TODO Se debería poder tener niveles con el mismo nombre. Lo que lo diferenciaría sería el padre. O se podría agregar un alias que no sea único, que se usaría en la app.
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
    public void delete(Integer identificador) {

        if(!accessLevel.remove(identificador)) {
            throw new HttpNoContentException("Nivel no existe.");
        }

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOUserRoleLevel assignRole(DTOAssignRoleToUserLevel dtoAssignRoleToUserLevel) {

    audit.debug("Assigning Role to User in Level.");
    Integer user = dtoAssignRoleToUserLevel.getUser();
    Integer level = dtoAssignRoleToUserLevel.getLevel();
    Integer role = dtoAssignRoleToUserLevel.getRole();
    if(!utilityVerifyRequestField.isValidField(user) ||
            !utilityVerifyRequestField.isValidField(level) ||
            !utilityVerifyRequestField.isValidField(role)) {
        throw new HttpBadRequestException("All fields required.");
    }

    audit.debug("Creating Role of User in Level.");
    UserRoleLevel userRoleLevel = new UserRoleLevel();

    userRoleLevel.setUser(accessUser.get(user)
            .orElseThrow( ()-> new HttpNotFoundException("User not found.")));

    userRoleLevel.setLevel(accessLevel.get(level)
            .orElseThrow( ()-> new HttpNotFoundException("Level not found.")));

    userRoleLevel.setRole(accessRole.get(role)
            .orElseThrow( ()-> new HttpNotFoundException("Role not found.")));

    audit.debug("Saving UserRoleLevel " + userRoleLevel.getUrlId() + ".");
    try {
        accessUserRoleLevel.save(userRoleLevel)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist UserRoleLevel."));
    } catch (ConstraintViolationException e) {
        throw new HttpBadRequestException("Already exists Role for User in Level.");
    }

    audit.debug("Mapping Entity into DTO.");
    return mapperUserRoleLevel.entidadATransferible(userRoleLevel);

    }

}
