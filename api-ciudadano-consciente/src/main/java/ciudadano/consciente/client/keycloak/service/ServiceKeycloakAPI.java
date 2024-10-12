package ciudadano.consciente.client.keycloak.service;

import ciudadano.consciente.client.keycloak.interfaces.APIKeycloak;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@RequestScoped
public class ServiceKeycloakAPI {

  @Inject
  Logger audit;

  @Inject
  @RestClient
  APIKeycloak apiKeycloak;

  @ConfigProperty(name = "realm")
  String realm;

  public boolean createRole(String role, String description) {

    return apiKeycloak.createRole(realm, role, description);

  }

  public boolean updateRole(String role, String description) {

    return apiKeycloak.updateRole(realm, role, description);

    // String body = "{\"name\": \"" + role + "\",\n" +
    // " \"description\": \"" + description + "\", \n" +
    // " \"composite\": false, \n" +
    // " \"clientRole\": false \n }";
    // ;
    // audit.debug("Response from Keycloak API: " +
    // apiKeycloak.updateRole(CIUDADANO_REALM, role, body));

  }

  public boolean deleteRole(String role) {

    return apiKeycloak.deleteRole(realm, role);
    // audit.debug("Response from Keycloak API: " +
    // apiKeycloak.deleteRole(CIUDADANO_REALM, role));

  }

  public boolean deleteUser(String authServerId) {

    return apiKeycloak.deleteUser(realm, authServerId);

  }

  public boolean assignRole(String authServerId, String role) {

    return apiKeycloak.assignRole(realm, role, authServerId);

  }

  public boolean removeRole(String authServerId, String role) {

    return apiKeycloak.removeRole(realm, role, authServerId);

  }

}
