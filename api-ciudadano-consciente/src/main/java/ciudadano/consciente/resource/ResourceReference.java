package ciudadano.consciente.resource;

import ciudadano.consciente.dto.DTOReference;
import ciudadano.consciente.service.ServiceReference;
import ciudadano.consciente.dto.DTOUpdateReference;
import ciudadano.consciente.dto.DTOCreateReference;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;

@Tag(name = "Recurso Referencia")
@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("references")
public class ResourceReference {

    static final String PATH_BASE_RECURSO = "/references/";

    @Inject
    ServiceReference serviceReference;

    @GET
    @Operation(summary = "Retornar todas las referencias.")
    @APIResponse(
            responseCode = "200",
            description = "Referencias retornadas con éxito."
    )
    public Response obtenerTodos() {

        return Response.ok(serviceReference.obtenerTodos()).build();

    }

    @GET
    @Path("{id}")
    @Operation(summary = "Retornar un usuario a partir de su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Referencia recuperada con éxito."
    )
    @APIResponse(
            responseCode = "404",
            description = "Referencia no existe."
    )
    public Response obtener(@PathParam("id") Integer identificador) {

        return Response.ok(serviceReference.obtener(identificador)).build();

    }

    @POST
    @Operation(summary = "Create Referencia para un Nivel.")
    @APIResponse(
            responseCode = "201",
            description = "Referencia creada con éxito"
    )
    @APIResponse(
            responseCode = "404",
            description = "Problemas al create Referencia. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al create Referencia. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Problemas al create Referencia. Revisar cabecera 'Warning'."
    )
    public Response create(DTOCreateReference DTOCreateReference) {

        DTOReference referencia = serviceReference.create(DTOCreateReference);

        URI uri = URI.create(PATH_BASE_RECURSO + referencia.getReferenceId());

        return Response.created(uri).entity(referencia).build();

    }

    @PATCH
    @Operation(summary = "Update una Referencia.")
    @APIResponse(
            responseCode = "200",
            description = "Referencia actualizada con éxito"
    )
    @APIResponse(
            responseCode = "404",
            description = "Problemas al update Referencia. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "400",
            description = "Problemas al update Referencia. Revisar cabecera 'Warning'."
    )
    @APIResponse(
            responseCode = "500",
            description = "Problemas al update Referencia. Revisar cabecera 'Warning'."
    )
    public Response update(DTOUpdateReference DTOUpdateReference) {

        return Response.ok(serviceReference.update(DTOUpdateReference)).build();

    }

    @DELETE
    @Path("{id}")
    @Operation(summary = "Eliminar una Referencia a partir de su identificador.")
    @APIResponse(
            responseCode = "200",
            description = "Referencia eliminada con éxito"
    )
    @APIResponse(
            responseCode = "404",
            description = "Problemas al eliminar Referencia. Revisar cabecera 'Warning'."
    )
    public Response eliminar(@PathParam("id") Integer identificador) {

        serviceReference.eliminar(identificador);

        return Response.ok().build();

    }

}
