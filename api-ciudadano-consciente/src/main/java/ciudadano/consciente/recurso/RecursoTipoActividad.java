package ciudadano.consciente.recurso;

import ciudadano.consciente.modelo.TipoActividad;
import ciudadano.consciente.servicio.ServicioTipoActividad;
import ciudadano.consciente.transferible.TransferibleCrearTipoActividad;
import ciudadano.consciente.transferible.TransferibleTipoActividad;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;

@Tag(name = "Recurso Tipo Actividad")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("tipo-actividad")
public class RecursoTipoActividad {

    final String PATH_BASE_RECURSO = "/tipo-actividad/";

    @Inject
    ServicioTipoActividad servicioTipoActividad;

    @POST
    @Operation(summary = "Crear un Tipo de Actividad.")
    @APIResponse(
            responseCode = "201",
            description = "Tipo de Actividad creada con éxito."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al crear Tipo de Actvidad. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Problemas al crear Tipo de Actvidad. Revisar cabecera 'Warning'."
    )
    public Response crear(TransferibleCrearTipoActividad transferibleCrearTipoActividad) {

        TransferibleTipoActividad tipoActividad = servicioTipoActividad.crear(transferibleCrearTipoActividad);

        URI uri = URI.create(PATH_BASE_RECURSO + tipoActividad.getActivityTypeId());

        return Response.created(uri)
                .entity(tipoActividad)
                .build();

    }

    @GET
    @Operation(summary = "Recuperar todos los Tipos de Actividad")
    @APIResponse(
            responseCode = "200",
            description = "Tipos de Actividad recuperados con éxito."
    )
    public Response obtenerTodos() {

        return Response.ok(servicioTipoActividad.obtenerTodos()).build();

    }


}
