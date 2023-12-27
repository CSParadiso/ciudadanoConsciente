package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessUser;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.model.User;
import ciudadano.consciente.dto.DTOUpdateUser;
import ciudadano.consciente.dto.DTOCreateUser;
import ciudadano.consciente.dto.DTOUser;
import ciudadano.consciente.mapper.MapperUser;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;


@RequestScoped
public class ServiceUser {

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    @Inject
    AccessUser accessUser;

    @Inject
    MapperUser mapperUser;

    public DTOUser obtener(Integer identificador) {

        User user = accessUser.get(identificador) // Si obtiene nulo, lanza excepción
                .orElseThrow(() -> new HttpNoContentException("No existe el usuario con el identificador " + identificador));

        return mapperUser.entidadATransferible(user);

    }

    public List<DTOUser> obtenerTodos() {

        List<User> userList = accessUser.obtenerTodos();

        return mapperUser.entidadATransferible(userList);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOUser create(DTOCreateUser DTOCreateUser) {

        String email = DTOCreateUser.getEmail();
        String username = DTOCreateUser.getUsername();
        String password = DTOCreateUser.getPassword();
        if(!utilityVerifyRequestField.isValidField(email) ||
                !utilityVerifyRequestField.isValidField(username) ||
                !utilityVerifyRequestField.isValidField(password)) {
            throw new HttpBadRequestException("Todos los campos son requeridos.");
        }

        if(accessUser.existeEmail(email)) {
            throw new HttpBadRequestException("El email ya existe.");
        }

        if(accessUser.existeUsername(username)) {
            throw new HttpBadRequestException("El nombre de usuario ya existe.");
        }

        User user = mapperUser.transferibleAEntidad(email, username);

        if(utilityVerifyRequestField.isValidField(password)) {
            user.setPassword(password);
        }

        user = accessUser.persistir(user)
                .orElseThrow( ()-> new HttpInternalServerException("Problemas al persistir nuevo usuario."));

        return mapperUser.entidadATransferible(user);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOUser update(DTOUpdateUser DTOUpdateUser) {

        Integer userId = DTOUpdateUser.getIdentificador();
        if(!utilityVerifyRequestField.isValidField(userId)) {
            throw new HttpBadRequestException("El campo identificador es requerido");
        }

        User user = accessUser.get(userId)
                .orElseThrow(()-> new HttpNoContentException("El Usuario no existe."));

        String email = DTOUpdateUser.getEmail();
        String username = DTOUpdateUser.getUsername();
        String password = DTOUpdateUser.getPassword();
        if(!utilityVerifyRequestField.isValidField(email) &&
                !utilityVerifyRequestField.isValidField(username) &&
                !utilityVerifyRequestField.isValidField(password)) {
            throw new HttpBadRequestException("Sin campos que update");
        }

        if(utilityVerifyRequestField.isValidField(email)) {
            if (!accessUser.existeEmail(email)) {
                user.setEmail(email);
            } else {
                throw new HttpBadRequestException("El email ya existe.");
            }
        }

        if(utilityVerifyRequestField.isValidField(username)) {
            if(!accessUser.existeUsername(username)) {
                user.setUsername(username);
            } else {
                throw new HttpBadRequestException("El nombre de Usuario ya existe.");
            }
        }

        if(utilityVerifyRequestField.isValidField(password)) {
            user.setPassword(password);
        }

        user = accessUser.persistir(user)
                .orElseThrow( () -> new HttpInternalServerException("Problemas al persistir actualización de Usuario."));

        return mapperUser.entidadATransferible(user);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void eliminar(Integer identificador) {

        if (!accessUser.eliminar(identificador)) {
            throw new HttpNoContentException("Usuario a eliminar no existe");
        };

    }

}
