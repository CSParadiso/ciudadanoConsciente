package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessActivity;
import ciudadano.consciente.access.AccessAnswer;
import ciudadano.consciente.access.AccessAnswersStatus;
import ciudadano.consciente.access.AccessUser;
import ciudadano.consciente.dto.DTOAnswer;
import ciudadano.consciente.dto.DTOCreateAnswer;
import ciudadano.consciente.dto.DTOUpdateAnswerStatus;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.mapper.MapperAnswer;
import ciudadano.consciente.model.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.time.LocalDate;
import java.util.List;

@RequestScoped
public class ServiceAnswer {

    @Inject
    Logger audit;

    @Inject
    MapperAnswer mapperAnswer;

    @Inject
    AccessAnswer accessAnswer;

    @Inject
    AccessActivity accessActivity;

    @Inject
    AccessUser accessUser;

    @Inject
    AccessAnswersStatus accessAnswersStatus;

    public List<DTOAnswer> getAll() {

        audit.debug("Getting all Answers.");
        return mapperAnswer.entityToDto(accessAnswer.getAll());
        
    }

    public DTOAnswer get(Integer id) {

        audit.debug("Getting Answer " + id + ".");
        Answer answer = accessAnswer.get(id)
                .orElseThrow( () -> new HttpNotFoundException("Answer not found."));

        audit.debug("Mapping Answer into DTO.");
        return  mapperAnswer.entityToDto(answer);
        
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOAnswer create(DTOCreateAnswer dtoCreateAnswer) {

        Integer activityDto = dtoCreateAnswer.getActivity();
        Integer userDto = dtoCreateAnswer.getUserId();
        Integer statusDto = dtoCreateAnswer.getAnswersStatus();

        Activity activity = accessActivity.get(activityDto)
                        .orElseThrow( ()-> new HttpNotFoundException("Activity not found."));

        User user = accessUser.get(userDto)
                        .orElseThrow( ()-> new HttpNotFoundException("User not found."));

        AnswersStatus answersStatus = accessAnswersStatus.get(statusDto)
                        .orElseThrow( ()-> new HttpNotFoundException("Answer Status not found.") );

        audit.debug("Creating Answer.");
        Answer answer = new Answer(activity, user, answersStatus);

        audit.debug("Saving Answer " + answer.getAnswerId() + ".");
        accessAnswer.save(answer)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new Answer.") );

        audit.debug("Mapping EntityType into DTO.");
        return mapperAnswer.entityToDto(answer);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOAnswer updateStatus(Integer id, DTOUpdateAnswerStatus dtoUpdateAnswerStatus) {

        Answer answer = accessAnswer.get(id)
                .orElseThrow( () -> new HttpNotFoundException("Answer not found."));

        AnswersStatus answersStatus = accessAnswersStatus.get(id)
                .orElseThrow( () -> new HttpNotFoundException("Answer Status not found."));

        audit.debug("Updating Answer " + id + ".");
        answer.setAnswersStatus(answersStatus);
        answer.setLastModified(LocalDate.now());

        audit.debug("Saving Answer " + answer.getAnswerId() + ".");
        accessAnswer.save(answer)
                .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated Answer."));

        audit.debug("Mapping EntityType into DTO.");
        return mapperAnswer.entityToDto(answer);

    }
}
