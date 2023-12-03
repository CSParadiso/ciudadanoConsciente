package ciudadano.consciente.recurso;

import ciudadano.consciente.servicio.ServicioRol;
import ciudadano.consciente.transferible.TransferibleActualizarRol;
import ciudadano.consciente.transferible.TransferibleCrearRol;
import ciudadano.consciente.transferible.TransferibleRol;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;

@Tag(name = "Recurso Rol")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("roles/")
public class RecursoRol {

    final String BASE_PATH_RECURSO = "/roles/";

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
            responseCode = "201",
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
    public Response crear(TransferibleCrearRol transferibleCrearRol) {

        TransferibleRol rol = servicioRol.crear(transferibleCrearRol);

        URI uri = URI.create(BASE_PATH_RECURSO + rol.getRoleId());

        return Response.created(uri).entity(rol).build();

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
    public Response actualizar(TransferibleActualizarRol transferibleActualizarRol) {

        return Response.ok(servicioRol.actualizar(transferibleActualizarRol)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation( summary = "Eliminar un Rol.")
    @APIResponse(
            responseCode = "200",
            description = "Éxito al eliminar Rol."
    )
    @APIResponse(
            responseCode = "404",
            description = "Problemas al eliminar Rol. Revisar cabecera 'Warning'."
    )
    public Response eliminar(@PathParam("id") Integer identificador) {

        servicioRol.eliminar(identificador);

        return Response.ok().build();

    }

    /*@GET
    @Path("{id}/users/levels")
    @Operation(summary = "Obtener todos los Usuarios de un Rol.")
    @APIResponse(
            responseCode = "200",
            description = "Éxito al recuperar Usuarios."
    )
    public Response obtenerUsuarios(@PathParam("id") Integer identificador) {

        return Response.ok(servicioRol.obtenerUsuarios(identificador)).build();

    }
*/
}
