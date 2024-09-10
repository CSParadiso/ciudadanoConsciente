package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOCreateRole;
import ciudadano.consciente.dto.DTORole;
import ciudadano.consciente.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE) // Ignore advertencia de unmapped
public interface MapperRole {

    List<DTORole> entityToDto(List<Role> roles);

    DTORole entityToDto(Role role);

    //Role dtoToEntity(String name);

    Role dtoToEntity(DTOCreateRole dtoCreateRole);
}
