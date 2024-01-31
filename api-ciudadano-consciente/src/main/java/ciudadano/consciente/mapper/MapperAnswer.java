package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOAnswer;
import ciudadano.consciente.model.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapperAnswer {

    @Mapping(target = "activity", source = "activity.activityId")
    @Mapping(target = "userId", source = "userId.userId")
    List<DTOAnswer> entityToDto(List<Answer> all);

    @Mapping(target = "activity", source = "activity.activityId")
    @Mapping(target = "userId", source = "userId.userId")
    DTOAnswer entityToDto(Answer answer);

}
