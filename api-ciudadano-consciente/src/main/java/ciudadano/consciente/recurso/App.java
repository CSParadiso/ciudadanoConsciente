package ciudadano.consciente.recurso;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;

@ApplicationScoped
@Tag(name = "App")
@Path("app")
public class App {

    @GET
    @Operation(summary = "Acceder al front de la App")
    @APIResponse(
            responseCode = "200",
            description = "Acceso Exitoso"
    )
    @APIResponse(
            responseCode = "404",
            description = "App no encontrada"
    )
    public Response app() {

        return Response.temporaryRedirect(URI.create("http://localhost:5173"))
                .build();

    }

}
