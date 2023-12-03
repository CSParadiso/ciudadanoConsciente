
package ciudadano.consciente.recurso;

import ciudadano.consciente.servicio.ServicioOrganizacion;
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

@Tag(name = "Recurso Organización")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("organizations/")
public class RecursoOrganizacion {

    final String PATH_BASE_RECURSO = "/organizations/";

    @Inject
    ServicioOrganizacion servicioOrganizacion;

    @GET
    @Operation( summary = "Retornar todas las organizaciones")
    @APIResponse(
            responseCode = "200",
            description = "Éxito al recuperar todas las organizaciones."
    )
    public Response obtener() {

        return Response.ok(servicioOrganizacion.obtenerTodos()).build();

    }

    @GET
    @Path("{id}/")
    @Operation( summary = "Retornar una organización a partir de su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Éxito al recuperar organización."
    )
    @APIResponse(
            responseCode = "204",
            description = "Problemas al recuperar el nivel. Revisar cabecera 'Warning'."
    )
    public Response obtener(@PathParam("id") Integer identificador) {

        return Response.ok(servicioOrganizacion.obtener(identificador)).build();

    }

    @POST
    @Operation( summary = "Crear una nueva organización.")
    @APIResponse(
            responseCode = "201",
            description = "Organización creada con éxito."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al crear Organización. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Problemas al crear Organización. Revisar cabecera 'Warning'."
    )
    public Response crear(TransferibleCrearOrganizacion transferibleCrearOrganizacion) {

        TransferibleOrganizacion organizacion = servicioOrganizacion.crear(transferibleCrearOrganizacion);

        URI uri = URI.create(PATH_BASE_RECURSO + organizacion.getOrganizationId());

        return Response.created(uri).entity(organizacion).build();
    }

    @PATCH
    @Operation( summary = "Actualizar datos de la Organización")
    @APIResponse(
            responseCode = "200",
            description = "Organización editada con éxito."
    )
    @APIResponse(
            responseCode = "204",
            description = "Problemas al actualizar organización. Revisar cabecera 'Warning'"
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al actualizar organización. Revisar cabecera 'Warning'"
    )
    @APIResponse(
            responseCode = "500",
            description = "Problemas al actualizar organización. Revisar cabecera 'Warning'"
    )
    public Response actualizar(TransferibleActualizarOrganizacion transferibleActualizarOrganizacion) {

        return Response.ok(servicioOrganizacion.actualizar(transferibleActualizarOrganizacion)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation( summary = "Eliminar un usuario a partir de su identificador")
    @APIResponse(
            responseCode = "200",
            description = "Organización eliminada con éxito."
    )
    @APIResponse(
            responseCode = "404",
            description = "Problemas al identificar organización. Revisar cabecera 'Warning'"
    )
    public Response eliminar(@PathParam("id") Integer identificador) {

        servicioOrganizacion.eliminar(identificador);

        return Response.ok().build();

    }

    /*@POST
    @Path("{id}/roles")
    @Operation(summary = "Asignar un Rol a un Usuario en una Organización.")
    @APIResponse(
            responseCode = "201",
            description = "Éxito al asignar Rol."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al asignar Rol. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "404",
            description = "Problemas al asignar Rol. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Problemas al asignar Rol. Revisar cabecera 'Warning'."
    )
    public Response asignarRol(@PathParam("id") Integer identificador,
                               TransferibleAsignarRolUsuario transferibleAsignarRolUsuario) {

        TransferibleUsuarioRolNivel usuarioRolNivel = servicioOrganizacion.asignarRol(transferibleAsignarRolUsuario);

        URI uri = URI.create("" + usuarioRolNivel.getUrlId())

    }*/

}

