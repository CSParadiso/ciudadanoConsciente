package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOCreateEntity;
import ciudadano.consciente.dto.DTOEntity;
import ciudadano.consciente.model.Entity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapperEntity {


    List<DTOEntity> entityToDto(List<Entity> all);

    DTOEntity entityToDto(Entity entity);

    @Mapping(target = "entityId", ignore = true)
    Entity dtoToEntity(DTOCreateEntity dtoCreateEntity);

}
