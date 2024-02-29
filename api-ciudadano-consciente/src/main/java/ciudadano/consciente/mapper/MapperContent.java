package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOContent;
import ciudadano.consciente.model.Content;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapperContent {

    DTOContent entityToDto(Content content);

    List<DTOContent> entityToDto(List<Content> all);

}
