package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOOrganization;
import ciudadano.consciente.dto.DTOUpdateOrganization;
import ciudadano.consciente.model.Organization;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE) // Ignore advertencia de unmapped

public interface MapperOrganization {
    // target = "nombreEnTransferible" source = "nombreEnModelo"

    @Mapping(target = "verified", source = "isVerified")
    DTOOrganization entityToDto(Organization organization);

    List<DTOOrganization> entityToDto(List<Organization> organizationList);

    @Mapping(target = "isVerified", source = "verified" )
    Organization dtoToEntity(String email, String name, Boolean verified);

}
