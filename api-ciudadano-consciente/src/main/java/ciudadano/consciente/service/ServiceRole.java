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

import java.util.List;

@RequestScoped
public class ServiceRole {

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    @Inject
    MapperRole mapperRole;

    @Inject
    AccessRole accessRole;

    @Inject
    AccessUserRoleLevel accessUserRoleLevel;

    public List<DTORole> obtenerTodos() {

        return mapperRole.entidadATransferible(accessRole.obtenerTodos());

    }

    public DTORole obtener(Integer identificador) {

        Role role = accessRole.obtener(identificador)
                .orElseThrow( ()-> new HttpNoContentException("El Rol no existe."));

        return mapperRole.entidadATransferible(role);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTORole create(DTOCreateRole DTOCreateRole) {

        String name = DTOCreateRole.getName();
        if(!utilityVerifyRequestField.isCampoValido(name)) {
            throw new HttpBadRequestException("Campo requerido sin completar.");
        }

        if(accessRole.existeNombre(name)) {
            throw new HttpBadRequestException("El rol ya existe");
        }

        Role role = mapperRole.transferibleAEntidad(name);

        role = accessRole.persistir(role)
                .orElseThrow(()-> new HttpInternalServerException("Problemas al persistir nuevo Rol."));

        return mapperRole.entidadATransferible(role);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void eliminar(Integer identificador) {

        if(!accessRole.eliminar(identificador)) {
            throw new HttpNoContentException("Rol a eliminar no existe.");
        }

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTORole update(DTOUpdateRole DTOUpdateRol) {

        Integer roleId = DTOUpdateRol.getId();
        if(!utilityVerifyRequestField.isCampoValido(roleId)) {
            throw new HttpBadRequestException("El campo identificador es requerido.");
        }

        Role role = accessRole.obtener(roleId)
                .orElseThrow( ()-> new HttpNoContentException("El Rol no existe."));

        String name = DTOUpdateRol.getName();
        if(!utilityVerifyRequestField.isCampoValido(name)) {
            throw new HttpBadRequestException("Sin campos que update");
        }

        if(accessRole.existeNombre(name)) {
            throw new HttpBadRequestException("El nombre del Rol ya existe");
        }

        role.setName(name);

        role = accessRole.persistir(role)
                .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir Rol actualizado.") );

        return mapperRole.entidadATransferible(role);

    }

}
