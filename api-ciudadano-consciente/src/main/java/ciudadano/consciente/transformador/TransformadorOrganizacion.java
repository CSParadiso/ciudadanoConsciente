package ciudadano.consciente.transformador;

import ciudadano.consciente.modelo.Organizacion;
import ciudadano.consciente.transferible.TransferibleOrganizacion;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface TransformadorOrganizacion {
    // target = "nombreEnTransferible" source = "nombreEnModelo"

    public TransferibleOrganizacion entidadATransferible(Organizacion organizacion);

    public List<TransferibleOrganizacion> entidadATransferible(List<Organizacion> organizacionList);

    Organizacion transferibleAEntidad(String name);
}
