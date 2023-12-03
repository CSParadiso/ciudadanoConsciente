package ciudadano.consciente.recurso;

import ciudadano.consciente.modelo.UsuarioRolNivel;
import ciudadano.consciente.servicio.ServicioNivel;
import ciudadano.consciente.transferible.*;
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

@Tag(name = "Recurso Nivel")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("levels/")
public class RecursoNivel {

    final String PATH_BASE_RECURSO = "/levels/";

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
            responseCode = "201",
            description = "Nivel creado con éxito."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al crear Nivel. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Problemas al crear Nivel. Revisar cabecera 'Warning'."
    )
    public Response crear(TransferibleCrearNivel transferibleCrearNivel) {

        TransferibleNivel nivel = servicioNivel.crear(transferibleCrearNivel);

        URI uri = URI.create(PATH_BASE_RECURSO + nivel.getLevelId());

        return Response.created(uri).entity(nivel).build();

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
    public Response actualizar(TransferibleActualizarNivel transferibleActualizarNivel) {

        return Response.ok(servicioNivel.actualizar(transferibleActualizarNivel)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar un nivel a partir de su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Nivel eliminado con éxito."
    )
    @APIResponse(
            responseCode = "404",
            description = "Problemas al identificar Nivel. Revisar cabecera 'Warning'"
    )
    public Response eliminar(@PathParam("id") Integer identificador) {

        servicioNivel.eliminar(identificador);

        return Response.ok().build();

    }

    @POST
    @Path("{id}/roles")
    @Operation(summary = "Asignar Rol a Usuario en Nivel")
    @APIResponse(
            responseCode = "201",
            description = "Asiganción realizada con éxito"
    )
    @APIResponse(
            responseCode = "404",
            description = "Problemas al asignar Rol. Revisar cabecera 'Warning.'"
    )
    @APIResponse(
            responseCode = "500",
            description = "Problemas al asignar Rol. Revisar cabecera 'Warning.'"
    )
    public Response asignarRol(@PathParam("id") Integer identificador,
                               TransferibleAsignarRolUsuario transferibleAsignarRolUsuario) {

        TransferibleUsuarioRolNivel usuarioRolNivel = servicioNivel.asignar(transferibleAsignarRolUsuario);

        URI uri = URI.create("" + usuarioRolNivel.getUrlId());

        return Response.created(uri).entity(usuarioRolNivel).build();

    }

}
