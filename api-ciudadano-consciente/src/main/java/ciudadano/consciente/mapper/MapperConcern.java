package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOConcern;
import ciudadano.consciente.dto.DTOCreateConcern;
import ciudadano.consciente.model.Concern;
import ciudadano.consciente.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapperConcern {

    List<DTOConcern> entityToDto(List<Concern> all);

    @Mapping(target = "user", source = "user.userId")
    DTOConcern entityToDto(Concern concern);

    @Mapping(target = "user", source = "user")
    Concern dtoToEntity(DTOCreateConcern dtoCreateConcern, User user);

}
