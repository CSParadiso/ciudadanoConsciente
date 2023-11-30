package ciudadano.consciente.transformador;

import ciudadano.consciente.modelo.Organizacion;
import ciudadano.consciente.transferible.TransferibleOrganizacion;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE) // Ignore advertencia de unmapped
public interface TransformadorOrganizacion {
    // target = "nombreEnTransferible" source = "nombreEnModelo"

    TransferibleOrganizacion entidadATransferible(Organizacion organizacion);

    List<TransferibleOrganizacion> entidadATransferible(List<Organizacion> organizacionList);

    Organizacion transferibleAEntidad(String email);
}
