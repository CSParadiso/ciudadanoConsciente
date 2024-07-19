package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessRandomStreak;
import ciudadano.consciente.access.AccessUser;
import ciudadano.consciente.dto.DTOCreateRandomStreak;
import ciudadano.consciente.dto.DTORandomStreak;
import ciudadano.consciente.dto.DTOUpdateRandomStreak;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.mapper.MapperRandomStreak;
import ciudadano.consciente.model.RandomStreak;
import ciudadano.consciente.model.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceStreak {

    @Inject
    Logger audit;

    @Inject
    AccessRandomStreak accessRandomStreak;

    @Inject
    MapperRandomStreak mapperRandomStreak;

    @Inject
    AccessUser accessUser;

    @Transactional(Transactional.TxType.REQUIRED)
    public DTORandomStreak createRandomStreak(DTOCreateRandomStreak dtoCreateRandomStreak) {

        int userId = dtoCreateRandomStreak.getUserId();
        User user = accessUser.get(userId)
                .orElseThrow( ()-> new HttpNoContentException("User not found."));

        int actualStreak = dtoCreateRandomStreak.getActualStreak();
        RandomStreak randomStreak = new RandomStreak(user, actualStreak);

        audit.debug("Saving randomStreak.");
        try {
            accessRandomStreak.save(randomStreak)
                    .orElseThrow(() -> new HttpInternalServerException("Failed to persist new Random Streak."));
        } catch (ConstraintViolationException e) {
            audit.debug("Random Streak exists for User " + randomStreak.getUser().getUserId());
            throw new HttpBadRequestException("Random Streak already exists for User " + randomStreak.getUser().getUserId());
        }

        audit.debug("Mapping EntityType into DTO.");
        return mapperRandomStreak.entityToDto(randomStreak);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTORandomStreak updateRandomStreak(DTOUpdateRandomStreak dtoUpdateRandomStreak) {

        audit.debug("Trying to update Random Streak of User.");
        int userId = dtoUpdateRandomStreak.getUserId();
        User user = accessUser.get(userId)
                .orElseThrow( ()-> new HttpNoContentException("User not found.") );

        RandomStreak randomStreak = accessRandomStreak.getRandomStreaksByUser(user)
                .orElseThrow( ()-> new HttpNoContentException("Random Streak not found.") );

        // Set values
        int dtoActualStreak = dtoUpdateRandomStreak.getActualStreak();
        randomStreak.incrementCount(dtoActualStreak); // Increment count
        randomStreak.setActualStreak(dtoActualStreak); // Update actual streak
        if (dtoActualStreak > randomStreak.getMaxStreak()) { // Update max streak
            audit.debug("Updated Max Streak of User " + user.getUserId() +
                    " from " + randomStreak.getMaxStreak() + " to " + dtoActualStreak);
            randomStreak.setMaxStreak(dtoActualStreak);
        }

        audit.debug("Updating Random Streak of User " + user.getUserId());
        accessRandomStreak.save(randomStreak)
                    .orElseThrow(() -> new HttpInternalServerException("Failed to update Random Streak."));

        audit.debug("Mapping Entity into DTO.");
        return mapperRandomStreak.entityToDto(randomStreak);

    }

    public List<DTORandomStreak> getAllRandom() {

        audit.debug("Trying to retrieve all Random Streaks.");
        return mapperRandomStreak.entityToDto(accessRandomStreak.getAllRandomStreaks());

    }

    public DTORandomStreak getRandomStreak(Integer id) {

        audit.debug("Trying to retrieve Random Streak.");
        RandomStreak randomStreak = accessRandomStreak.getRandomStreak(id)
                .orElseThrow( ()-> new HttpNoContentException("Random Streak not found.") );
        return mapperRandomStreak.entityToDto(randomStreak);

    }

    public DTORandomStreak getRandomStreakByUser(Integer userId) {

        audit.debug("Trying to retrieve Random Streak by User.");
        User user = accessUser.get(userId)
                .orElseThrow( ()-> new HttpNoContentException("User not found.") );

        RandomStreak randomStreak = accessRandomStreak.getRandomStreaksByUser(user)
                .orElseThrow( ()-> new HttpNoContentException("Random Streak not found.") );

        audit.debug("Mapping Entity into DTO.");
        return mapperRandomStreak.entityToDto(randomStreak);

    }

}
