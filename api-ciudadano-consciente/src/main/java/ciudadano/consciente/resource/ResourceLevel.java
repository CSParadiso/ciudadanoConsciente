package ciudadano.consciente.resource;

import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.service.ServiceLevel;
import ciudadano.consciente.dto.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;

@Tag(name = "Recurso Nivel")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("levels/")
public class ResourceLevel {

    final String PATH_BASE_RECURSO = "/levels/";

    @Inject
    ServiceLevel serviceLevel;

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

        return Response.ok(serviceLevel.obtenerTodos()).build();

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

        return Response.ok(serviceLevel.obtener(identificador)).build();

    }

    @POST
    @Operation(summary = "Create un nivel.")
    @APIResponse(
            responseCode = "201",
            description = "Nivel creado con éxito."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al create Nivel. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Problemas al create Nivel. Revisar cabecera 'Warning'."
    )
    public Response create(DTOCreateLevel DTOCreateLevel) {

        DTOLevel nivel = serviceLevel.create(DTOCreateLevel);

        URI uri = URI.create(PATH_BASE_RECURSO + nivel.getLevelId());

        return Response.created(uri).entity(nivel).build();

    }

    @PATCH
    @Path("{id}")
    @Operation(summary = "Update un Nivel de acuerdo a su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Éxito al update Nivel."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al update Nivel. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "404",
            description = "Problemas al update Nivel. Revisar cabecera 'Warning'."
    )
    public Response update(@PathParam("id") Integer identificador,
                               DTOUpdateLevel dtoUpdateLevel) {

        if(identificador != dtoUpdateLevel.getLevelId()) {
            throw new HttpBadRequestException("El identificador del Body y del Path deben ser iguales.");
        }

        return Response.ok(serviceLevel.update(identificador, dtoUpdateLevel)).build();

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

        serviceLevel.eliminar(identificador);

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
                               DTOAssignRolToUser DTOAssignRolToUser) {

        DTOUserRoleLevel usuarioRolNivel = serviceLevel.asignarRol(DTOAssignRolToUser);

        URI uri = URI.create("" + usuarioRolNivel.getUrlId());

        return Response.created(uri).entity(usuarioRolNivel).build();

    }

}
