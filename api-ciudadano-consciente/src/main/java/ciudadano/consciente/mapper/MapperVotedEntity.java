package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOVotedEntity;
import ciudadano.consciente.model.*;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper
public interface MapperVotedEntity {

    List<DTOVotedEntity> votedLevelEntityToDto(List<VotedLevel> votes);

    List<DTOVotedEntity> votedOrganizationEntityToDto(List<VotedOrganization> votes);

    List<DTOVotedEntity> votedActivityTypeEntityToDto(List<VotedActivityType> allVotes);

    List<DTOVotedEntity> votedConcernEntityToDto(List<VotedConcern> allVotes);

    List<DTOVotedEntity> votedReferenceEntityToDto(List<VotedReference> votes);

    List<DTOVotedEntity> votedActivityTypeVersionEntityToDto(List<VotedActivityTypeVersion> votes);

    List<DTOVotedEntity> votedContentEntityToDto(List<VotedContent> allVotes);

}
