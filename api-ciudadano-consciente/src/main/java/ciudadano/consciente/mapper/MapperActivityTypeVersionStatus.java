package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOActivityTypeVersionStatus;
import ciudadano.consciente.dto.DTOCreateActivityTypeVersionStatus;
import ciudadano.consciente.model.ActivityTypeVersionStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapperActivityTypeVersionStatus {

    List<DTOActivityTypeVersionStatus> entityToDto(List<ActivityTypeVersionStatus> all);

    @Mapping(target = "answersStatusId", source = "activityTypeVersionStatus.activityTypeVersionStatusId")
    DTOActivityTypeVersionStatus entityToDto(ActivityTypeVersionStatus activityTypeVersionStatus);

    ActivityTypeVersionStatus dtoToEntity(DTOCreateActivityTypeVersionStatus dtoCreateActivityTypeVersionStatus);

}
