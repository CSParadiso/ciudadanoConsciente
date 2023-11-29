
package ciudadano.consciente.recurso;

import ciudadano.consciente.servicio.ServicioOrganizacion;
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
            description = "Éxito al recuprar organización."
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
            responseCode = "200",
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
    public Response crear(@QueryParam("name") String name,
                          @QueryParam("email") String email,
                          @QueryParam("description") String description) {

        return Response.ok(servicioOrganizacion.crear(name, email, description)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation( summary = "Eliminar un usuario a partir de su identificador")
    @APIResponse(
            responseCode = "200",
            description = "Organización eliminada con éxito."
    )
    @APIResponse(
            responseCode = "204",
            description = "Problemas al identificar organización. Revisar cabecera 'Warning'"
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al identificar organización. Revisar cabecera 'Warning'"
    )
    public Response eliminar(@PathParam("id") Integer identificador) {

        servicioOrganizacion.eliminar(identificador);
        return Response.ok().build();

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
    public Response actualizar(@QueryParam("id") Integer identificador,
                               @QueryParam("name") String name,
                               @QueryParam("email") String email,
                               @QueryParam("description") String description) {

        return Response.ok(servicioOrganizacion.actualizar(identificador, name, email, description)).build();

    }

}

