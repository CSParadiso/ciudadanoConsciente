package ciudadano.consciente.mapper;

import ciudadano.consciente.model.Reference;
import ciudadano.consciente.dto.DTOReference;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE) // Ignore advertencia de unmapped
public interface MapperReference {

    // target = "nombreEnTransferible" source = "nombreEnModelo"
    Reference transferibleAEntidad(String title, String url);

    DTOReference entidadATransferible(Reference reference);

    List<DTOReference> entidadATransferible(List<Reference> references);

}
