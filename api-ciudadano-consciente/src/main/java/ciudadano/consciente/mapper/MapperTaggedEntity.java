package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.DTOTaggedEntity;
import ciudadano.consciente.dto.DTOVotedEntity;
import ciudadano.consciente.model.*;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper
public interface MapperTaggedEntity {

    List<DTOTaggedEntity> taggedOrganizationEntityToDto(List<TaggedOrganization> tags);

    List<DTOTaggedEntity> taggedLevelEntityToDto(List<TaggedLevel> allTags);

    List<DTOTaggedEntity> taggedActivityTypeEntityToDto(List<TaggedActivityType> allTags);

    List<DTOTaggedEntity> taggedConcernEntityToDto(List<TaggedConcern> tags);

    List<DTOTaggedEntity> taggedReferenceEntityToDto(List<TaggedReference> allTags);

    List<DTOTaggedEntity> taggedActivityTypeVersionEntityToDto(List<TaggedActivityTypeVersion> allTags);

    List<DTOTaggedEntity> taggedContentEntityToDto(List<TaggedContent> allTags);

}
