package ciudadano.consciente.recurso;

import ciudadano.consciente.servicio.ServicioReferencia;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URL;

@Tag(name = "Recurso Referencia")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("reference")
public class RecursoReferencia {

    @Inject
    ServicioReferencia servicioReferencia;

    @POST
    @Operation(summary = "Crear Referencia para un Nivel.")
    @APIResponse(
            responseCode = "200",
            description = "Referencia creada con éxito"
    )
    @APIResponse(
            responseCode = "204",
            description = "Problemas al crear Referencia. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al crear Referencia. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Problemas al crear Referencia. Revisar cabecera 'Warning'."
    )
    public Response crear(@QueryParam("level") Integer level,
                          @QueryParam("title") String title,
                          @QueryParam("url") String url,
                          @QueryParam("description") String description) {

        return Response.ok(servicioReferencia.crear(level, title, url, description)).build();

    }

}
