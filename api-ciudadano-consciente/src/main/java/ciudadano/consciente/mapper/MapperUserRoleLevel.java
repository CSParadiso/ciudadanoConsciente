package ciudadano.consciente.mapper;

import ciudadano.consciente.model.UserRoleLevel;
import ciudadano.consciente.dto.DTOUserRoleLevel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapperUserRoleLevel {

    //@Mapping(source = "user.userId", target = "user")
    @Mapping(source = "role.roleId", target = "role")
    @Mapping(source = "level.levelId", target = "level")
    DTOUserRoleLevel entityToDto(UserRoleLevel userRoleLevel);

    //@Mapping(source = "user.userId", target = "user")
    @Mapping(source = "role.roleId", target = "role")
    @Mapping(source = "level.levelId", target = "level")
    List<DTOUserRoleLevel> entityToDto(List<UserRoleLevel> userRoleLevel);
}

