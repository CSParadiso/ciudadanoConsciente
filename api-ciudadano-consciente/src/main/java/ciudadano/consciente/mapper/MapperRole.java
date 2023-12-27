package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTORole;
import ciudadano.consciente.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE) // Ignore advertencia de unmapped
public interface MapperRole {

    List<DTORole> entidadATransferible(List<Role> roles);

    DTORole entidadATransferible(Role role);

    Role transferibleAEntidad(String name);

}
