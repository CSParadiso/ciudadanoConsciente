package ciudadano.consciente.service;

import ciudadano.consciente.access.*;
import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.mapper.*;
import ciudadano.consciente.model.*;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class ServiceOrganization {

  final String ENTITY_NAME = "Organization";

  @Inject
  UtilityVerifyRequestField utilityVerifyRequestField;

  @Inject
  AccessOrganization accessOrganization;

  @Inject
  AccessUser accessUser;

  @Inject
  AccessRole accessRole;

  @Inject
  AccessUserRoleOrganization accessUserRoleOrganization;

  @Inject
  MapperOrganization mapperOrganization;

  @Inject
  MapperUserRoleOrganization mapperUserRoleOrganization;

  @Inject
  Logger audit;

  @Inject
  AccessVote accessVote;

  @Inject
  AccessEntityType accessEntityType;

  @Inject
  MapperVote mapperVote;

  @Inject
  MapperVotedEntity mapperVotedEntity;

  @Inject
  AccessVotedOrganization accessVotedOrganization;

  @Inject
  AccessTaggedOrganization accessTaggedOrganization;

  @Inject
  MapperTaggedEntity mapperTaggedEntity;

  public List<DTOOrganization> getAll() {

    audit.debug("Getting all Organizations.");
    List<Organization> organizations = accessOrganization.getAll();
    return mapperOrganization.entityToDto(organizations);

  }

  public DTOOrganization get(Integer id) {

    audit.debug("Getting Oganization " + id + ".");
    Organization organization = accessOrganization.get(id)
        .orElseThrow(() -> new HttpNoContentException("Organization not found."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperOrganization.entityToDto(organization);

  }

  public List<DTOOrganization> getOrganizationsByUser(Integer userId) {

    audit.debug("Retrieving Organization by User " + userId);
    User user = accessUser.get(userId)
            .orElseThrow( ()-> new HttpNoContentException("User not found.") );

    List<UserRolOrganization> userRolOrganizations = accessUserRoleOrganization.getByUser(user);

    List<Organization> organizations = userRolOrganizations.stream()
            .map(UserRolOrganization::getOrganization)
            .filter(organization -> accessOrganization.get(organization.getOrganizationId()).isPresent())
            .distinct()
            .toList();

    return mapperOrganization.entityToDto(organizations);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOOrganization create(DTOCreateOrganization dtoCreateOrganization) {

    audit.debug("Creating Organization.");
    String email = dtoCreateOrganization.getEmail();
    String name = dtoCreateOrganization.getName();

    if (accessOrganization.existEmail(email)) {
      throw new HttpBadRequestException("Email already exists.");
    }

    if (accessOrganization.existName(name)) {
      throw new HttpBadRequestException("Name already exists.");
    }

    audit.debug("Mapping DTO into EntityType.");
    Organization organization = mapperOrganization.dtoToEntity(email, name);

    // TODO Quizás se pueda asignar directamente el DTO en las creaciones
    String description = dtoCreateOrganization.getDescription();
    if (utilityVerifyRequestField.isValidField(description)) {
      organization.setDescription(description);
    }

    audit.debug("Saving Organization " + organization.getOrganizationId() + ".");
    accessOrganization.save(organization)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist new Organization."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperOrganization.entityToDto(organization);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOOrganization update(Integer id, DTOUpdateOrganization dtoUpdateOrganization) {

    audit.debug("Updating Organization " + id + ".");
    Organization organization = accessOrganization.get(id)
        .orElseThrow(() -> new HttpNoContentException("Organization not found"));

    String email = dtoUpdateOrganization.getEmail();
    String name = dtoUpdateOrganization.getName();
    String description = dtoUpdateOrganization.getDescription();
    if (utilityVerifyRequestField.isValidField(name)) {
      if (accessOrganization.existName(name)) {
        throw new HttpBadRequestException("The name already exists.");
      }
      organization.setName(name);
    }

    if (utilityVerifyRequestField.isValidField(email)) {
      if (accessOrganization.existEmail(email)) {
        throw new HttpBadRequestException("The email already exists.");
      }
      organization.setEmail(email);
    }

    if (utilityVerifyRequestField.isValidField(description)) {
      organization.setDescription(description);
    }

    audit.debug("Saving Organization " + organization.getOrganizationId() + ".");
    organization = accessOrganization.save(organization)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated Organization."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperOrganization.entityToDto(organization);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOOrganization delete(Integer id) {

    audit.debug("Deleting Organization " + id);
    Organization organization = accessOrganization.get(id)
        .orElseThrow(() -> new HttpNoContentException("Organization not found"));

    if (!accessOrganization.remove(organization.getOrganizationId())) {
      throw new HttpInternalServerException("Failed to delete Organization.");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperOrganization.entityToDto(organization);

  }

  @Deprecated
  @Transactional(Transactional.TxType.REQUIRED)
  public DTOUserRoleOrganization assignRole(DTOAssingRoleToUserOrganization dtoAssingRoleToUserOrganization) {

    audit.debug("Assigning Role to User in Organization.");
    Integer user = dtoAssingRoleToUserOrganization.getUser();
    Integer organization = dtoAssingRoleToUserOrganization.getOrganization();
    Integer role = dtoAssingRoleToUserOrganization.getRole();
    if (!utilityVerifyRequestField.isValidField(user) ||
        !utilityVerifyRequestField.isValidField(organization) ||
        !utilityVerifyRequestField.isValidField(role)) {
      throw new HttpBadRequestException("All fields required.");
    }

    audit.debug("Creating Role of User in Organization.");
    UserRolOrganization userRolOrganization = new UserRolOrganization();

    userRolOrganization.setUser(accessUser.get(user)
        .orElseThrow(() -> new HttpNoContentException("User not found.")));

    userRolOrganization.setOrganization(accessOrganization.get(organization)
        .orElseThrow(() -> new HttpNoContentException("Organization not found.")));

    userRolOrganization.setRole(accessRole.get(role)
        .orElseThrow(() -> new HttpNoContentException("Role not found.")));

    audit.debug("Saving UserRoleOrganization " + userRolOrganization.getUroId() + ".");
    try {
      accessUserRoleOrganization.save(userRolOrganization)
          .orElseThrow(() -> new HttpInternalServerException("Failed to persist UserRoleLevel."));
    } catch (ConstraintViolationException e) {
      throw new HttpBadRequestException("Already exists Role for User in Organization: " + e.getErrorMessage());
    }


    audit.debug("Mapping EntityType into DTO.");
    return mapperUserRoleOrganization.entityToDto(userRolOrganization);

  }

  public List<DTOUserRoleOrganization> getUserRoleOrganization(Integer id) {

    audit.debug("Getting Organization " + id + ".");
    Organization organization = accessOrganization.get(id)
        .orElseThrow(() -> new HttpNoContentException("Organization not found."));

    audit.debug("Retrieving all UserRole of Organization " + organization.getOrganizationId() + ".");
    List<UserRolOrganization> userRolOrganizations = accessUserRoleOrganization.getByOrganization(organization);

    if (userRolOrganizations.isEmpty()) {
      throw new HttpNoContentException("No Roles assigned to User in Organization.");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperUserRoleOrganization.entityToDto(userRolOrganizations);

  }

  public List<DTOUserRoleOrganization> getAllUsersWithRoleByOrganization(Integer idOrganization) {

    Organization organization = accessOrganization.get(idOrganization)
        .orElseThrow(() -> new HttpNoContentException("Organization not found."));

    audit.debug("Retrieving all Users with Roles in Organization(" + idOrganization + ").");
    List<UserRolOrganization> userRoleOrganizationList = accessUserRoleOrganization.getByOrganization(organization);

    if (userRoleOrganizationList.isEmpty()) {
      throw new HttpNoContentException("Organization without Roles assigned.");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperUserRoleOrganization.entityToDto(userRoleOrganizationList);

  }

  public DTOUserRoleOrganization getRoleInOrganizationByUser(Integer idOrganization, Integer idUser) {

    audit.debug("Retrieving Role of User(" + idUser + ") in Organization(" + idOrganization + ").");
    UserRolOrganization userRoleOrganization = accessUserRoleOrganization.getByOrganizationAndUser(idOrganization, idUser)
            .orElseThrow( ()-> new HttpNoContentException("User don't have Roles in Organization."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperUserRoleOrganization.entityToDto(userRoleOrganization);

  }

  public List<DTOUserRoleOrganization> getAllUsersWithRoleInOrganization(Integer idOrganization, Integer idRole) {

    audit.debug("Retrieving all Users with Role(" + idRole + ") in Organization(" + idOrganization + ").");
    List<UserRolOrganization> userRoleOrganizationList = accessUserRoleOrganization
        .getByOrganizationAndRole(idOrganization, idRole);

    if (userRoleOrganizationList.isEmpty()) {
      throw new HttpNoContentException("Role don't have Users in Organization.");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperUserRoleOrganization.entityToDto(userRoleOrganizationList);

  }

  public DTOUserRoleOrganization getUserRoleOrganization(Integer idOrganization, Integer idUser, Integer idRole) {

    audit.debug("Retrieving UserRoleOrganization");
    UserRolOrganization userRoleorganization = accessUserRoleOrganization.get(idOrganization, idUser, idRole)
        .orElseThrow(() -> new HttpNoContentException("UserRoleOrganization not found"));

    audit.debug("Mapping EntityType into DTO.");
    return mapperUserRoleOrganization.entityToDto(userRoleorganization);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOUserRoleOrganization assignRoleToUserInOrganization(Integer idOrganization, Integer idUser,
      Integer idRole) {

    audit.debug("Verify if UserRoleOrganization already exists.");
    if (accessUserRoleOrganization.get(idOrganization, idUser, idRole).isPresent()) {
      throw new HttpBadRequestException("UserRoleOrganization already exists.");
    }

    User user = accessUser.get(idUser)
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    Organization organization = accessOrganization.get(idOrganization)
        .orElseThrow(() -> new HttpNoContentException("Organization not found."));

    Role role = accessRole.get(idRole)
        .orElseThrow(() -> new HttpNoContentException("Role not found."));

    audit.debug("Creating Role of User in Organization.");
    UserRolOrganization userRoleOrganization = new UserRolOrganization();
    userRoleOrganization.setUser(user);
    userRoleOrganization.setOrganization(organization);
    userRoleOrganization.setRole(role);

    audit.debug("Saving UserRoleOrganization " + userRoleOrganization.getUroId() + ".");
    try {
      accessUserRoleOrganization.save(userRoleOrganization)
              .orElseThrow(() -> new HttpInternalServerException("Failed to persist UserRoleLevel."));
    } catch (ConstraintViolationException e) {
      audit.debug("Already exists Role for User in Organization: " + e.getErrorMessage());
      throw new HttpBadRequestException("Already exists Role for User in Organization: " + e.getErrorMessage());
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperUserRoleOrganization.entityToDto(userRoleOrganization);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOUserRoleOrganization updateRoleOfUserInOrganization(Integer idOrganization, Integer idUser, Integer newRoleId) {

    audit.debug("Verifying if User exists.");
    User user = accessUser.get(idUser)
            .orElseThrow( ()-> new HttpNoContentException("User not found.") );

    audit.debug("Verifying if new Role exists.");
    Role newRole = accessRole.get(newRoleId)
        .orElseThrow(() -> new HttpNoContentException("Role not found."));

    audit.debug("Verifying if new Organization exists.");
    Organization organization = accessOrganization.get(idOrganization)
            .orElseThrow(() -> new HttpNoContentException("Organization not found."));

    audit.debug("Verifying if UserRoleOrganization original exists.");
    Optional<UserRolOrganization> userRoleOrganization = accessUserRoleOrganization.getByOrganizationAndUser(organization.getOrganizationId(), user.getUserId());
    if(userRoleOrganization.isEmpty()) {
      audit.debug("User " + user.getUserId() + " does not have Role in Organization " + idOrganization);
      throw new HttpNoContentException("UserRoleOrganization not found.");
    }

    if(userRoleOrganization.get().getRole().equals(newRole)) {
      audit.debug("User " + user.getUserId() + " already has Role " + newRole.getRoleId() + " in Organization " + organization.getOrganizationId() + ".");
    } else {
      audit.debug("Updating Rol of User in Organization: from " + userRoleOrganization.get().getRole().getRoleId() + " to " + newRole.getRoleId());
      userRoleOrganization.get().setRole(newRole);
      audit.debug("Saving updated UserRoleOrganization " + userRoleOrganization.get().getUroId() + ".");
      accessUserRoleOrganization.save(userRoleOrganization.get())
              .orElseThrow(() -> new HttpNoContentException("Failed to update Role of User in Organization."));
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperUserRoleOrganization.entityToDto(userRoleOrganization.get());

  }

  @Deprecated(since = "1.0.3 Users should hace only one Role by Organization")
  @Transactional(Transactional.TxType.REQUIRED)
  public DTOUserRoleOrganization deleteRoleOfUserInOrganization(Integer idOrganization, Integer idUser) {

    Organization organization = accessOrganization.get(idOrganization)
                    .orElseThrow( ()-> new HttpNoContentException("Organization not found.") );

    User user = accessUser.get(idUser)
            .orElseThrow( ()-> new HttpNoContentException("User not found.") );

    audit.debug("Retrieving UserRoleOrganization.");
    UserRolOrganization userRoleOrganization = accessUserRoleOrganization.getByOrganizationAndUser(organization.getOrganizationId(),
                    user.getUserId())
            .orElseThrow( ()-> new HttpNoContentException("User don't have Roles in Organization.") );

    audit.debug("Deleting Role " + userRoleOrganization.getRole().getRoleId()
            + " of User " + idUser + " from Organization " + idOrganization + ".");
    accessUserRoleOrganization.remove(userRoleOrganization.getUroId());

    audit.debug("Mapping EntityType into DTO.");
    return mapperUserRoleOrganization.entityToDto(userRoleOrganization);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOUserRoleOrganization deleteUserRoleOrganization(Integer idOrganization, Integer idUser, Integer idRole) {

    audit.debug("Deleting User(" + idUser + ")Role(" + idRole + ")Organization(" + idOrganization + ".");
    UserRolOrganization userRoleOrganization = accessUserRoleOrganization.get(idOrganization, idUser, idRole)
        .orElseThrow(() -> new HttpNoContentException("UserRoleOrganization not found."));

    if (!accessUserRoleOrganization.remove(userRoleOrganization.getUroId())) {
      throw new HttpInternalServerException("Failed to remove UserRoleOrganization.");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperUserRoleOrganization.entityToDto(userRoleOrganization);

  }

  // HANDLING VOTES IN ORGANIZATION
  @Deprecated(since = "1.0.1")
  @Transactional(Transactional.TxType.REQUIRED)
  public DTOVote vote(Integer idOrganization, Integer idUser) {

    audit.debug("Retrieving Entity Type");
    EntityType entityType = accessEntityType.getByName(ENTITY_NAME)
        .orElseThrow(() -> new HttpNoContentException("Entity Type not found."));

    Organization organization = accessOrganization.get(idOrganization)
        .orElseThrow(() -> new HttpNoContentException("Organization not found."));

    User user = accessUser.get(idUser)
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    audit.debug("Verify if Vote already exists.");
    if (accessVote.getByKeys(user, organization.getOrganizationId(), entityType).isPresent()) {
      throw new HttpBadRequestException("Vote already exists.");
    }

    audit.debug("Creating Vote for Organization.");
    Vote vote = new Vote(user, organization.getOrganizationId(), entityType);

    audit.debug("Saving Vote.");
    accessVote.save(vote)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist new Vote."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperVote.entityToDto(vote);

  }

  public List<DTOVotedEntity> getAllVotes() {

    audit.debug("Retrieving all votes from Organizations.");
    return mapperVotedEntity.votedOrganizationEntityToDto((accessVotedOrganization.getAllVotes()));

  }

  public List<DTOVotedEntity> getVotes(Integer id) {

    Organization organization = accessOrganization.get(id)
            .orElseThrow(() -> new HttpNoContentException("Organization not found."));

    return mapperVotedEntity.votedOrganizationEntityToDto(accessVotedOrganization.getVotes(organization));

  }

  public List<DTOTaggedEntity> getAllTags() {

    audit.debug("Retrieving all tags from Organizations.");
    return mapperTaggedEntity.taggedOrganizationEntityToDto((accessTaggedOrganization.getAllTags()));

  }

  public List<DTOTaggedEntity> getTags(Integer id) {

    Organization organization = accessOrganization.get(id)
            .orElseThrow(() -> new HttpNoContentException("Organization not found."));

    return mapperTaggedEntity.taggedOrganizationEntityToDto(accessTaggedOrganization.getTags(organization));

  }  
  
}
