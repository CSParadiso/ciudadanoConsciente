package ciudadano.consciente.recurso;

import ciudadano.consciente.servicio.ServicioRol;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Recurso Rol")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("roles/")
public class RecursoRol {

    @Inject
    ServicioRol servicioRol;

    @GET
    @Operation( summary = "Retornar todos los roles." )
    @APIResponse(
            responseCode = "200",
            description = "Éxito al recuperar roles"
    )
    public Response obtenerTodos() {

        return Response.ok(servicioRol.obtenerTodos()).build();

    }

    @GET
    @Path("{id}")
    @Operation( summary = "Retornar un rol a partir de su identificador." )
    @APIResponse(
            responseCode = "200",
            description = "Éxito al recuperar Rol."
    )
    @APIResponse(
            responseCode = "204",
            description = "Problemas al recuperar Rol. Revisar cabecera 'Warning'."
    )
    public Response obtener(@PathParam("id") Integer identificador) {

        return Response.ok(servicioRol.obtener(identificador)).build();

    }

    @POST
    @Operation( summary = "Crear un nuevo Rol.")
    @APIResponse(
            responseCode = "200",
            description = "Éxito al crear Rol."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al crear Rol. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Problemas al crear Rol. Revisar cabecera 'Warning'."
    )
    public Response crear(@QueryParam("name") String name) {

        return Response.ok(servicioRol.crear(name)).build();

    }

    @DELETE
    @Operation( summary = "Eliminar un Rol.")
    @APIResponse(
            responseCode = "200",
            description = "Éxito al eliminar Rol."
    )
    @APIResponse(
            responseCode = "204",
            description = "Problemas al eliminar Rol. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al eliminar Rol. Revisar cabecera 'Warning'."
    )
    public Response eliminar(@QueryParam("id") Integer identificador) {

        servicioRol.eliminar(identificador);

        return Response.ok().build();

    }

    @PATCH
    @Operation(summary = "Actualizar Rol a partir de su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Éxito al actualizar Rol."
    )
    @APIResponse(
            responseCode = "204",
            description = "Problemas al actualizar Rol. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al actualizar Rol. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Problemas al actualizar Rol. Revisar cabecera 'Warning'."
    )
    public Response actualizar(@QueryParam("id") Integer id,
                               @QueryParam("name") String name) {

        return Response.ok(servicioRol.actualizar(id, name)).build();

    }

}
