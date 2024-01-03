package ciudadano.consciente.mapper;

import ciudadano.consciente.access.AccessActivity;
import ciudadano.consciente.access.AccessActivityType;
import ciudadano.consciente.access.AccessLevel;
import ciudadano.consciente.dto.DTOActivity;
import ciudadano.consciente.dto.DTOUpdateActivity;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.model.Activity;
import ciudadano.consciente.model.ActivityType;
import ciudadano.consciente.model.Level;
import org.mapstruct.*;

import java.util.List;

@Mapper
public interface MapperActivity {
    // target = "nombreEnTransferible" source = "nombreEnModelo"

    @Mapping(source = "level", target = "level")
    @Mapping(source = "description", target = "description")
    Activity dtoToEntity(Level level, String description);

    @Mapping(source = "level.levelId", target = "level")
    @Mapping(source = "activityType.activityTypeId", target = "activityType")
    DTOActivity entityToDto(Activity activity);

    List<DTOActivity> entityToDto(List<Activity> all);

    /*@Mapping(target = "activityId", ignore = true)
    @Mapping(target = "level",
            qualifiedByName = "IntegerToLevel",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "description",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "activityType",
            qualifiedByName = "IntegerToActivityType",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateActivity(@MappingTarget Activity activity, DTOUpdateActivity dtoUpdateActivity);

    @Named("IntegerToLevel")
    default Level integerToLevel(Integer levelDTO) {
        return new AccessLevel().get(levelDTO)
                .orElseThrow( ()-> new HttpNotFoundException("Level not found."));
    }

    @Named("IntegerToActivityType")
    default ActivityType integerToActivityType(Integer activityTypeDTO) {
        return new AccessActivityType().get(activityTypeDTO)
                .orElseThrow( ()-> new HttpNotFoundException("Activity Type not found."));
    }*/

}
