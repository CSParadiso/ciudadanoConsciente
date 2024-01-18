package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessUser;
import ciudadano.consciente.access.AccessVote;
import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.mapper.MapperUser;
import ciudadano.consciente.mapper.MapperVote;
import ciudadano.consciente.model.EntityType;
import ciudadano.consciente.model.User;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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

    public DTOUser get(Integer id) {

        audit.debug("Retrieving User " + id + ".");
        User user = accessUser.get(id)
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

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOUser create(DTOCreateUser dtoCreateUser) {

        audit.debug("Creating new User.");
        String email = dtoCreateUser.getEmail();
        String username = dtoCreateUser.getUsername();
        if(accessUser.existsEmail(email)) {
            throw new HttpBadRequestException("Email already exists.");
        }

        if(accessUser.existsUsername(username)) {
            throw new HttpBadRequestException("Username already exists.");
        }

        audit.debug("Mapping DTO into EntityType.");
        User user = mapperUser.dtoToEntity(dtoCreateUser);

        audit.debug("Saving User " + user.getUserId() + ".");
        accessUser.save(user)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new User."));

        audit.debug("Mapping EntityType into DTO.");
        return mapperUser.entityToDto(user);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOUser update(Integer id, DTOUpdateUser dtoUpdateUser) {

        audit.debug("Updating User " + id + ".");
        User user = accessUser.get(id)
                .orElseThrow( ()-> new HttpNoContentException("User not found."));

        String email = dtoUpdateUser.getEmail();
        String username = dtoUpdateUser.getUsername();
        String password = dtoUpdateUser.getPassword();

        if(utilityVerifyRequestField.isValidField(username)) {
            if(accessUser.existsUsername(username)) {
                throw new HttpBadRequestException("Username already exists.");
            }
            user.setUsername(username);
        }

        if (utilityVerifyRequestField.isValidField(email)) {
            if(accessUser.existsEmail(email)) {
                throw new HttpBadRequestException("Email already exists.");
            }
            user.setEmail(email);
        }

        if(utilityVerifyRequestField.isValidField(password)) {
            user.setPassword(password);
        }

        audit.debug("Saving User " + user.getUserId() + ".");
        accessUser.save(user)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist updated User.") );

        audit.debug("Mapping EntityType into DTO.");
        return mapperUser.entityToDto(user);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOUser delete(Integer id) {

        audit.debug("Deleting User " + id + ".");
        User user = accessUser.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("User not found."));

        if (!accessUser.remove(user.getUserId())) {
            throw new HttpInternalServerException("Failed to delete User.");
        }

        audit.debug("Mapping EntityType into DTO.");
        return mapperUser.entityToDto(user);

    }


    public List<DTOVote> getVotes(Integer id) {

        User user = accessUser.get(id)
                .orElseThrow( () -> new HttpNotFoundException("User not found."));

        return mapperVote.entityToDto(accessVote.getByUser(user));

    }

}
