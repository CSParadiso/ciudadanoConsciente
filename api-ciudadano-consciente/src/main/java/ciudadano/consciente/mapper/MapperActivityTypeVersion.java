package ciudadano.consciente.mapper;

import ciudadano.consciente.clients.dto.DTOResponseContent;
import ciudadano.consciente.dto.DTOActivityTypeVersion;
import ciudadano.consciente.dto.DTOCreateActivityTypeVersion;
import ciudadano.consciente.dto.DTOVersionContent;
import ciudadano.consciente.model.ActivityTypeVersion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapperActivityTypeVersion {

    @Mapping(target = "activityTypeId", source = "activityTypeVersion.activityTypeId.activityTypeId")
    @Mapping(target = "activityTypeVersionStatusId", source = "activityTypeVersion.activityTypeVersionStatusId.activityTypeVersionStatusId")
    @Mapping(target = "versionServer", source = "activityTypeVersion.versionServer.versionServerId")
    DTOActivityTypeVersion entityToDto(ActivityTypeVersion activityTypeVersion);

    List<DTOActivityTypeVersion> entityToDto(List<ActivityTypeVersion> allByActivityType);

    @Mapping(target = "activityTypeId.activityTypeId", source = "activityTypeId")
    ActivityTypeVersion dtoToEntity(DTOCreateActivityTypeVersion dtoCreateActivityTypeVersion);

    @Mapping(target = "model", source = "responseContent.model.content")
    @Mapping(target = "template", source = "responseContent.template.content")
    @Mapping(target = "readme", source = "responseContent.readme.content")
    DTOVersionContent responseToVersionContent(DTOResponseContent responseContent);

}
