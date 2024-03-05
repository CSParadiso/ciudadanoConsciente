package ciudadano.consciente.mapper;

import ciudadano.consciente.access.AccessActivity;
import ciudadano.consciente.access.AccessActivityType;
import ciudadano.consciente.access.AccessLevel;
import ciudadano.consciente.dto.DTOActivity;
import ciudadano.consciente.dto.DTOCreateActivity;
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

    @Mapping(target = "level.levelId", source = "level")
    @Mapping(target = "content.contentId", source = "content")
    Activity dtoToEntity(DTOCreateActivity dtoCreateActivity);

    @Mapping(target = "level", source = "level.levelId")
    @Mapping(target = "content", source = "content.contentId")
    DTOActivity entityToDto(Activity activity);

    List<DTOActivity> entityToDto(List<Activity> all);


}
