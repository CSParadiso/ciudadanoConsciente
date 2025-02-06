package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOCreateNotificationTemplate;
import ciudadano.consciente.dto.DTONotificationTemplate;
import ciudadano.consciente.model.NotificationTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapperNotificationTemplate {

    List<DTONotificationTemplate> entityToDto(List<NotificationTemplate> all);

    DTONotificationTemplate entityToDto(NotificationTemplate entityType);

}
