package ciudadano.consciente.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ciudadano.consciente.dto.DTOTagged;
import ciudadano.consciente.model.Tagged;

@Mapper
public interface MapperTagged {

  @Mapping(target = "tagId", source = "tagId.tagId")
  @Mapping(target = "entityTypeId", source = "entityTypeId.entityTypeId")
  DTOTagged entityToDto(Tagged tagged);

  List<DTOTagged> entityToDto(List<Tagged> all);

}
