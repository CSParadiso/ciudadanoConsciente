package ciudadano.consciente.transformador;

import ciudadano.consciente.modelo.UsuarioRolNivel;
import ciudadano.consciente.transferible.TransferibleUsuarioRolNivel;
import org.mapstruct.Mapper;

@Mapper
public interface TransformadorUsuarioRolNivel {

    TransferibleUsuarioRolNivel entidadATransferible(UsuarioRolNivel usuarioRolNivel);
}

