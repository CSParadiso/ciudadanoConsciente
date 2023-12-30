package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOCreateUser;
import ciudadano.consciente.dto.DTOUser;
import ciudadano.consciente.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE) // Ignore advertencia de unmapped
public interface MapperUser {

    // target = "nombreEnTransferible" source = "nombreEnModelo"
    DTOUser entityToDto(User user);

    List<DTOUser> entityToDto(List<User> userList);

    User dtoToEntity(DTOCreateUser dtoCreateUser);

}
