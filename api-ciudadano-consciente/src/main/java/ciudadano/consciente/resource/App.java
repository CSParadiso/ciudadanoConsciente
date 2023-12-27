package ciudadano.consciente.resource;

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
    @Operation(summary = "Access Front End of the App")
    @APIResponse(
            responseCode = "200",
            description = "Successfully accessed."
    )
    @APIResponse(
            responseCode = "404",
            description = "App not found."
    )
    public Response app() {

        return Response.temporaryRedirect(URI.create("http://localhost:5173"))
                .build();

    }

}
