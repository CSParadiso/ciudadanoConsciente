package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOActivityTypeVersion;
import ciudadano.consciente.dto.DTOCreateActivityTypeVersion;
import ciudadano.consciente.model.ActivityTypeVersion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapperActivityTypeVersion {

    @Mapping(target = "activityTypeId", source = "activityTypeId.activityTypeId")
    @Mapping(target = "activityTypeVersionStatusId", source = "activityTypeVersionStatusId.activityTypeVersionStatusId")
    DTOActivityTypeVersion entityToDto(ActivityTypeVersion activityTypeVersion);

    List<DTOActivityTypeVersion> entityToDto(List<ActivityTypeVersion> allByActivityType);

    @Mapping(target = "activityTypeId.activityTypeId", source = "activityTypeId")
    @Mapping(target = "activityTypeVersionStatusId.activityTypeVersionStatusId", source = "activityTypeVersionStatusId")
    ActivityTypeVersion dtoToEntity(DTOCreateActivityTypeVersion dtoCreateActivityTypeVersion);

}
