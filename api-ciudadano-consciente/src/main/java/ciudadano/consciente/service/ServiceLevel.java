package ciudadano.consciente.service;

import ciudadano.consciente.access.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.mapper.*;
import ciudadano.consciente.model.*;
import ciudadano.consciente.dto.*;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

import java.sql.Date;
import java.time.OffsetDateTime;
import java.util.*;

@RequestScoped
public class ServiceLevel {

  final String ENTITY_NAME = "Level";

  @Inject
  UtilityVerifyRequestField utilityVerifyRequestField;

  @Inject
  AccessLevel accessLevel;

  @Inject
  AccessOrganization accessOrganization;

  @Inject
  MapperLevel mapperLevel;

  @Inject
  AccessUser accessUser;

  @Inject
  AccessRole accessRole;

  @Inject
  MapperUserRoleLevel mapperUserRoleLevel;

  @Inject
  AccessUserRoleLevel accessUserRoleLevel;

  @Inject
  Logger audit;

  @Inject
  AccessVote accessVote;

  @Inject
  MapperVote mapperVote;

  @Inject
  AccessEntityType accessEntityType;

  @Inject
  AccessVotedLevel accessVotedLevel;

  @Inject
  MapperVotedEntity mapperVotedEntity;
  
  @Inject
  AccessTaggedLevel accessTaggedLevel;
  
  @Inject
  MapperTaggedEntity mapperTaggedEntity;

  @Inject
  AccessUserRoleOrganization accessUserRoleOrganization;

  public List<DTOLevel> getAll() {

    audit.debug("Getting all Levels.");
    return mapperLevel.entityToDto(accessLevel.getAll());

  }

  public DTOLevel get(Integer id) {

    audit.debug("Getting Level " + id + ".");
    Level level = accessLevel.get(id)
        .orElseThrow(() -> new HttpNoContentException("Level not found."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperLevel.entityToDto(level);

  }

  public List<DTOLevelWithChildrens> getChildrens(Integer id) {

    audit.debug("Getting Level " + id + ".");
    Level level = accessLevel.get(id)
        .orElseThrow(() -> new HttpNoContentException("Parent Level not found."));

    audit.debug("Getting childrens of Levels.");
    return mapperLevel.entityToLevelWithChildrensDto(accessLevel.getAllChildrens(level.getLevelId()));

  }

  public List<DTOLevelPathWithVotes> getAllPaths() {

    audit.debug("Retrieving entityType");
    EntityType entityType = accessEntityType.getByName(ENTITY_NAME)
        .orElseThrow(() -> new HttpNoContentException("EntityType not found"));

    audit.debug("Getting all Paths.");
    List<DTOLevelPathWithVotes> paths = mapperLevel.entityToPathWithVotesDto(accessLevel.getAllPaths());

    audit.debug("Getting all votes of entityType");
    Map<Integer, Integer> votes = accessVote.getMostVotedEntitiesByEntityType(entityType.getEntityTypeId());

    // If the path has votes, we set it. Otherwise, default is 0 (zero)
    for (DTOLevelPathWithVotes path : paths) {
      path.setVotes(votes.getOrDefault(path.getLevelId(), 0));
    }

    return paths;

  }

  public List<DTOLevelPathWithVotes> getPathsByOrganization(Integer id) {

    audit.debug("Getting Organization " + id + ".");
    Organization organization = accessOrganization.get(id)
        .orElseThrow(() -> new HttpNoContentException("Organization not found."));

    audit.debug("Getting paths of Organization.");
    List<DTOLevelPathWithVotes> paths = mapperLevel
        .entityToPathWithVotesDto(accessLevel.getAllPathsByOrganization(organization));

    audit.debug("Retrieving entityType");
    EntityType entityType = accessEntityType.getByName(ENTITY_NAME)
        .orElseThrow(() -> new HttpNoContentException("EntityType not found"));

    audit.debug("Getting all votes of entityType");
    Map<Integer, Integer> votes = accessVote.getMostVotedEntitiesByEntityType(entityType.getEntityTypeId());

    // If the path has votes, we set it. Otherwise, default is 0 (zero)
    for (DTOLevelPathWithVotes path : paths) {
      path.setVotes(votes.getOrDefault(path.getLevelId(), 0));
    }

    return paths;

  }

  public List<DTOLevelPath> getPathsByUserFavorite(Integer userId) {

    audit.debug("Getting User " + userId + ".");
    User user = accessUser.get(userId)
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    audit.debug("Retrieving entityType");
    EntityType entityType = accessEntityType.getByName(ENTITY_NAME)
        .orElseThrow(() -> new HttpNoContentException("EntityType not found"));

    audit.debug("Getting all votes of entityType by user");
    List<Vote> votes = accessVote.getByEntityTypeAndUser(entityType, user);

    // Recuperar levels de votos
    audit.debug("Retrieve levels of votes.");
    List<Level> paths = new ArrayList<>();
    for (Vote vote : votes) {

      Level level = accessLevel.get(vote.getEntity()).orElseThrow(() -> new HttpNoContentException("Level not found."));
      // Filtrar levels sin parent
      if (level.getParent() == null) {
        paths.add(level);
      }

    }

    return mapperLevel.entityToPathDto(paths);

  }

  public List<DTOLevelPathUsedRecentlyByUser> getPathsUsedByUserRecently(Integer userId) {

    audit.debug("Getting User " + userId + ".");
    User user = accessUser.get(userId)
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    audit.debug("Retrieving Paths recently used by User.");
    List<Object[]> levels = accessLevel.getRecentlyUsedByUser(user);

    audit.debug("Mapping Result into DTO");
    List<DTOLevelPathUsedRecentlyByUser> paths = new ArrayList<>();
    for (Object[] level : levels) {
      DTOLevelPathUsedRecentlyByUser path = new DTOLevelPathUsedRecentlyByUser();
      path.setLevelId((Integer) level[0]);
      path.setName((String) level[1]);
      path.setDescription((String) level[2]);
      path.setOrganization((Integer) level[3]);
      path.setCreated((OffsetDateTime) level[4]);
      paths.add(path);
    }

    return mapperLevel.entityToRecentlyUsedPathDto(paths);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOLevel create(DTOCreateLevel dtoCreateLevel) {

    String name = dtoCreateLevel.getName();
    if (accessLevel.existName(name)) { // TODO Se podría agregar un alias que no sea único, que se usaría en la app.
      throw new HttpBadRequestException("The name already exists.");
    }

    Integer organizationDto = dtoCreateLevel.getOrganization();
    Organization organization = accessOrganization.get(organizationDto)
        .orElseThrow(() -> new HttpNoContentException("Organization not found."));

    audit.debug("Creating Level.");
    Level level = mapperLevel.dtoToEntity(name, organization);

    Integer parent = dtoCreateLevel.getParent();
    if (utilityVerifyRequestField.isValidField(parent)) {
      level.setParent(accessLevel.get(parent)
          .orElse(null));
    }

    String description = dtoCreateLevel.getDescription();
    if (utilityVerifyRequestField.isValidField(description)) {
      level.setDescription(dtoCreateLevel.getDescription());
    }

    audit.debug("Saving Level " + level.getLevelId() + ".");
    accessLevel.save(level)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist new Level."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperLevel.entityToDto(level);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOLevel update(Integer id, DTOUpdateLevel dtoUpdateLevel) {

    audit.debug("Updating Level " + id + ".");
    String name = dtoUpdateLevel.getName();
    Integer organization = dtoUpdateLevel.getOrganization();
    Integer parent = dtoUpdateLevel.getParent();
    String description = dtoUpdateLevel.getDescription();

    Level level = accessLevel.get(id)
        .orElseThrow(() -> new HttpNoContentException("Level not found."));

    if (utilityVerifyRequestField.isValidField(name)) {
      if (accessLevel.existName(name)) {
        throw new HttpBadRequestException("The name already exists.");
      }
      level.setName(name);
    }

    if (utilityVerifyRequestField.isValidField(organization)) {
      Organization organizationEntity = accessOrganization.get(dtoUpdateLevel.getOrganization())
          .orElseThrow(() -> new HttpNoContentException("Organization not found."));
      level.setOrganization(organizationEntity);
    }

    if (utilityVerifyRequestField.isValidField(parent)) {
      level.setParent(accessLevel.get(parent)
          .orElseThrow(() -> new HttpNoContentException("Parent Level not found.")));
    }

    if (utilityVerifyRequestField.isValidField(description)) {
      level.setDescription(description);
    }

    audit.debug("Saving Level " + level.getLevelId() + ".");
    accessLevel.save(level)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated Level."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperLevel.entityToDto(level);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOLevel delete(Integer id) {

    audit.debug("Deleting Level " + id + ".");
    Level level = accessLevel.get(id)
        .orElseThrow(() -> new HttpNoContentException("Level not found."));

    if (!accessLevel.remove(level.getLevelId())) {
      throw new HttpInternalServerException("Failed to delete Level");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperLevel.entityToDto(level);

  }

  // ROLE HANDLING IN LEVEL

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOUserRoleLevel assignRoleToUserInLevel(Integer idLevel, Integer idUser, Integer idRole) {

    audit.debug("Verify if UserRoleLevel already exists.");
    if (accessUserRoleLevel.get(idLevel, idUser, idRole).isPresent()) {
      throw new HttpBadRequestException("UserRoleLevel already exists.");
    }
    
    User user = accessUser.get(idUser)
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    Level level = accessLevel.get(idLevel)
        .orElseThrow(() -> new HttpNoContentException("Level not found."));

    Role role = accessRole.get(idRole)
        .orElseThrow(() -> new HttpNoContentException("Role not found."));
    
    audit.debug("Creating Role of User in Level.");
    UserRoleLevel userRoleLevel = new UserRoleLevel();
    userRoleLevel.setUser(user);
    userRoleLevel.setLevel(level);
    userRoleLevel.setRole(role);

    audit.debug("Saving UserRoleLevel " + userRoleLevel.getUrlId() + ".");
    try {
      accessUserRoleLevel.save(userRoleLevel)
              .orElseThrow(() -> new HttpInternalServerException("Failed to persist new UserRoleLevel."));
    } catch (ConstraintViolationException e) {
      audit.debug("Already exists Role for User in Level: " + e.getErrorMessage());
      throw new HttpBadRequestException("Already exists Role for User in Level: " + e.getErrorMessage());
    }
    
    audit.debug("Mapping EntityType into DTO.");
    return mapperUserRoleLevel.entityToDto(userRoleLevel);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOUserRoleLevel updateRoleOfUserInLevel(Integer idLevel, Integer idUser, Integer newRoleId) {

    audit.debug("Verifying if User exists.");
    User user = accessUser.get(idUser)
            .orElseThrow( ()-> new HttpNoContentException("User not found.") );

    audit.debug("Verifying if new Role exists.");
    Role newRole = accessRole.get(newRoleId)
            .orElseThrow(() -> new HttpNoContentException("Role not found."));

    audit.debug("Verifying if new Level exists.");
    Level level = accessLevel.get(idLevel)
            .orElseThrow(() -> new HttpNoContentException("Level not found."));

    audit.debug("Verifying if UserRoleLevel original exists.");
    Optional<UserRoleLevel> userRoleLevel = accessUserRoleLevel.getByLevelAndUser(level.getLevelId(), user.getUserId());
    if(userRoleLevel.isEmpty()) {
      audit.debug("User " + user.getUserId() + " does not have Role in Level " + idLevel);
      throw new HttpNoContentException("UserRoleLevel not found.");
    }

    if(userRoleLevel.get().getRole().equals(newRole)) {
      audit.debug("User " + user.getUserId() + " already has Role " + newRole.getRoleId() + " in Level " + level.getLevelId() + ".");
    } else {
      audit.debug("Updating Rol of User in Level: from " + userRoleLevel.get().getRole().getRoleId() + " to " + newRole.getRoleId());
      userRoleLevel.get().setRole(newRole);
      audit.debug("Saving updated UserRoleLevel " + userRoleLevel.get().getUrlId() + ".");
      accessUserRoleLevel.save(userRoleLevel.get())
              .orElseThrow(() -> new HttpNoContentException("Failed to update Role of User in Level."));
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperUserRoleLevel.entityToDto(userRoleLevel.get());
    
//    audit.debug("Verifying if UserRoleLevel intended exists.");
//    if (accessUserRoleLevel.get(idLevel, idUser, newRole).isPresent()) {
//      throw new HttpBadRequestException("UserRoleLevel already exists.");
//    }
//
//    audit.debug("Verifying if new Role exists.");
//    Role role = accessRole.get(newRole)
//            .orElseThrow(() -> new HttpNoContentException("Role not found."));
//
//    audit.debug("Verifying if UserRoleLevel original exists.");
//    UserRoleLevel userRoleLevel = accessUserRoleLevel.get(idLevel, idUser, idRole)
//            .orElseThrow(() -> new HttpNoContentException("UserRoleLevel not found."));
//
//    audit.debug("Upating Rol of User in Level: from " + idRole + " to " + newRole);
//    userRoleLevel.setRole(role);
//
//    audit.debug("Saving updated UserRoleLevel " + userRoleLevel.getUrlId() + ".");
//    accessUserRoleLevel.save(userRoleLevel)
//            .orElseThrow(() -> new HttpNoContentException("Failed to update Role of User in Level."));
//
//    audit.debug("Mapping EntityType into DTO.");
//    return mapperUserRoleLevel.entityToDto(userRoleLevel);

  }

  @Deprecated
  public List<DTOUserRoleLevel> getUserRoleLevel(Integer id) {

    audit.debug("Getting Level " + id + ".");
    Level level = accessLevel.get(id)
        .orElseThrow(() -> new HttpNoContentException("Level not found."));

    audit.debug("Retrieving all UserRole of Level " + level.getLevelId() + ".");
    List<UserRoleLevel> userRoleLevel = accessUserRoleLevel.getByLevel(level);

    if (userRoleLevel.isEmpty()) {
      throw new HttpNoContentException("No Roles assigned to User in Level.");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperUserRoleLevel.entityToDto(userRoleLevel);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOUserRoleLevel deleteRoleOfUserInLevel(Integer idLevel, Integer idUser) {

    Level level = accessLevel.get(idLevel)
                    .orElseThrow( ()-> new HttpNoContentException("Level not found.") );
    
    User user = accessUser.get(idUser)
                    .orElseThrow( ()-> new HttpNoContentException("User not found.") );
    
    audit.debug("Retrieving UserRoleLevel.");
    UserRoleLevel userRoleLevel = accessUserRoleLevel.getByLevelAndUser(level.getLevelId(),
                    user.getUserId())
            .orElseThrow( ()-> new HttpNoContentException("User don't have Roles in Level.") );

    audit.debug("Deleting Role " + userRoleLevel.getRole().getRoleId()
            + " of User " + idUser + " from Level " + idLevel + ".");
    accessUserRoleLevel.remove(userRoleLevel.getUrlId());

    audit.debug("Mapping EntityType into DTO.");
    return mapperUserRoleLevel.entityToDto(userRoleLevel);

  }

  public DTOUserRoleLevel getUserRoleLevel(Integer idLevel, Integer idUser, Integer idRole) {

    audit.debug("Retrieving UserRoleLevel");
    UserRoleLevel userRoleLevel = accessUserRoleLevel.get(idLevel, idUser, idRole)
        .orElseThrow(() -> new HttpNoContentException("UserRoleLevel not found"));

    audit.debug("Mapping EntityType into DTO.");
    return mapperUserRoleLevel.entityToDto(userRoleLevel);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOUserRoleLevel deleteUserRoleLevel(Integer idLevel, Integer idUser, Integer idRole) {

    audit.debug("Deleting User(" + idUser + ")Role(" + idRole + ")Level(" + idLevel + ".");
    UserRoleLevel userRoleLevel = accessUserRoleLevel.get(idLevel, idUser, idRole)
        .orElseThrow(() -> new HttpNoContentException("UserRoleLevel not found."));

    if (!accessUserRoleLevel.remove(userRoleLevel.getUrlId())) {
      throw new HttpInternalServerException("Failed to remove UserRoleLevel.");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperUserRoleLevel.entityToDto(userRoleLevel);

  }

  public DTOUserRoleLevel getRoleInLevelByUser(Integer idLevel, Integer idUser) {

    audit.debug("Retrieving Role of User(" + idUser + ") in Level(" + idLevel + ").");
    UserRoleLevel userRoleLevel = accessUserRoleLevel.getByLevelAndUser(idLevel, idUser)
            .orElseThrow( ()-> new HttpNoContentException("User don't have Roles in Level."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperUserRoleLevel.entityToDto(userRoleLevel);

  }

  public List<DTOUserRoleLevel> getAllUsersWithRoleByLevel(Integer idLevel) {

    Level level = accessLevel.get(idLevel)
        .orElseThrow(() -> new HttpNoContentException("Level not found."));

    audit.debug("Retrieving all Users with Roles in Level(" + idLevel + ").");
    List<UserRoleLevel> userRoleLevelList = accessUserRoleLevel.getByLevel(level);

    if (userRoleLevelList.isEmpty()) {
      throw new HttpNoContentException("Level without Roles assigned.");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperUserRoleLevel.entityToDto(userRoleLevelList);

  }

  public List<DTOUserRoleLevel> getAllUsersWithRoleInLevel(Integer idLevel, Integer idRole) {

    audit.debug("Retrieving all Users with Role(" + idRole + ") in Level(" + idLevel + ").");
    List<UserRoleLevel> userRoleLevelList = accessUserRoleLevel.getByLevelAndRole(idLevel, idRole);

    if (userRoleLevelList.isEmpty()) {
      throw new HttpNoContentException("Role don't have Users in Level.");
    }

    audit.debug("Mapping EntityType into DTO.");
    return mapperUserRoleLevel.entityToDto(userRoleLevelList);

  }

  // LEVEL HANDLING IN LEVEL
  @Deprecated(since = "1.0.1")
  @Transactional(Transactional.TxType.REQUIRED)
  public DTOVote vote(Integer idLevel, Integer idUser) {

    audit.debug("Retrieving Entity Type");
    EntityType entityType = accessEntityType.getByName(ENTITY_NAME)
        .orElseThrow(() -> new HttpNoContentException("Entity Type not found."));

    Level level = accessLevel.get(idLevel)
        .orElseThrow(() -> new HttpNoContentException("Level not found."));

    User user = accessUser.get(idUser)
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    audit.debug("Verify if Vote already exists.");
    if (accessVote.getByKeys(user, level.getLevelId(), entityType).isPresent()) {
      throw new HttpBadRequestException("Vote already exists.");
    }

    audit.debug("Creating Vote for Level.");
    Vote vote = new Vote(user, level.getLevelId(), entityType);

    audit.debug("Saving Vote.");
    accessVote.save(vote)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist new Vote."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperVote.entityToDto(vote);

  }

  public List<DTOLevel> getLevelsByOrganizationUserAndRole(Integer organizationId, Integer userId, Integer roleId) {

    Organization organization = accessOrganization.get(organizationId)
            .orElseThrow( ()-> new HttpNoContentException("Organization not found.") );

    User user = accessUser.get(userId)
            .orElseThrow( ()-> new HttpNoContentException("User not found.") );

    Role role = accessRole.get(roleId)
            .orElseThrow( ()-> new HttpNoContentException("Role not found.") );

    List<UserRoleLevel> userRoleLevel = accessUserRoleLevel.getByUserAndRole(user, role);

    List<Level> levels = userRoleLevel.stream()
            .map(UserRoleLevel::getLevel)
            .filter(level -> level.getOrganization().equals(organization))
            .toList();

    return mapperLevel.entityToDto(levels);

  }

  public List<DTOVotedEntity> getAllVotes() {

    audit.debug("Retrieving all votes from Levels.");
    return mapperVotedEntity.votedLevelEntityToDto((accessVotedLevel.getAllVotes()));

  }

  public List<DTOVotedEntity> getVotes(Integer id) {

    Level level = accessLevel.get(id)
            .orElseThrow(() -> new HttpNoContentException("Level not found."));

    return mapperVotedEntity.votedLevelEntityToDto(accessVotedLevel.getVotes(level));

  }

  public List<DTOTaggedEntity> getAllTags() {

    audit.debug("Retrieving all tags from Levels.");
    return mapperTaggedEntity.taggedLevelEntityToDto((accessTaggedLevel.getAllTags()));

  }

  public List<DTOTaggedEntity> getTags(Integer id) {

    Level level = accessLevel.get(id)
            .orElseThrow(() -> new HttpNoContentException("Level not found."));

    return mapperTaggedEntity.taggedLevelEntityToDto(accessTaggedLevel.getTags(level));

  }  

}
