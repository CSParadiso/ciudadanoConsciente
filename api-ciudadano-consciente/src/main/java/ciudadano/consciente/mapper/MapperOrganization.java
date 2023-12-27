package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOOrganization;
import ciudadano.consciente.model.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE) // Ignore advertencia de unmapped
public interface MapperOrganization {
    // target = "nombreEnTransferible" source = "nombreEnModelo"

    DTOOrganization entidadATransferible(Organization organization);

    List<DTOOrganization> entidadATransferible(List<Organization> organizationList);

    Organization transferibleAEntidad(String email);
}
