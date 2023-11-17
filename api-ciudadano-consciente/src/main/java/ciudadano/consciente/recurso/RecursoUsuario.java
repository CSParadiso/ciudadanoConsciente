package ciudadano.consciente.recurso;

import ciudadano.consciente.acceso.AccesoUsuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/")
public class RecursoUsuario {

    @Inject
    Logger auditor;

    @Inject
    AccesoUsuario accesoUsuario;

    @GET
    public void getUsuario() {

        auditor.debug(accesoUsuario.getName(1));
    }

}
