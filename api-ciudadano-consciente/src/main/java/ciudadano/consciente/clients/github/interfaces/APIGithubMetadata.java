package ciudadano.consciente.clients.github.interfaces;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://api.github.com/repos/")
public interface APIGithubMetadata {

    @GET
    @Path("{user}/{repo}/contents/{path}")
    @Produces(MediaType.APPLICATION_JSON)
    @ClientHeaderParam(name = "Accept", value = "application/vnd.github.v3+json")
    Response fetchMetadata(@PathParam("user") String user,
                           @PathParam("repo") String repo,
                           @PathParam("path") String path);

    @GET
    @Path("{user}/{repo}/branches/{branch}")
    @Produces(MediaType.APPLICATION_JSON)
    @ClientHeaderParam(name = "Accept", value = "application/vnd.github.v3+json")
    Response fetchCommitInfo(@PathParam("user") String user,
                             @PathParam("repo") String repo,
                             @PathParam("branch") String branch);

    @GET
    @Path("{user}/{repo}/contents/{path}/{filename}")
    @Produces(MediaType.APPLICATION_JSON)
    @ClientHeaderParam(name = "Accept", value = "application/vnd.github.v3+json")
    Response fetchDownloadUrl(@PathParam("user") String user,
                              @PathParam("repo") String repo,
                              @PathParam("path") String path,
                              @PathParam("filename") String filename,
                              @QueryParam("shaCommit") String shaCommit);

    // HARDCODING IN CALLING
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ClientHeaderParam(name = "Accept", value = "application/vnd.github.v3+json")
    Response fetchFile();
    //    Response fetchFile(@PathParam("user") String user,
    //                       @PathParam("repo") String repo,
    //                       @PathParam("sha") String sha,
    //                       @PathParam("filepath") String filepath);

}
