package ciudadano.consciente.recurso;

import ciudadano.consciente.servicio.ServicioUsuario;
import ciudadano.consciente.transferible.TransferibleUsuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Recurso Usuario")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/users")
public class RecursoUsuario {

    @Inject
    ServicioUsuario servicioUsuario;

    @GET
    @Operation( summary = "Retornar todos los usuarios.")
    @APIResponse(
            responseCode = "200",
            description = "Éxito al recuperar",
            content = @Content(schema = @Schema(implementation = TransferibleUsuario.class))
            )
    public Response obtener() {

        return Response.ok(servicioUsuario.obtenerTodos()).build();

    }

    @GET
    @Path("/{id}/")
    @Operation( summary = "Retornar un usuario a partir de su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Éxito al recuperar"
    )
    @APIResponse(
            responseCode = "204",
            description = "Error al identificar usuario. Revisar cabecera 'Warning'."
    )
    public Response obtener(@PathParam("id") Integer identificador) {

        // Integer.MAX_VALUE es 2,147,483,647. Si el id lo supera lanza 404.
        return Response.ok(servicioUsuario.obtener(identificador)).build();

    }

    @POST
    @Operation( summary = "Crear un nuevo usuario")
    @APIResponse(
            responseCode = "200",
            description = "Usuario creado con éxito"
    )
    @APIResponse(
            responseCode = "204",
            description = "Error al crear usuario. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "400",
            description = "Error al crear usuario. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Error al crear usuario. Revisar cabecera 'Warning'."
    )
    public Response crear(@QueryParam("email") String email,
                          @QueryParam("username") String username,
                          @QueryParam("password") String password) {

        TransferibleUsuario transferibleUsuario = servicioUsuario.crear(email,  username, password);

        return Response.ok(transferibleUsuario).build();

    }

    @DELETE
    @Operation( summary = "Eliminar un usuario a partir de su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Usuario eliminado con éxito."
    )
    @APIResponse(
            responseCode = "204",
            description = "Error al eliminar usuario. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al identificar usuario. Revisar cabecera 'Warning'"
    )
    public Response eliminar(@QueryParam("id") Integer identificador) {

        servicioUsuario.eliminar(identificador);

        return Response.ok().build();

    }

    @PATCH
    @Operation( summary = "Actualizar datos de Usuario.")
    @APIResponse(
            responseCode = "200",
            description = "Usuario editado con éxito."
    )
    @APIResponse(
            responseCode = "204",
            description = "Error al actualizar usuario. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "400",
            description = "Error al actualizar usuario. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Error al actualizar usuario. Revisar cabecera 'Warning'."
    )
    public Response actualizar(@QueryParam("id") Integer identificador,
                               @QueryParam("email") String email,
                               @QueryParam("username") String username,
                               @QueryParam("password") String password) {

        TransferibleUsuario usuario = servicioUsuario.actualizar(identificador, email, username, password);
        return Response.ok(usuario).build();

    }

}
