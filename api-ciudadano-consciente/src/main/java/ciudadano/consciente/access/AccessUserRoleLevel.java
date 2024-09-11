package ciudadano.consciente.access;

import ciudadano.consciente.model.*;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessUserRoleLevel implements PanacheRepositoryBase<UserRoleLevel, Integer> {

  @Inject
  Logger audit;

  public Optional<UserRoleLevel> save(UserRoleLevel userRoleLevel) {

    audit.debug("Trying to persist UserRole in Level " + userRoleLevel.getUrlId() + ".");
    persist(userRoleLevel);
    return findByIdOptional(userRoleLevel.getUrlId());

  }

  public List<UserRoleLevel> getByLevel(Level level) {

    audit.debug("Trying to get all UserRoles in Level " + level + ".");
    return list("level", level);

  }

  public Optional<UserRoleLevel> get(Integer id) {

    audit.debug("Trying to retrieve UserRoleLevel " + id);
    return findByIdOptional(id);

  }

  public boolean exists(Integer userId, Integer roleId, Integer levelId) {

    audit.debug("Verifying if the User " + userId + " Role " + roleId + " Level " + levelId + " exists in DB.");
    Optional<UserRoleLevel> userRoleLevel = find("user.userId = ?1 and role.roleId = ?2 and level.levelId = ?3",
        userId, roleId, levelId).stream().findFirst();

    return userRoleLevel.isPresent();

  }

  public List<UserRoleLevel> getAll() {

    audit.debug("Trying to get all UserRoleLevel");
    return findAll().stream().toList();

  }

  public boolean remove(Integer id) {

    audit.debug("Trying to delete UserRoleLevel " + id + ".");
    return deleteById(id);

  }

  public Optional<UserRoleLevel> getByLevelAndUser(Integer idLevel, Integer idUser) {

    audit.debug("Trying to retrieve User(" + idUser + ") Roles in Level(" + idLevel + ").");
    return find("level.levelId = ?1 and user.userId = ?2", idLevel, idUser).firstResultOptional();

  }

  public Optional<UserRoleLevel> get(Integer idLevel, Integer idUser, Integer idRole) {

    audit.debug("Trying to retrieve User(" + idUser + ")Role(" + idRole + ")Level(" + idUser + ") " + idLevel + ".");
    return find("level.levelId = ?1 and user.userId = ?2 and role.roleId = ?3", idLevel, idUser, idRole)
        .firstResultOptional();

  }

  public List<UserRoleLevel> getByLevelAndRole(Integer idLevel, Integer idRole) {

    audit.debug("Trying to retrieve all Users with Role(" + idRole + ") in Level(" + idLevel + ").");
    return find("level.levelId = ?1 and role.roleId = ?2", idLevel, idRole).stream().toList();

  }

  public List<UserRoleLevel> getByUserAndRole(User user, Role role) {

    return find("user.userId = ?1 and role.roleId = ?2", user.getUserId(), role.getRoleId()).stream().toList();

  }

}
