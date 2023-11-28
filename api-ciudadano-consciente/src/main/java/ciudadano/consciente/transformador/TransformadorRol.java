package ciudadano.consciente.transformador;

import ciudadano.consciente.modelo.Rol;
import ciudadano.consciente.transferible.TransferibleRol;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE) // Ignore advertencia de unmapped
public interface TransformadorRol {

    List<TransferibleRol> entidadATransferible(List<Rol> roles);

    TransferibleRol entidadATransferible(Rol rol);

    Rol transferibleAEntidad(String name);

}
