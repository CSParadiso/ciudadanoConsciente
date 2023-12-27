package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOUser;
import ciudadano.consciente.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE) // Ignore advertencia de unmapped
public interface MapperUser {

    // target = "nombreEnTransferible" source = "nombreEnModelo"
    DTOUser entidadATransferible(User user);

    List<DTOUser> entidadATransferible(List<User> userList);

    User transferibleAEntidad(String email, String username);
}
