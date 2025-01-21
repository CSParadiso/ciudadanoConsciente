package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOActivity;
import ciudadano.consciente.dto.DTOCreateActivity;
import ciudadano.consciente.dto.DTOFileNameRequired;
import ciudadano.consciente.model.Activity;
import ciudadano.consciente.model.FileNameRequired;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapperFileNameRequired {

    List<DTOFileNameRequired> entityToDto(List<FileNameRequired> all);

}
