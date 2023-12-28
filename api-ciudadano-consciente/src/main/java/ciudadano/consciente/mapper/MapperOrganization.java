package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOOrganization;
import ciudadano.consciente.model.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE) // Ignore advertencia de unmapped
public interface MapperOrganization {
    // target = "nombreEnTransferible" source = "nombreEnModelo"

    DTOOrganization entityToDto(Organization organization);

    List<DTOOrganization> entityToDto(List<Organization> organizationList);

    Organization dtoToEntity(String email, String name);
}
