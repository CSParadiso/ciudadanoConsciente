package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOCreateTag;
import ciudadano.consciente.dto.DTOTag;
import ciudadano.consciente.dto.DTOUpdateTag;
import ciudadano.consciente.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface MapperTag {

    List<DTOTag> entityToDto(List<Tag> all);

    DTOTag entityToDto(Tag tag);

    Tag dtoToEntity(DTOCreateTag dtoCreateTag);

    @Mapping(target = "tagId", ignore = true)
    void update(@MappingTarget Tag tag, DTOUpdateTag dtoUpdateTag);

}
