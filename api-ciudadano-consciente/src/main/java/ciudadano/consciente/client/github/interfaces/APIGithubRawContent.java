package ciudadano.consciente.client.github.interfaces;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://raw.githubusercontent.com/")
public interface APIGithubRawContent {

    @GET
    @Path("{user}/{repo}/{commit}/{path}/{filename}")
    @Produces(MediaType.APPLICATION_JSON)
    @ClientHeaderParam(name = "Accept", value = "application/vnd.github.v3+json")
    Response fetchFile(@PathParam("user") String user,
                       @PathParam("repo") String repo,
                       @PathParam("commit") String commit,
                       @PathParam("path") String path,
                       @PathParam("filename") String filename);

}
