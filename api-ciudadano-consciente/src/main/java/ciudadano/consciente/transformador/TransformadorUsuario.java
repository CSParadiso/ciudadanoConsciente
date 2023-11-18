package ciudadano.consciente.transformador;

import ciudadano.consciente.modelo.Usuario;
import ciudadano.consciente.transferible.TransferibleRequestCrearUsuario;
import ciudadano.consciente.transferible.TransferibleUsuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TransformadorUsuario {

    // target = "nombreEnTransferible" source = "nombreEnModelo"
    TransferibleUsuario entidadATransferible(Usuario usuario);

    List<TransferibleUsuario> entidadATransferible(List<Usuario> usuarioList);

    Usuario transferibleAEntidad(TransferibleRequestCrearUsuario transferible);

}
