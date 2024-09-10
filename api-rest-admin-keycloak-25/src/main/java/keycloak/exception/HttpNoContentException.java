package keycloak.exception;

public class HttpNoContentException extends RuntimeException {

  public HttpNoContentException(String mensaje) {
    super(mensaje);
  }

}
