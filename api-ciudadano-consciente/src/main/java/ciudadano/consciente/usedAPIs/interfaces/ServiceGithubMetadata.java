package ciudadano.consciente.usedAPIs.interfaces;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "api-github-metadata")
public interface ServiceGithubMetadata {

    final String NAME = "github";

//`https://api.github.com/repos/${jsonObject.user}/${jsonObject.repo}/contents/${jsonObject.path}`
    @GET
    @Path("{user}/{repo}/contents/{path}")
    @Produces(MediaType.APPLICATION_JSON)
    @ClientHeaderParam(name = "Accept", value = "application/vnd.github.v3+json")
    Response verifyFiles(@PathParam("user") String user,
                         @PathParam("repo") String repo,
                         @PathParam("path") String path);

}
