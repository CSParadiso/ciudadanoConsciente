package ciudadano.consciente.mapper;

import ciudadano.consciente.dto.*;
import ciudadano.consciente.model.Level;
import ciudadano.consciente.model.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE) // Ignore advertencia de unmapped
public interface MapperLevel {
    // target = "nombreEnTransferible" source = "nombreEnModelo"

    @Mapping(target = "organization", source = "organization.organizationId")
    DTOLevel entityToDto(Level level);

    List<DTOLevel> entityToDto(List<Level> niveles);

    @Mapping(target = "organization", source = "organization.organizationId")
    DTOLevelPath entityToPathDto(Level niveles);

    List<DTOLevelPath> entityToPathDto(List<Level> niveles);

    @Mapping(target = "organization", source = "organization.organizationId")
    DTOLevelPathWithVotes entityToPathWithVotesDto(Level level);

    @Mapping(target = "organization", source = "organizationId")
    List<DTOLevelPathWithVotes> entityToPathWithVotesDto(List<Level> levels);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "organization", source = "organization")
    Level dtoToEntity(String name, Organization organization);

    DTOLevelPathUsedRecentlyByUser entityToRecentlyUsedPathDto(DTOLevelPathUsedRecentlyByUser paths);

    List<DTOLevelPathUsedRecentlyByUser> entityToRecentlyUsedPathDto(List<DTOLevelPathUsedRecentlyByUser> paths);

    @Mapping(target = "parent", source = "parent.levelId")
    @Mapping(target = "organization", source = "organization.organizationId")
    DTOLevelWithChildrens entityToLevelWithChildrensDto(Level allChildrens);

    List<DTOLevelWithChildrens> entityToLevelWithChildrensDto(List<Level> allChildrens);

}
