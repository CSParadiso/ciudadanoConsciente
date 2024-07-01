package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessLevel;
import ciudadano.consciente.access.AccessRole;
import ciudadano.consciente.access.AccessUser;
import ciudadano.consciente.access.AccessUserRoleLevel;
import ciudadano.consciente.dto.DTOUpdateRoleUserLevel;
import ciudadano.consciente.dto.DTOUserRoleLevel;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.mapper.MapperUserRoleLevel;
import ciudadano.consciente.model.Organization;
import ciudadano.consciente.model.UserRoleLevel;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceUserRoleLevel {

    @Inject
    Logger audit;

    @Inject
    MapperUserRoleLevel mapperUserRoleLevel;

    @Inject
    AccessUserRoleLevel accessUserRoleLevel;

    @Inject
    AccessUser accessUser;

    @Inject
    AccessRole accessRole;

    @Inject
    AccessLevel accessLevel;

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    public List<DTOUserRoleLevel> getAll() {

        audit.debug("Retrieving all Activities.");
        return mapperUserRoleLevel.entityToDto(accessUserRoleLevel.getAll());

    }
    @Transactional(Transactional.TxType.REQUIRED)
    public DTOUserRoleLevel updateRol(Integer id, DTOUpdateRoleUserLevel dtoUpdateRoleUserLevel) {

        audit.debug("Updating RoleUserLevel " + id + ".");
        UserRoleLevel userRoleLevel = accessUserRoleLevel.get(id)
                .orElseThrow( ()-> new HttpNoContentException("UserRoleLevel not found.") );

        Integer user = dtoUpdateRoleUserLevel.getUser();
        Integer level = dtoUpdateRoleUserLevel.getLevel();
        Integer role = dtoUpdateRoleUserLevel.getRole();
        if(!utilityVerifyRequestField.isValidField(user) &&
                !utilityVerifyRequestField.isValidField(level) &&
                !utilityVerifyRequestField.isValidField(role)) {
            throw new HttpBadRequestException("No updates to make.");
        }

        // Debido al metodo accesor.exists implementa un update al consultar NO SE POR QUE
        UserRoleLevel userRoleLevelAux = new UserRoleLevel();

        if(utilityVerifyRequestField.isValidField(user)) {
            userRoleLevelAux.setUser(accessUser.get(user)
                    .orElseThrow( ()-> new HttpNoContentException("User not found.")));
        } else {
            userRoleLevelAux.setUser(userRoleLevel.getUser());
        }

        if(utilityVerifyRequestField.isValidField(level)) {
            userRoleLevel.setLevel(accessLevel.get(level)
                    .orElseThrow( ()-> new HttpNoContentException("Level not found.")));
        } else {
            userRoleLevelAux.setRole(userRoleLevel.getRole());
        }

        if(utilityVerifyRequestField.isValidField(role)) {
            userRoleLevel.setRole(accessRole.get(role)
                    .orElseThrow( ()-> new HttpNoContentException("Role not found.")));
        } else {
            userRoleLevelAux.setLevel(userRoleLevel.getLevel());
        }

        audit.debug("Verify if UserRoleLevel already exists.");
        if(accessUserRoleLevel.exists(userRoleLevelAux.getUser().getUserId(),
                userRoleLevelAux.getRole().getRoleId(),
                userRoleLevelAux.getLevel().getLevelId())) {
            throw new HttpBadRequestException("UserRoleLevel already exists.");
        }

        audit.debug("Saving UserRoleLevel " + userRoleLevel.getUrlId() + ".");
        accessUserRoleLevel.save(userRoleLevel)
                .orElseThrow( ()-> new HttpInternalServerException( "Failed to persist updated UserRoleLevel." ));

        audit.debug("Mapping EntityType into DTO.");
        return mapperUserRoleLevel.entityToDto(userRoleLevel);

    }

    public DTOUserRoleLevel get(Integer id) {

        audit.debug("Retrieving UserRoleLevel " + id + ".");
        UserRoleLevel userRoleLevel = accessUserRoleLevel.get(id)
                .orElseThrow(() -> new HttpNoContentException("UserRoleLevel not found."));

        audit.debug("Mapping EntityType into DTO.");
        return mapperUserRoleLevel.entityToDto(userRoleLevel);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void delete(Integer id) {

        audit.debug("Deleting User " + id + ".");
        if (!accessUserRoleLevel.remove(id)) {
            throw new HttpNoContentException("User not found.");
        };

    }
}
