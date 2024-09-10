package keycloak.exception;

import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import keycloak.dto.DTOException;

public class ExceptionMappers {

  @Inject
  Logger audit;

  @ServerExceptionMapper
  public RestResponse<DTOException> mapHttpNoContentException(HttpNoContentException x, @Context UriInfo uriInfo) {

    audit.debug("mapHttpNoContentException: " + x.getMessage());
    DTOException body = new DTOException();
    body.setStatus(204);
    body.setDetail(x.getMessage());
    body.setInstance(uriInfo.getPath());
    return RestResponse.ResponseBuilder.create(RestResponse.Status.NO_CONTENT, body).header("Warning", x.getMessage())
        .build();

  }

  @ServerExceptionMapper
  public RestResponse<DTOException> mapHttpBadRequestException(HttpBadRequestException x, @Context UriInfo uriInfo) {

    audit.debug("mapHttpBadRequestException: " + x.getMessage());
    DTOException body = new DTOException();
    body.setStatus(400);
    body.setDetail(x.getMessage());
    body.setInstance(uriInfo.getPath());
    return RestResponse.ResponseBuilder.create(RestResponse.Status.BAD_REQUEST, body).header("Warning", x.getMessage())
        .build();

  }

  @ServerExceptionMapper
  public RestResponse<DTOException> mapHttpInternalServerException(HttpInternalServerException x,
      @Context UriInfo uriInfo) {

    audit.debug("mapHttpInternalServerException: " + x.getMessage());
    DTOException body = new DTOException();
    body.setStatus(500);
    body.setDetail(x.getMessage());
    body.setInstance(uriInfo.getPath());
    return RestResponse.ResponseBuilder.create(RestResponse.Status.INTERNAL_SERVER_ERROR, body)
        .header("Warning", x.getMessage()).build();

  }

  @ServerExceptionMapper
  public RestResponse<DTOException> mapAuthDenialSecurityException(AuthDenialSecurityException x,
      @Context UriInfo uriInfo) {

    audit.debug("mapAuthDenialSecurityException: " + x.getMessage());
    DTOException body = new DTOException();
    body.setStatus(401);
    body.setDetail(x.getMessage());
    body.setInstance(uriInfo.getPath());
    return RestResponse.ResponseBuilder.create(RestResponse.Status.UNAUTHORIZED, body).header("Warning", x.getMessage())
        .build();

  }

}