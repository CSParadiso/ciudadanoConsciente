package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOUserRoleOrganization;
import ciudadano.consciente.model.UserRolOrganization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapperUserRoleOrganization {

    @Mapping(source = "user.userId", target = "user")
    @Mapping(source = "role.roleId", target = "role")
    @Mapping(source = "organization.organizationId", target = "organization")
    DTOUserRoleOrganization entityToDto(UserRolOrganization userRolOrganization);

    @Mapping(source = "user.userId", target = "user")
    @Mapping(source = "role.roleId", target = "role")
    @Mapping(source = "organization.organizationId", target = "organization")
    List<DTOUserRoleOrganization> entityToDto(List<UserRolOrganization> userRolOrganizations);

}
