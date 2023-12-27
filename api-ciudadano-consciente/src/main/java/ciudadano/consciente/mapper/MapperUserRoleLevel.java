package ciudadano.consciente.mapper;

import ciudadano.consciente.model.UserRoleLevel;
import ciudadano.consciente.dto.DTOUserRoleLevel;
import org.mapstruct.Mapper;

@Mapper
public interface MapperUserRoleLevel {

    DTOUserRoleLevel entidadATransferible(UserRoleLevel userRoleLevel);
}

