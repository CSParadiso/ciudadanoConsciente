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
    @Mapping(target = "answersStatus", source = "answersStatus.answersStatusId")
    List<DTOAnswer> entityToDto(List<Answer> all);

    @Mapping(target = "activity", source = "activity.activityId")
    @Mapping(target = "userId", source = "userId.userId")
    @Mapping(target = "answersStatus", source = "answersStatus.answersStatusId")
    DTOAnswer entityToDto(Answer answer);

}
