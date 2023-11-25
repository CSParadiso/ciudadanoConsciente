package ciudadano.consciente.recurso;

import ciudadano.consciente.servicio.ServicioNivel;
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
    @Operation(summary = "Retornar todos los niveles.")
    @APIResponse(
            responseCode = "200",
            description = "Niveles retornados con éxito."
    )
    @APIResponse(
            responseCode = "204",
            description = "Problemas al recuperar los niveles. Revisar cabecera 'Warnings'."
    )
    public Response obtener() {

        return Response.ok(servicioNivel.obtenerTodos()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Retornar un nivel a partir de su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Nivel retornado con éxito."
    )
    @APIResponse(
            responseCode = "204",
            description = "Problemas al recuperar Nivel. Revisar cabecera 'Warnings'."
    )
    public Response obtener(@PathParam("id") Integer identificador) {

        return Response.ok(servicioNivel.obtener(identificador)).build();

    }

    @POST
    @Operation(summary = "Crear un nivel.")
    @APIResponse(
            responseCode = "200",
            description = "Nivel creado con éxito"
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al crear Nivel. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Problemas al crear Nivel. Revisar cabecera 'Warning'."
    )
    public Response crear(@QueryParam("name") String name,
                          @QueryParam("description") String description,
                          @QueryParam("organization") Integer organization,
                          @QueryParam("parent") Integer parent) {

        return Response.ok(servicioNivel.crear(name, description, organization, parent)).build();

    }

    @DELETE
    @Operation(summary = "Eliminar un nivel a partir de su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Nivel elimninado con éxito."
    )
    @APIResponse(
            responseCode = "204",
            description = "Problemas al eliminar Nivel. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al identificar Nivel. Revisar cabecera 'Warning'"
    )
    public Response eliminar(@QueryParam("id") Integer identificador) {

        servicioNivel.eliminar(identificador);
        return Response.ok().build();

    }

    @PATCH
    @Operation(summary = "Actualizar un nivel a partir de su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Nivel editado con éxito"
    )
    @APIResponse(
            responseCode = "204",
            description = "Problemas al actualizar Nivel. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al actualizar Nivel. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Problemas al actualizar Nivel. Revisar cabecera 'Warning'."
    )
    public Response actualizar(@QueryParam("id") Integer identificador,
                           @QueryParam("name") String name,
                           @QueryParam("description") String description,
                           @QueryParam("organization") Integer organization,
                           @QueryParam("parent") Integer parent) {

        return Response.ok(servicioNivel.actualizar(identificador, name, description, organization, parent)).build();

    }

}
