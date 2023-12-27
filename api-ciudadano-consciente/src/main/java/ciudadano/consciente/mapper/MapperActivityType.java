package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOActivityType;
import ciudadano.consciente.model.ActivityType;
import org.mapstruct.*;

import java.util.List;

@Mapper
public interface MapperActivityType {
    ActivityType dtoToEntity(String name, String description, String functionalTemplateUrl);

    DTOActivityType entityToDto(ActivityType activityType);

    List<DTOActivityType> entityToDto(List<ActivityType> activityTypes);

}
