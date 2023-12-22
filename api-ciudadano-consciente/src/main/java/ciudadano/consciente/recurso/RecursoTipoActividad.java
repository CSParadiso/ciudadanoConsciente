package ciudadano.consciente.recurso;

import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.servicio.ServicioTipoActividad;
import ciudadano.consciente.transferible.TransferibleActualizarTipoActividad;
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
import org.jboss.logging.Logger;

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

    @Inject
    Logger auditor;

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

    @GET
    @Path("{id}")
    @Operation(summary = "Recuperar un Tipo de Actividad de acuerdo a su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Éxito al recuperar Tipo de Actividad."
    )
    @APIResponse(
            responseCode = "404",
            description = "Problemas al recuperar Tipo de Actividad. Revisar cabecera 'Warning'."
    )
    public Response obtener(@PathParam("id") Integer identificador) {

        return Response.ok(servicioTipoActividad.obtener(identificador)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar un Tipo de Actividad de acuerdo a su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Éxito al eliminar Tipo de Actividad."
    )
    @APIResponse(
            responseCode = "404",
            description = "Problemas al eliminar el Tipo de Actividad. Revisar cabecera 'Warning'."
    )
    public Response eliminar(@PathParam("id") Integer identificador) {

        servicioTipoActividad.eliminar(identificador);
        return Response.ok().build();

    }

    @PATCH
    @Path("{id}")
    @Operation(summary = "Actualizar un Tipo de Actividad de acuerdo a su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Éxito al actualizar Tipo de Actividad."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al actualizar Tipo de Actividad. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "404",
            description = "Problemas al actualizar Tipo de Actividad. Revisar cabecera 'Warning'."
    )
    public Response actualizar(@PathParam("id") Integer identificador,
                               TransferibleActualizarTipoActividad transferibleActualizarTipoActividad) {

        auditor.debug(identificador + "-" + transferibleActualizarTipoActividad.getActivityTypeId());

        if(identificador != transferibleActualizarTipoActividad.getActivityTypeId()) {
            throw new HttpBadRequestException("El identificador del Body y del Path deben ser iguales.");
        }

        return Response.ok(servicioTipoActividad.actualizar(identificador, transferibleActualizarTipoActividad)).build();

    }


}
