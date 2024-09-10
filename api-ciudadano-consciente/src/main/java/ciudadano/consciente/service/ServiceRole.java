package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessRole;
import ciudadano.consciente.client.keycloak.service.ServiceKeycloakAPI;
import ciudadano.consciente.dto.DTORole;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.model.Role;
import ciudadano.consciente.dto.DTOUpdateRole;
import ciudadano.consciente.dto.DTOCreateRole;
import ciudadano.consciente.mapper.MapperRole;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceRole {

  @Inject
  Logger audit;

  @Inject
  UtilityVerifyRequestField utilityVerifyRequestField;

  @Inject
  MapperRole mapperRole;

  @Inject
  AccessRole accessRole;

  // @IfBuildProfile("dev")
  @Inject
  ServiceKeycloakAPI keycloak;

  public List<DTORole> getAll() {

    audit.debug("Getting all Roles.");
    return mapperRole.entityToDto(accessRole.getAll());

  }

  public DTORole get(Integer id) {

    audit.debug("Retrieving Role " + id + " .");
    Role role = accessRole.get(id)
        .orElseThrow(() -> new HttpNoContentException("Role not found."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperRole.entityToDto(role);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTORole create(DTOCreateRole dtoCreateRole) {

    // UPDATE KEYCLOAK SERVER (si no se puede actualizar, falla)
    audit.debug("Trying to create Role tru the Keycloak API.");
    if (!keycloak.createRole(dtoCreateRole.getName(),
        dtoCreateRole.getDescription())) {
      audit.error("Failed to create Roles tru the Keycloak API");
      throw new HttpInternalServerException("Failed to create Role tru the Keycloak API");
    }

    // TODO Quizás estaría bueno añadir el id del rol de Keycloak (por ahora no)
    audit.debug("Mapping DTO into EntityType");
    Role role = mapperRole.dtoToEntity(dtoCreateRole);

    audit.debug("Saving Role.");
    try {
      accessRole.save(role)
          .orElseThrow(() -> new HttpInternalServerException("Failed to persist new Role."));
    } catch (ConstraintViolationException e) {
      audit.debug("Role already exists: " + e.getErrorMessage());
      throw new HttpBadRequestException("Role already exists: " + e.getErrorMessage());
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperRole.entityToDto(role);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTORole update(Integer id, DTOUpdateRole dtoUpdateRole) {

    audit.debug("Updating Role " + id + ".");
    Role role = accessRole.get(id)
        .orElseThrow(() -> new HttpNoContentException("Role not found."));

    // UPDATE KEYCLOAK SERVER (si no se puede actualizar, falla)
    audit.debug("Trying to update Role tru the Keycloak API.");
    if (!keycloak.updateRole(role.getName(),
        dtoUpdateRole.getDescription())) {
      audit.error("Failed to update Role tru the Keycloak API");
      throw new HttpInternalServerException("Failed to update Role tru the Keycloak API.");
    }

    role.setDescription(dtoUpdateRole.getDescription());

    audit.debug("Saving Role " + role.getRoleId() + ".");
    accessRole.save(role)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated Role."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperRole.entityToDto(role);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTORole delete(Integer id) {

    audit.debug("Deleting Role " + id + ".");
    Role role = accessRole.get(id)
        .orElseThrow(() -> new HttpNoContentException("Role not found."));

    // UPDATE KEYCLOAK SERVER (si no se puede actualizar, falla)
    audit.debug("Trying to delete Role tru the Keycloak API.");
    if (!keycloak.deleteRole(role.getName())) {
      audit.error("Failed to delete Role tru the Keycloak API");
      throw new HttpInternalServerException("Failed to delete Role tru the Keycloak API.");
    }

    if (!accessRole.remove(role.getRoleId())) {
      throw new HttpInternalServerException("Failed to dalete Role.");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperRole.entityToDto(role);

  }

}
