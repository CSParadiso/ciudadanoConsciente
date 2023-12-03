package ciudadano.consciente.exception;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

public class ExceptionMappers {

    @Inject
    Logger auditor;

    @ServerExceptionMapper
    public Response mapHttpNoContentException( HttpNoContentException x ) {

        auditor.debug(x.getMessage());
        auditor.debug("mapHttpNoContentException");
        return Response.noContent().header("Warning", x.getMessage()).build();

    }

    @ServerExceptionMapper
    public Response mapHttpBadRequestException( HttpBadRequestException x ) {

        auditor.debug(x.getMessage());
        auditor.debug("mapHttpBadRequestException");
        return Response.status(Response.Status.BAD_REQUEST).header("Warning", x.getMessage()).build();

    }

    @ServerExceptionMapper
    public Response mapHttpInternalServerException( HttpInternalServerException x ) {

        auditor.debug(x.getMessage());
        auditor.debug("mapHttpInternalServerException");
        return Response.serverError().header("Warning", x.getMessage()).build();

    }

    @ServerExceptionMapper
    public Response mapNotFoundExceptionException( HttpNotFoundException x ) {

        auditor.debug(x.getMessage());
        auditor.debug("mapHttpNotFoundException");
        return Response.status(Response.Status.NOT_FOUND).header("Warning", x.getMessage()).build();

    }

}
