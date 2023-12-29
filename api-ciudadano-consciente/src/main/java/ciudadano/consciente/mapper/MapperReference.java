package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOCreateReference;
import ciudadano.consciente.model.Level;
import ciudadano.consciente.model.Reference;
import ciudadano.consciente.dto.DTOReference;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE) // Ignore advertencia de unmapped
public interface MapperReference {

    // target = "nombreEnTransferible" source = "nombreEnModelo"
    @Mapping(target = "level", source = "level.levelId")
    DTOReference entityToDto(Reference reference);

    @Mapping(target = "level", source = "level.levelId")
    List<DTOReference> entityToDto(List<Reference> references);

    @Mapping(target = "level.levelId", source = "level")
    Reference dtoToEntity(DTOCreateReference dtoCreateReference);

}
