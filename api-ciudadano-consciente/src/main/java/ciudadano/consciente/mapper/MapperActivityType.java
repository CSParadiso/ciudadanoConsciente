package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOActivityType;
import ciudadano.consciente.model.ActivityType;
import ciudadano.consciente.model.User;
import org.mapstruct.*;

import java.util.List;

@Mapper
public interface MapperActivityType {
    @Mapping(target = "creator", source = "user")
    ActivityType dtoToEntity(String name, String description, String functionalTemplateUrl, User user);

    @Mapping(target = "creator", source = "creator.userId")
    DTOActivityType entityToDto(ActivityType activityType);

    List<DTOActivityType> entityToDto(List<ActivityType> activityTypes);

}
