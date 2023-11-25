package ciudadano.consciente.transformador;

import ciudadano.consciente.modelo.Nivel;
import ciudadano.consciente.transferible.TransferibleNivel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TransformadorNivel {

    TransferibleNivel entidadATransferible(Nivel nivel);

    List<TransferibleNivel> entidadATransferible(List<Nivel> niveles);

    Nivel transferibleAEntidad(String name);
}
