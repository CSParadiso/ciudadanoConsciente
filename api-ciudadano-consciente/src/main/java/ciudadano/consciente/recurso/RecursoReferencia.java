package ciudadano.consciente.recurso;

import ciudadano.consciente.servicio.ServicioReferencia;
import ciudadano.consciente.transferible.TransferibleActualizarReferencia;
import ciudadano.consciente.transferible.TransferibleCrearReferencia;
import ciudadano.consciente.transferible.TransferibleReferencia;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.List;

@Tag(name = "Recurso Referencia")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("references")
public class RecursoReferencia {

    static final String PATH_BASE_RECURSO = "/references/";

    @Inject
    ServicioReferencia servicioReferencia;

    @GET
    @Operation(summary = "Retornar todas las referencias.")
    @APIResponse(
            responseCode = "200",
            description = "Referencias retornadas con éxito."
    )
    public Response obtenerTodos() {

        return Response.ok(servicioReferencia.obtenerTodos()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Retornar un usuario a partir de su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Referencia recuperada con éxito."
    )
    @APIResponse(
            responseCode = "404",
            description = "Referencia no existe."
    )
    public Response obtener(@PathParam("id") Integer identificador) {

        return Response.ok(servicioReferencia.obtener(identificador)).build();

    }

    @POST
    @Operation(summary = "Crear Referencia para un Nivel.")
    @APIResponse(
            responseCode = "201",
            description = "Referencia creada con éxito"
    )
    @APIResponse(
            responseCode = "404",
            description = "Problemas al crear Referencia. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al crear Referencia. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Problemas al crear Referencia. Revisar cabecera 'Warning'."
    )
    public Response crear(TransferibleCrearReferencia transferibleCrearReferencia) {

        TransferibleReferencia referencia = servicioReferencia.crear(transferibleCrearReferencia);

        URI uri = URI.create(PATH_BASE_RECURSO + referencia.getReferenceId());

        return Response.created(uri).entity(referencia).build();

    }

    @PATCH
    @Operation(summary = "Actualizar una Referencia.")
    @APIResponse(
            responseCode = "200",
            description = "Referencia actualizada con éxito"
    )
    @APIResponse(
            responseCode = "404",
            description = "Problemas al actualizar Referencia. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al actualizar Referencia. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Problemas al actualizar Referencia. Revisar cabecera 'Warning'."
    )
    public Response actualizar(TransferibleActualizarReferencia transferibleActualizarReferencia) {

        return Response.ok(servicioReferencia.actualizar(transferibleActualizarReferencia)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar una Referencia a partir de su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Referencia eliminada con éxito"
    )
    @APIResponse(
            responseCode = "404",
            description = "Problemas al eliminar Referencia. Revisar cabecera 'Warning'."
    )
    public Response eliminar(@PathParam("id") Integer identificador) {

        servicioReferencia.eliminar(identificador);

        return Response.ok().build();

    }

}
