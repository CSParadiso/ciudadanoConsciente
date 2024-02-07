package ciudadano.consciente.usedAPIs.interfaces;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "api-github-content")
public interface ServiceGithubContent {

    // https://raw.githubusercontent.com/:owner/:repo/:commit/:filepath
    @GET
    @Path("{user}/{repo}/{sha}/{filepath}")
    @ClientHeaderParam(name = "Accept", value = "application/vnd.github.v3+json")
    Response verifyFiles(@PathParam("user") String user,
                         @PathParam("repo") String repo,
                         @PathParam("sha") String sha,
                         @PathParam("filepath") String filepath);
}
