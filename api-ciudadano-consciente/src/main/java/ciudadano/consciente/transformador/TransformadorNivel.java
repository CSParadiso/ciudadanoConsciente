package ciudadano.consciente.transformador;

import ciudadano.consciente.modelo.Nivel;
import ciudadano.consciente.transferible.TransferibleNivel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE) // Ignore advertencia de unmapped
public interface TransformadorNivel {

    TransferibleNivel entidadATransferible(Nivel nivel);

    List<TransferibleNivel> entidadATransferible(List<Nivel> niveles);

    Nivel transferibleAEntidad(String name);
}
