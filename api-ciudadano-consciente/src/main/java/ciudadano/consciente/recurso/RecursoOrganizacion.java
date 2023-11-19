
package ciudadano.consciente.recurso;

import ciudadano.consciente.servicio.ServicioOrganizacion;
import ciudadano.consciente.transferible.TransferibleOrganizacion;
import ciudadano.consciente.transferible.TransferibleRequestCrearOrganizacion;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

@Tag(name = "Recurso Organización")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("organizations/")
public class RecursoOrganizacion {

    @Inject
    Logger auditor;

    @Inject
    ServicioOrganizacion servicioOrganizacion;

    @GET
    @Path("{id}/")
    @Operation( summary = "Retornar una organización a partir de su identificador. Todas si el id es 0.")
    @APIResponse(
            responseCode = "200",
            description = "Éxito al recuprar organización."
    )
    public Response obtener(@PathParam("id") Integer identificador) {

        if(identificador == 0) {
            return Response.ok(servicioOrganizacion.obtenerTodos()).build();
        }

        return Response.ok(servicioOrganizacion.obtener(identificador)).build();

    }

    @POST
    @Path("add/")
    @Operation( summary = "Crear una nueva organización.")
    @APIResponse(
            responseCode = "200",
            description = "Organización creada con éxito."
    )
    public Response crear(TransferibleRequestCrearOrganizacion transferibleRequestCrearOrganizacion) {

        return Response.ok(servicioOrganizacion.crear(transferibleRequestCrearOrganizacion)).build();

    }

    @Path("delete/")
    @DELETE
    @Operation( summary = "Eliminar un usuario a partir de su identificador")
    @APIResponse(
            responseCode = "200",
            description = "Organización eliminada con éxito."
    )
    @APIResponse(
            responseCode = "204",
            description = "Problemas al identificar organización. Revisar cabecera 'Warning'"
    )
    public Response eliminar(@QueryParam("id") Integer identificador) {

        servicioOrganizacion.eliminar(identificador);
        return Response.ok().build();

    }

    @PATCH
    @Path("edit/")
    @Operation( summary = "Editar Organización")
    @APIResponse(
            responseCode = "200",
            description = "Organización editada con éxito."
    )
    public Response editar(@QueryParam("id") Integer identificador,
                           @QueryParam("name") String name,
                           @QueryParam("email") String email,
                           @QueryParam("description") String description) {

        TransferibleOrganizacion organizacion = servicioOrganizacion.editar(identificador, name, email, description);
        return Response.ok(organizacion).build();

    }

    // TODO @PATCH organization

}

