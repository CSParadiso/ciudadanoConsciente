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
    Logger auditoria;

    public List<DTOLevel> obtenerTodos() {

        return mapperLevel.entidadATransferible(accessLevel.obtenerTodos());

    }

    public DTOLevel obtener(Integer identificador) {

        Level level = accessLevel.obtener(identificador)
                .orElseThrow( () -> new HttpNoContentException("El Nivel no existe."));

        return  mapperLevel.entidadATransferible(level);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOLevel create(DTOCreateLevel DTOCreateLevel) {

        String name = DTOCreateLevel.getName();
        if(!utilityVerifyRequestField.isCampoValido(name)) {
            throw new HttpBadRequestException("El nombre del nivel es requerido.");
        }

        if(accessLevel.existeNombre(name)) { //TODO Se debería poder tener niveles con el mismo nombre. Lo que lo diferenciaría sería el padre.
            throw new HttpBadRequestException("El nombre de Nivel ya existe");
        }

        Level level = mapperLevel.transferibleAEntidad(name);

        Integer organization = DTOCreateLevel.getOrganization();
        if(utilityVerifyRequestField.isCampoValido(organization)) {
            level.setOrganization(accessOrganization.obtener(DTOCreateLevel.getOrganization())
                    .orElse(null));
        }

        Integer parent = DTOCreateLevel.getParent();
        if(utilityVerifyRequestField.isCampoValido(parent)) {
            level.setParent(accessLevel.obtener(parent)
                    .orElse(null));
        }

        String description = DTOCreateLevel.getDescription();
        if(utilityVerifyRequestField.isCampoValido(description)) {
            level.setDescription(DTOCreateLevel.getDescription());
        }

        level = accessLevel.persistir(level)
                .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir actualización de Nivel.") );

        return mapperLevel.entidadATransferible(level);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOLevel update(Integer identificador, DTOUpdateLevel dtoUpdateLevel) {

        String name = dtoUpdateLevel.getName();
        Integer organization = dtoUpdateLevel.getOrganization();
        Integer parent = dtoUpdateLevel.getParent();
        String description = dtoUpdateLevel.getDescription();
        if(!utilityVerifyRequestField.isCampoValido(name) &&
                !utilityVerifyRequestField.isCampoValido(parent) &&
                !utilityVerifyRequestField.isCampoValido(organization) &&
                !utilityVerifyRequestField.isCampoValido(description)) {
            throw new HttpBadRequestException("Sin campos que update.");
        }

        Level level = accessLevel.obtener(identificador)
                .orElseThrow( () -> new HttpNoContentException("El Nivel no existe."));

        // FIXME Resolver Cómo se actualiza una entidad cuando solo algunos campos son modificados.

        if(accessLevel.existeNombre(name)) { //TODO Se debería poder tener niveles con el mismo nombre. Lo que lo diferenciaría sería el padre.
            throw new HttpBadRequestException("El nombre de Nivel ya existe");
        }

        mapperLevel.transferibleAEntidad(level, dtoUpdateLevel);

        if(utilityVerifyRequestField.isCampoValido(organization)) {
            level.setOrganization(accessOrganization.obtener(dtoUpdateLevel.getOrganization())
                    .orElseThrow( ()-> new HttpNotFoundException("La Organización no existe.")) );
        }

        if(utilityVerifyRequestField.isCampoValido(parent)) {
            level.setParent(accessLevel.obtener(parent)
                    .orElseThrow( ()-> new HttpNotFoundException("El Nivel Padre no existe.")) );
        }

        accessLevel.persistir(level)
                .orElseThrow(() -> new HttpInternalServerException("Problemas al persistir Nivel."));

        return mapperLevel.entidadATransferible(level);

        /*


        if(utilityVerifyRequestField.isCampoValido(name)) {
            if(!accessLevel.existeNombre(name)) {
                level.setName(name);
            } else {
                throw new HttpBadRequestException("El nombre de Nivel ya existe.");
            }
        }

        if (utilityVerifyRequestField.isCampoValido(organization)) {
            level.setOrganization(accessOrganization.obtener(organization)
                    .orElse(null));
        }

        if(utilityVerifyRequestField.isCampoValido(parent)) {
            if(level.getLevelId() == parent) {
                throw new HttpBadRequestException("Un nivel no puede ser padre de sí mismo.");
            }
            level.setParent(accessLevel.obtener(parent).orElse(null));
        }

        if(utilityVerifyRequestField.isCampoValido(description)) {
            level.setDescription(DTOUpdateLevel.getDescription());
        }

        level = accessLevel.persistir(level)
                .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir actualización de Nivel.") );

        return mapperLevel.entidadATransferible(level);*/

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void eliminar(Integer identificador) {

        if(!accessLevel.eliminar(identificador)) {
            throw new HttpNoContentException("Nivel no existe.");
        }

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOUserRoleLevel asignarRol(DTOAssignRolToUser DTOAssignRolToUser) {

    Integer user = DTOAssignRolToUser.getUser();
    Integer level = DTOAssignRolToUser.getRoleableEntity();
    Integer role = DTOAssignRolToUser.getRole();
    if(!utilityVerifyRequestField.isCampoValido(user) ||
            !utilityVerifyRequestField.isCampoValido(level) ||
            !utilityVerifyRequestField.isCampoValido(role)) {
        throw new HttpBadRequestException("Todos los campos son requeridos");
    }

    UserRoleLevel userRoleLevel = new UserRoleLevel();

    userRoleLevel.setUser(accessUser.obtener(user)
            .orElseThrow( ()-> new HttpNotFoundException("Usuario no existe.")));

    userRoleLevel.setLevel(accessLevel.obtener(level)
            .orElseThrow( ()-> new HttpNotFoundException("Nivel no existe.")));

    userRoleLevel.setRole(accessRole.obtener(role)
            .orElseThrow( ()-> new HttpNotFoundException("Rol no existe.")));

    userRoleLevel = accessUserRoleLevel.persistir(userRoleLevel)
            .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir asignación de Rol de Usuario en Nivel"));

    return mapperUserRoleLevel.entidadATransferible(userRoleLevel);

    }

}
