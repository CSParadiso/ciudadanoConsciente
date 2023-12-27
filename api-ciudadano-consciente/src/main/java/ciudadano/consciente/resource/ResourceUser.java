package ciudadano.consciente.resource;

import ciudadano.consciente.dto.DTOUser;
import ciudadano.consciente.service.ServiceUser;
import ciudadano.consciente.dto.DTOUpdateUser;
import ciudadano.consciente.dto.DTOCreateUser;
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

import java.net.URI;

@Tag(name = "Recurso Usuario")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/users")
public class ResourceUser {

    final String BASE_PATH_RECURSO = "/users/";

    @Inject
    ServiceUser servicioUsuario;

    @GET
    @Operation( summary = "Retornar todos los usuarios.")
    @APIResponse(
            responseCode = "200",
            description = "Éxito al recuperar",
            content = @Content(schema = @Schema(implementation = DTOUser.class))
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
    @Operation( summary = "Create un nuevo usuario")
    @APIResponse(
            responseCode = "201",
            description = "Usuario creado con éxito"
    )
    @APIResponse(
            responseCode = "400",
            description = "Error al create usuario. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Error al create usuario. Revisar cabecera 'Warning'."
    )
    public Response create(DTOCreateUser DTOCreateUser) {

        DTOUser usuario = servicioUsuario.create(DTOCreateUser);

        URI uri = URI.create(BASE_PATH_RECURSO + usuario.getUserId());

        return Response.created(uri).entity(usuario).build();

    }

    @PATCH
    @Operation( summary = "Update datos de Usuario.")
    @APIResponse(
            responseCode = "200",
            description = "Usuario editado con éxito."
    )
    @APIResponse(
            responseCode = "204",
            description = "Error al update usuario. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "400",
            description = "Error al update usuario. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Error al update usuario. Revisar cabecera 'Warning'."
    )
    public Response update(DTOUpdateUser DTOUpdateUser) {

        DTOUser usuario = servicioUsuario.update(DTOUpdateUser);
        return Response.ok(usuario).build();

    }

    @DELETE
    @Path("{id}")
    @Operation( summary = "Eliminar un usuario a partir de su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Usuario eliminado con éxito."
    )
    @APIResponse(
            responseCode = "404",
            description = "Problemas al identificar usuario. Revisar cabecera 'Warning'"
    )
    public Response eliminar(@PathParam("id") Integer identificador) {

        servicioUsuario.eliminar(identificador);

        return Response.ok().build();

    }

}
