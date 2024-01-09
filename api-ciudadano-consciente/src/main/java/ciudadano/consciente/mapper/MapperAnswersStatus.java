package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOAnswersStatus;
import ciudadano.consciente.dto.DTOCreateAnswersStatus;
import ciudadano.consciente.model.AnswersStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapperAnswersStatus {

    List<DTOAnswersStatus> entityToDto(List<AnswersStatus> all);

    DTOAnswersStatus entityToDto(AnswersStatus answersStatus);

    @Mapping(target = "answersStatusId", ignore = true)
    AnswersStatus dtoToEntity(DTOCreateAnswersStatus dtoCreateAnswersStatus);

}
