package ciudadano.consciente.resource;

import ciudadano.consciente.service.ServiceRole;
import ciudadano.consciente.dto.DTOUpdateRole;
import ciudadano.consciente.dto.DTOCreateRole;
import ciudadano.consciente.dto.DTORole;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;

@Tag(name = "Recurso Rol")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("roles/")
public class ResourceRole {

    final String BASE_PATH_RECURSO = "/roles/";

    @Inject
    ServiceRole serviceRole;

    @GET
    @Operation( summary = "Retornar todos los roles." )
    @APIResponse(
            responseCode = "200",
            description = "Éxito al recuperar roles"
    )
    public Response obtenerTodos() {

        return Response.ok(serviceRole.obtenerTodos()).build();

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

        return Response.ok(serviceRole.obtener(identificador)).build();

    }

    @POST
    @Operation( summary = "Create un nuevo Rol.")
    @APIResponse(
            responseCode = "201",
            description = "Éxito al create Rol."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al create Rol. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Problemas al create Rol. Revisar cabecera 'Warning'."
    )
    public Response create(DTOCreateRole DTOCreateRole) {

        DTORole rol = serviceRole.create(DTOCreateRole);

        URI uri = URI.create(BASE_PATH_RECURSO + rol.getRoleId());

        return Response.created(uri).entity(rol).build();

    }

    @PATCH
    @Operation(summary = "Update Rol a partir de su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Éxito al update Rol."
    )
    @APIResponse(
            responseCode = "204",
            description = "Problemas al update Rol. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al update Rol. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Problemas al update Rol. Revisar cabecera 'Warning'."
    )
    public Response update(DTOUpdateRole DTOUpdateRol) {

        return Response.ok(serviceRole.update(DTOUpdateRol)).build();

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

        serviceRole.eliminar(identificador);

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
