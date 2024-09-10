package keycloak.exception;

public class AuthDenialSecurityException extends SecurityException {

  public AuthDenialSecurityException(String mensaje) {
    super(mensaje);
  }

}
