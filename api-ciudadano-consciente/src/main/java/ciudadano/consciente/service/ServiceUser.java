package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessUser;
import ciudadano.consciente.access.AccessVote;
import ciudadano.consciente.client.keycloak.service.ServiceKeycloakAPI;
import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.AuthDenialSecurityException;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.mapper.MapperUser;
import ciudadano.consciente.mapper.MapperVote;
import ciudadano.consciente.model.User;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.oidc.UserInfo;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceUser {

  @Inject
  Logger audit;

  @Inject
  UtilityVerifyRequestField utilityVerifyRequestField;

  @Inject
  AccessUser accessUser;

  @Inject
  MapperUser mapperUser;

  @Inject
  AccessVote accessVote;

  @Inject
  MapperVote mapperVote;

  @Inject
  ServiceKeycloakAPI keycloak;

  public DTOUser get(Integer id) {

    audit.debug("Retrieving User " + id + ".");
    User user = accessUser.get(id)
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperUser.entityToDto(user);

  }

  public DTOUser getByUsername(String username) {

    audit.debug("Retrieving User " + username + ".");
    User user = accessUser.getByUsername(username)
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperUser.entityToDto(user);

  }

  public DTOUser getByEmail(String email) {

    audit.debug("Retrieving User with email " + email + ".");
    User user = accessUser.getByEmail(email)
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperUser.entityToDto(user);

  }

  public List<DTOUser> getAll() {

    audit.debug("Retrieving all Users.");
    List<User> userList = accessUser.getAll();

    audit.debug("Mapping EntityType into DTO.");
    return mapperUser.entityToDto(userList);

  }

  @Deprecated
  @Transactional(Transactional.TxType.REQUIRED)
  public DTOUser createUser(DTOCreateUser dtoCreateUser) {

    audit.debug("Creating new User.");
    String email = dtoCreateUser.getEmail();
    String username = dtoCreateUser.getUsername();
    if (accessUser.existsEmail(email)) {
      throw new HttpBadRequestException("Email already exists.");
    }

    if (accessUser.existsUsername(username)) {
      throw new HttpBadRequestException("Username already exists.");
    }

    audit.debug("Mapping DTO into EntityType.");
    User user = mapperUser.dtoToEntity(dtoCreateUser);

    audit.debug("Saving User " + user.getUserId() + ".");
    accessUser.save(user)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist new User."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperUser.entityToDto(user);

  }

  @Deprecated(since = "1.1.0. The only field that can be updated is 'authServerId' if IdentityProvider changes.")
  @Transactional(Transactional.TxType.REQUIRED)
  public DTOUser updateUser(Integer id, DTOUpdateUser dtoUpdateUser) {

    audit.debug("Updating User " + id + ".");
    User user = accessUser.get(id)
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    String email = dtoUpdateUser.getEmail();
    String username = dtoUpdateUser.getUsername();
    String password = dtoUpdateUser.getPassword();

    if (utilityVerifyRequestField.isValidField(username)) {
      if (accessUser.existsUsername(username)) {
        throw new HttpBadRequestException("Username already exists.");
      }
      user.setUsername(username);
    }

    if (utilityVerifyRequestField.isValidField(email)) {
      if (accessUser.existsEmail(email)) {
        throw new HttpBadRequestException("Email already exists.");
      }
      user.setEmail(email);
    }

    if (utilityVerifyRequestField.isValidField(password)) {
      user.setAuthServerId(password);
    }

    audit.debug("Saving User " + user.getUserId() + ".");
    accessUser.save(user)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated User."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperUser.entityToDto(user);

  }

  @Deprecated(since = "1.1.0. Deleting should explicitly log who is requesting action.")
  @Transactional(Transactional.TxType.REQUIRED)
  public DTOUser deleteUser(Integer id) {

    audit.debug("Deleting User " + id + ".");
    User user = accessUser.get(id)
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    // TODO try to remove from Identity Provider tru API

    if (!accessUser.remove(user.getUserId())) {
      throw new HttpInternalServerException("Failed to delete User.");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperUser.entityToDto(user);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOUser delete(Integer id, UserInfo userInfo, boolean userRequested) {

    audit.debug("Retrieving User " + id + ".");
    User user = accessUser.get(id)
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    if (userRequested) {
      // Just for security double check
      if (user.getAuthServerId().equals(userInfo.getSubject()) &&
          user.getEmail().equals(userInfo.getEmail()) &&
          user.getUsername().equals(userInfo.getPreferredUserName())) {

        audit.debug("User " + user.getUserId() + " is deleting himself.");

      } else {
        audit.warn("Mismatch: NOT AUTHORIZED TO DELETE. User Claims doesn't match User data.");
        throw new AuthDenialSecurityException(
            "Mismatch: NOT AUTHORIZED TO DELETE. User Claims doesn't match User data.");
      }
    }

    // UPDATE KEYCLOAK SERVER (si no se puede actualizar, falla)
    audit.debug("Trying to delete User tru the Keycloak API.");
    if (!keycloak.deleteUser(user.getAuthServerId())) {
      audit.debug("Failed to delete User tru the Keycloak API");
      throw new HttpInternalServerException("Failed to delete User tru the Keycloak API");
    }

    if (!accessUser.remove(user.getUserId())) {
      throw new HttpInternalServerException("Failed to delete User.");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperUser.entityToDto(user);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOUser create(String authServerId, String username, String email) {

    audit.debug("Creating new User.");
    User user = new User(authServerId, username, email);

    audit.debug("Saving User with authServerId " + authServerId + ".");
    try {
      accessUser.save(user)
          .orElseThrow(() -> new HttpInternalServerException("Failed to persist new User."));
    } catch (ConstraintViolationException e) {
      audit.debug("User already exists: " + e.getErrorMessage());
      throw new HttpBadRequestException("User already exists: " + e.getErrorMessage());
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperUser.entityToDto(user);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOUser update(Integer id, DTOUpdateUserIdentityProvider dtoUpdateUserIdentityProvider) {

    audit.debug("Updating User " + id + ".");
    User user = accessUser.get(id)
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    // Just for double check security
    if (user.getAuthServerId().equals(dtoUpdateUserIdentityProvider.getActualAuthServerId())) {
      audit.debug("Updating authServerId of User " + user.getUserId() + "from " +
          dtoUpdateUserIdentityProvider.getActualAuthServerId() + " to " +
          dtoUpdateUserIdentityProvider.getNewAuthServerId());
      user.setAuthServerId(dtoUpdateUserIdentityProvider.getNewAuthServerId());
    } else {
      audit.debug("Mismatch: provided actualAuthServerId is not correct.");
      // TODO verificar que Excepción lanzar acá cuando los datos de verificación de
      // seguridad son incorrectos
      throw new HttpInternalServerException("Mismatch: provided actualAuthServerId is not correct.");
    }
    ;

    audit.debug("Saving User updated user " + user.getUserId() + ".");
    accessUser.save(user)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated User."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperUser.entityToDto(user);

  }

}
