package ciudadano.consciente.transformador;

import ciudadano.consciente.modelo.TipoActividad;
import ciudadano.consciente.transferible.TransferibleTipoActividad;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TransformadorTipoActividad {
    TipoActividad transferibleAEntidad(String name, String description, String functionalTemplateUrl);

    TransferibleTipoActividad entidadATransferible(TipoActividad tipoActividad);

    List<TransferibleTipoActividad> entidadATransferible(List<TipoActividad> tipoActividads);

}
