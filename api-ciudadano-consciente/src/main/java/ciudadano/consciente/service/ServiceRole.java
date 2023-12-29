package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessRole;
import ciudadano.consciente.access.AccessUserRoleLevel;
import ciudadano.consciente.dto.DTORole;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.model.Role;
import ciudadano.consciente.dto.DTOUpdateRole;
import ciudadano.consciente.dto.DTOCreateRole;
import ciudadano.consciente.mapper.MapperRole;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceRole {

    @Inject
    Logger audit;

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    @Inject
    MapperRole mapperRole;

    @Inject
    AccessRole accessRole;

    @Inject
    AccessUserRoleLevel accessUserRoleLevel;

    public List<DTORole> getAll() {

        audit.debug("Getting all Roles.");
        return mapperRole.entityToDto(accessRole.getAll());

    }

    public DTORole get(Integer id) {

        audit.debug("Retrieving Role " + id + " .");
        Role role = accessRole.get(id)
                .orElseThrow( ()-> new HttpNoContentException("Role not found."));

        audit.debug("Mapping Entity into DTO.");
        return mapperRole.entityToDto(role);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTORole create(DTOCreateRole dtoCreateRole) {

        audit.debug("Creating new Role.");
        String name = dtoCreateRole.getName();
        if(!utilityVerifyRequestField.isValidField(name)) {
            throw new HttpBadRequestException("Name field required.");
        }

        if(accessRole.existsName(name)) {
            throw new HttpBadRequestException("The name of the Role already exists.");
        }

        audit.debug("Mapping DTO into Entity");
        Role role = mapperRole.dtoToEntity(name);

        audit.debug("Saving new Role " + role.getRoleId() + ".");
        accessRole.save(role)
                .orElseThrow(()-> new HttpInternalServerException("Failed to persist new Role."));

        audit.debug("Mapping Entity into DTO.");
        return mapperRole.entityToDto(role);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void delete(Integer id) {

        audit.debug("Deleting Role " + id + ".");
        if(!accessRole.remove(id)) {
            throw new HttpNoContentException("Role not found.");
        }

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTORole update(Integer id, DTOUpdateRole dtoUpdateRole) {

        audit.debug("Updating Role " + id + ".");
        Role role = accessRole.get(id)
                .orElseThrow( ()-> new HttpNoContentException("Role not found."));

        String name = dtoUpdateRole.getName();
        if(!utilityVerifyRequestField.isValidField(name)) {
            throw new HttpBadRequestException("No updates to make.");
        }

        if(accessRole.existsName(name)) {
            throw new HttpBadRequestException("The name of the Role already exists.");
        }
        role.setName(name);

        audit.debug("Saving Role " + role.getRoleId() + ".");
        accessRole.save(role)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist updated Role.") );

        audit.debug("Mapping Entity into DTO.");
        return mapperRole.entityToDto(role);

    }

}
