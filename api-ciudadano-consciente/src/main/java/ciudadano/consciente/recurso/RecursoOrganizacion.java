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

}
