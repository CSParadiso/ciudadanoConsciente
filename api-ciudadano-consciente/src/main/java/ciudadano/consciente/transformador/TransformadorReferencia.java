package ciudadano.consciente.transformador;

import ciudadano.consciente.modelo.Nivel;
import ciudadano.consciente.modelo.Referencia;
import ciudadano.consciente.transferible.TransferibleReferencia;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE) // Ignore advertencia de unmapped
public interface TransformadorReferencia {

    // target = "nombreEnTransferible" source = "nombreEnModelo"
    Referencia transferibleAEntidad(String title);

    TransferibleReferencia entidadATransferible(Referencia referencia);

}
