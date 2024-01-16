package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOVote;
import ciudadano.consciente.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapperVote {

    List<DTOVote> entityToDto(List<Vote> all);

    @Mapping(target = "user", source = "user.userId")
    @Mapping(target = "entityType", source = "entityType.entityTypeId")
    DTOVote entityToDto(Vote vote);

}
