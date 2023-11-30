package ciudadano.consciente.transformador;

import ciudadano.consciente.modelo.Usuario;
import ciudadano.consciente.transferible.TransferibleUsuario;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE) // Ignore advertencia de unmapped
public interface TransformadorUsuario {

    // target = "nombreEnTransferible" source = "nombreEnModelo"
    TransferibleUsuario entidadATransferible(Usuario usuario);

    List<TransferibleUsuario> entidadATransferible(List<Usuario> usuarioList);

    Usuario transferibleAEntidad(String email, String username);
}
