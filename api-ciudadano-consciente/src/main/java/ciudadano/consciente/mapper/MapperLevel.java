package ciudadano.consciente.mapper;

import ciudadano.consciente.model.Level;
import ciudadano.consciente.dto.DTOLevel;
import ciudadano.consciente.model.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE) // Ignore advertencia de unmapped
public interface MapperLevel {
    // target = "nombreEnTransferible" source = "nombreEnModelo"

    DTOLevel entityToDto(Level level);

    List<DTOLevel> entityToDto(List<Level> niveles);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "organization", source = "organization")
    Level dtoToEntity(String name, Organization organization);

}
