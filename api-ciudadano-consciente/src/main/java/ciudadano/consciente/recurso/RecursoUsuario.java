package ciudadano.consciente.recurso;

import ciudadano.consciente.exception.HttpBadRequestException;
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
import org.jboss.logging.Logger;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/users")
public class RecursoUsuario {

    @Inject
    Logger auditor;

    @Inject
    ServicioUsuario servicioUsuario;

    @GET
    @Path("/{id}/")
    @Operation( summary = "Retornar el usuario a partir de su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Usuario recuperado con éxito.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TransferibleUsuario.class)
            )
    )
    @APIResponse(
            responseCode = "204",
            description = "Error al identificar usuario. Revisar cabecera 'Warning'."
    )
    public Response obtenerUsuario(@PathParam("id") Integer identificador) {

        // MAX_VALUE de Integer es 2,147,483,647, si el id supera esto lanza 404.

        TransferibleUsuario usuario = servicioUsuario.obtener(identificador);

        auditor.debug(usuario);

        return Response.ok(usuario).build();

    }

}
