package ciudadano.consciente.recurso;

import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.servicio.ServicioUsuario;
import ciudadano.consciente.transferible.TransferibleRequestCrearUsuario;
import ciudadano.consciente.transferible.TransferibleUsuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/users")
public class RecursoUsuario {

    @Inject
    Logger auditor;

    @Inject
    ServicioUsuario servicioUsuario;

  /*  @GET
    @Path("/0/")
    @Operation(summary = "Retornar todos los usuarios")
    @APIResponse(
            responseCode = "200",
            description = "Todos los usuarios recuperados con éxito",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TransferibleUsuario.class)
            )
    )
    public Response obtenerTodos(){

        auditor.debug("Recuperando a todos los usuarios.");

        List<TransferibleUsuario> transferibleUsuarios = servicioUsuario.obtenerTodos();

        return Response.ok(transferibleUsuarios).build();

    }*/

    @GET
    @Path("/{id}/")
    @Operation( summary = "Retornar un usuario a partir de su identificador. Todos si el id es 0.")
    @APIResponse(
            responseCode = "200",
            description = "Éxito al recuperar",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TransferibleUsuario.class)
            )
    )
    @APIResponse(
            responseCode = "204",
            description = "Error al identificar usuario. Revisar cabecera 'Warning'."
    )
    public Response obtener(@PathParam("id") Integer identificador) {

        // Integer.MAX_VALUE es 2,147,483,647. Si el id lo supera lanza 404.

        if(identificador == 0) { // Recuperamos todos los usuarios
            servicioUsuario.obtenerTodos();
            return Response.ok(servicioUsuario.obtenerTodos()).build();
        }

        TransferibleUsuario usuario = servicioUsuario.obtener(identificador);

        return Response.ok(usuario).build();

    }

    @POST
    @Path("/add/")
    @Operation( summary = "Crear un nuevo usuario")
    @APIResponse(
            responseCode = "200",
            description = "Usuario creado con éxito"
    )

    public Response crear(TransferibleRequestCrearUsuario transferibleRequestCrearUsuario) {

        TransferibleUsuario transferibleUsuario = servicioUsuario.crear(transferibleRequestCrearUsuario);

        return Response.ok(transferibleUsuario).build();

    }

    @DELETE
    @Path("/delete/")
    @Operation( summary = "Eliminar un usuario a partir de su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Usuario eliminado con éxito."
    )
    @APIResponse(
            responseCode = "204",
            description = "Usuario no encontrado."
    )
    public Response eliminar(@QueryParam("id") Integer identificador) {

        servicioUsuario.eliminar(identificador);

        return Response.ok().build();

    }

    @PATCH
    @Path("/edit/")
    @Operation( summary = "Editar usuario.")
    @APIResponse(
            responseCode = "200",
            description = "Usuario editado con éxito."
    )
    public Response editar(@QueryParam("id") Integer identificador,
                           @QueryParam("email") String email,
                           @QueryParam("username") String username,
                           @QueryParam("password") String password) {

        TransferibleUsuario usuario = servicioUsuario.editar(identificador, email, username, password);
        return Response.ok(usuario).build();

    }
    // TODO @Patch -> user

}
