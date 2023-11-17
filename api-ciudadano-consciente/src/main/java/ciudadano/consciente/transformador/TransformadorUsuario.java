package ciudadano.consciente.transformador;

import ciudadano.consciente.modelo.Usuario;
import ciudadano.consciente.transferible.TransferibleUsuario;
import org.mapstruct.Mapper;

@Mapper
public interface TransformadorUsuario {

    // target = "nombreEnTransferible" source = "nombreEnModelo"
    TransferibleUsuario entidadATransferible(Usuario usuario);

}
