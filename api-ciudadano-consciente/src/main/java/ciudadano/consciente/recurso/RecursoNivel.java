package ciudadano.consciente.recurso;

import ciudadano.consciente.servicio.ServicioNivel;
import ciudadano.consciente.transferible.TransferibleRequestCrearNivel;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

@Tag(name = "Recurso Nivel")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("levels/")
public class RecursoNivel {

    @Inject
    Logger auditor;

    @Inject
    ServicioNivel servicioNivel;

    @GET
    @Path("{id}")
    @Operation(summary = "Retornar un nivel a partir de su identificador. Todos si el id es 0")
    @APIResponse(
            responseCode = "200",
            description = "Nivel retornado con éxito."
    )
    @APIResponse(
            responseCode = "204",
            description = "Problemas al recuperar Nivel. Revisar cabecera 'Warnings'."
    )
    public Response obtener(@PathParam("id") Integer identificador) {

        if(identificador == 0) {
            return Response.ok(servicioNivel.obtenerTodos()).build();
        }

        return Response.ok(servicioNivel.obtener(identificador)).build();

    }

    @POST
    @Path("add/")
    @Operation(summary = "Crear un nivel.")
    @APIResponse(
            responseCode = "200",
            description = "Nivel creado con éxito"
    )
    @APIResponse(
            responseCode = "500",
            description = "Problemas al crear Nivel. Revisar cabecera 'Warning'."
    )
    public Response crear(TransferibleRequestCrearNivel transferibleRequestCrearNivel) {

        return Response.ok(servicioNivel.crear(transferibleRequestCrearNivel)).build();

    }

    @DELETE
    @Path("delete/")
    @Operation(summary = "Eliminar un nivel a partir de su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Nivel elimninado con éxito."
    )
    public Response eliminar(@QueryParam("id") Integer identificador) {

        servicioNivel.eliminar(identificador);
        return Response.ok().build();

    }

    // TODO @POST (corregir Organizacion y Nivel cuando carga) @PATCH

}
