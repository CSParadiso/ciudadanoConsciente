package ciudadano.consciente.service;

import ciudadano.consciente.access.*;
import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.mapper.MapperAnswer;
import ciudadano.consciente.model.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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
    AccessLevel accessLevel;


    public List<DTOAnswer> getAll() {

        audit.debug("Getting all Answers.");
        return mapperAnswer.entityToDto(accessAnswer.getAll());
        
    }

    public List<DTOAnswerOfChildrens> getAllChildrenLevelsAnswers(Integer levelId) {

        audit.debug("Verifying if parent Level exists " + levelId);
        Level level = accessLevel.get(levelId)
                .orElseThrow( ()-> new HttpNoContentException("Level not found."));

        List<Object[]> rawAnswers = accessAnswer.getAllChildrenLevelsAnswers(level.getLevelId());

        List<DTOAnswerOfChildrens> answerOfChildrens = new ArrayList<>();
        for(Object[] rawAnswer : rawAnswers) {
            DTOAnswerOfChildrens dtoAnswerOfChildrens = new DTOAnswerOfChildrens();
            dtoAnswerOfChildrens.setLevel((Integer) rawAnswer[0]);
            dtoAnswerOfChildrens.setParent((Integer) rawAnswer[1]);
            dtoAnswerOfChildrens.setActivity((Integer) rawAnswer[2]);
            dtoAnswerOfChildrens.setContent((Integer) rawAnswer[3]);
            dtoAnswerOfChildrens.setAnswer((Integer) rawAnswer[4]);
            dtoAnswerOfChildrens.setUser((Integer) rawAnswer[5]);
            dtoAnswerOfChildrens.setCreated((Date) rawAnswer[6]);
            dtoAnswerOfChildrens.setLastModified((Date) rawAnswer[7]);
            dtoAnswerOfChildrens.setStatus((Boolean) rawAnswer[8]);
            answerOfChildrens.add(dtoAnswerOfChildrens);
        }

        return answerOfChildrens;

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
        Boolean statusDto = dtoCreateAnswer.getStatus();

        Activity activity = accessActivity.get(activityDto)
                        .orElseThrow( ()-> new HttpNotFoundException("Activity not found."));

        User user = accessUser.get(userDto)
                        .orElseThrow( ()-> new HttpNotFoundException("User not found."));

        audit.debug("Creating Answer.");
        Answer answer = new Answer(activity, user, statusDto);

        audit.debug("Saving Answer " + answer.getAnswerId() + ".");
        accessAnswer.save(answer)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new Answer.") );

        audit.debug("Mapping EntityType into DTO.");
        return mapperAnswer.entityToDto(answer);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<DTOAnswer> createBatchAnswers(DTOCreateBatchAnswer dtoCreateBatchAnswer) {

        Integer userDto = dtoCreateBatchAnswer.getUserId();

        List<DTOAnswer> answers = new ArrayList<>();
        for (DTOCreateBatchAnswer.DTOBatchAnswer batchAnswer : dtoCreateBatchAnswer.getAnswers() ) {
            DTOCreateAnswer dtoCreateAnswer = new DTOCreateAnswer();
            dtoCreateAnswer.setUserId(userDto);
            dtoCreateAnswer.setActivity(batchAnswer.getActivity());
            dtoCreateAnswer.setStatus(batchAnswer.getStatus());
            answers.add(create(dtoCreateAnswer));
        }
        return answers;

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOAnswer updateStatus(Integer id, DTOUpdateAnswerStatus dtoUpdateAnswerStatus) {

        Answer answer = accessAnswer.get(id)
                .orElseThrow( () -> new HttpNotFoundException("Answer not found."));

        audit.debug("Updating Answer " + id + ".");
        answer.setStatus(dtoUpdateAnswerStatus.getStatus());
        answer.setLastModified(LocalDate.now());

        audit.debug("Saving Answer " + answer.getAnswerId() + ".");
        accessAnswer.save(answer)
                .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated Answer."));

        audit.debug("Mapping EntityType into DTO.");
        return mapperAnswer.entityToDto(answer);

    }

}
