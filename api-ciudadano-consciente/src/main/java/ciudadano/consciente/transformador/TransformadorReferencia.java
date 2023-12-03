package ciudadano.consciente.transformador;

import ciudadano.consciente.modelo.Referencia;
import ciudadano.consciente.transferible.TransferibleReferencia;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE) // Ignore advertencia de unmapped
public interface TransformadorReferencia {

    // target = "nombreEnTransferible" source = "nombreEnModelo"
    Referencia transferibleAEntidad(String title, String url);

    TransferibleReferencia entidadATransferible(Referencia referencia);

    List<TransferibleReferencia> entidadATransferible(List<Referencia> referencias);

}
