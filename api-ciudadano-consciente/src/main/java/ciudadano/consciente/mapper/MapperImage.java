package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOCreateImage;
import ciudadano.consciente.dto.DTOImage;
import ciudadano.consciente.model.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapperImage {

    @Mapping(target = "content.contentId", source = "content")
    Image dtoToEntity(DTOCreateImage dtoCreateImage);

    @Mapping(target = "contentId", source = "content.contentId")
    DTOImage entityToDto(Image image);

    List<DTOImage> dtoToEntity(List<Image> imageList);

}
