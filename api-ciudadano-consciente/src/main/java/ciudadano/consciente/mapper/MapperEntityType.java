package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOCreateEntityType;
import ciudadano.consciente.dto.DTOEntityType;
import ciudadano.consciente.model.EntityType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapperEntityType {

    List<DTOEntityType> entityToDto(List<EntityType> all);

    DTOEntityType entityToDto(EntityType entityType);

    @Mapping(target = "entityTypeId", ignore = true)
    EntityType dtoToEntity(DTOCreateEntityType dtoCreateEntityType);

}
