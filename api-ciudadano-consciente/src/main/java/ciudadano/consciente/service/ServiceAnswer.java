package ciudadano.consciente.service;

import ciudadano.consciente.access.*;
import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.AuthDenialSecurityException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.mapper.MapperAnswer;
import ciudadano.consciente.model.*;
import io.quarkus.oidc.UserInfo;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
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

  @Inject
  AccessUserRoleLevel accessUserRoleLevel;

  public List<DTOAnswer> getAll() {

    audit.debug("Getting all Answers.");
    return mapperAnswer.entityToDto(accessAnswer.getAll());

  }

  public List<DTOAnswerOfChildrens> getAllChildrenLevelsAnswers(UserInfo userInfo, Integer levelId) {

    audit.debug("Verifying if parent Level exists " + levelId);
    Level level = accessLevel.get(levelId)
        .orElseThrow(() -> new HttpNoContentException("Level not found."));

    User user = accessUser.getByUsername(userInfo.getPreferredUserName())
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    // TODO ESTO ESTÁ HORRIBLE PERO FUNCIONÓ RÁPIDO
    // Corregir accesor para que retorne otra cosa accesor.hasRoleInGenealogy(user,
    // levelId)
    // Generar DTO de Genealogy <Level, Level>

    List<Integer> genalogy = accessLevel.getGenealogy(level);
    boolean hasRole = false;
    for (int levelInGenealogy : genalogy) {
      if (accessUserRoleLevel.getByLevelAndUser(levelInGenealogy, user.getUserId()).isPresent()) {
        hasRole = true;
        break;
      }
      ;
    }

    if (!hasRole) {
      audit.warn("Mismatch: NOT AUTHORIZED TO GET ANSWERS OF LEVEL. User Claims doesn't match User data.");
      throw new AuthDenialSecurityException(
          "Mismatch: NOT AUTHORIZED TO GET ANSWERS OF LEVEL. User Claims doesn't match User data.");
    }

    List<Object[]> rawAnswers = accessAnswer.getAllChildrenLevelsAnswers(level.getLevelId());

    List<DTOAnswerOfChildrens> answerOfChildrens = new ArrayList<>();
    for (Object[] rawAnswer : rawAnswers) {
      DTOAnswerOfChildrens dtoAnswerOfChildrens = new DTOAnswerOfChildrens();
      dtoAnswerOfChildrens.setLevel((Integer) rawAnswer[0]);
      dtoAnswerOfChildrens.setParent((Integer) rawAnswer[1]);
      dtoAnswerOfChildrens.setActivity((Integer) rawAnswer[2]);
      dtoAnswerOfChildrens.setContent((Integer) rawAnswer[3]);
      dtoAnswerOfChildrens.setAnswer((Integer) rawAnswer[4]);
      dtoAnswerOfChildrens.setUser((Integer) rawAnswer[5]);
      // TODO refactorizar INICIO
      audit.debug("Fecha recuperada: " + rawAnswer[6]);
      Instant fecha = (Instant) rawAnswer[6];
      dtoAnswerOfChildrens.setCreated(fecha.atZone(ZoneId.of("America/Argentina/Ushuaia")).toOffsetDateTime());
      // TODO refactorizar FIN
      dtoAnswerOfChildrens.setStatus((Boolean) rawAnswer[7]);
      answerOfChildrens.add(dtoAnswerOfChildrens);
    }

    return answerOfChildrens;

  }

  @Deprecated(since = "1.1.0")
  public List<DTOAnswerOfChildrens> getAllChildrenLevelsAnswersOfUser(Integer levelId, Integer userId) {

    audit.debug("Verifying if parent Level exists " + levelId);
    Level level = accessLevel.get(levelId)
        .orElseThrow(() -> new HttpNoContentException("Level not found."));

    audit.debug("Verifying if user exists " + userId);
    User user = accessUser.get(userId)
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    List<Object[]> rawAnswers = accessAnswer.getAllChildrenLevelsAnswersOfUser(level.getLevelId(), user.getUserId());

    List<DTOAnswerOfChildrens> answerOfChildrens = new ArrayList<>();
    for (Object[] rawAnswer : rawAnswers) {
      DTOAnswerOfChildrens dtoAnswerOfChildrens = new DTOAnswerOfChildrens();
      dtoAnswerOfChildrens.setLevel((Integer) rawAnswer[0]);
      dtoAnswerOfChildrens.setParent((Integer) rawAnswer[1]);
      dtoAnswerOfChildrens.setActivity((Integer) rawAnswer[2]);
      dtoAnswerOfChildrens.setContent((Integer) rawAnswer[3]);
      dtoAnswerOfChildrens.setAnswer((Integer) rawAnswer[4]);
      dtoAnswerOfChildrens.setUser((Integer) rawAnswer[5]);
      // TODO refactorizar INICIO
      audit.debug("Fecha recuperada: " + rawAnswer[6]);
      Instant fecha = (Instant) rawAnswer[6];
      dtoAnswerOfChildrens.setCreated(fecha.atZone(ZoneId.of("America/Argentina/Ushuaia")).toOffsetDateTime());
      // TODO refactorizar FIN
      dtoAnswerOfChildrens.setStatus((Boolean) rawAnswer[7]);
      answerOfChildrens.add(dtoAnswerOfChildrens);
    }

    return answerOfChildrens;

  }

  public List<DTOAnswerOfChildrens> getAllChildrenLevelsAnswersOfUser(UserInfo userInfo, Integer levelId) {

    audit.debug("Verifying if parent Level exists " + levelId);
    Level level = accessLevel.get(levelId)
        .orElseThrow(() -> new HttpNoContentException("Level not found."));

    audit.debug("Verifying if user exists " + userInfo.getPreferredUserName());
    User user = accessUser.getByUsername(userInfo.getPreferredUserName())
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    List<Object[]> rawAnswers = accessAnswer.getAllChildrenLevelsAnswersOfUser(level.getLevelId(), user.getUserId());

    List<DTOAnswerOfChildrens> answerOfChildrens = new ArrayList<>();
    for (Object[] rawAnswer : rawAnswers) {
      DTOAnswerOfChildrens dtoAnswerOfChildrens = new DTOAnswerOfChildrens();
      dtoAnswerOfChildrens.setLevel((Integer) rawAnswer[0]);
      dtoAnswerOfChildrens.setParent((Integer) rawAnswer[1]);
      dtoAnswerOfChildrens.setActivity((Integer) rawAnswer[2]);
      dtoAnswerOfChildrens.setContent((Integer) rawAnswer[3]);
      dtoAnswerOfChildrens.setAnswer((Integer) rawAnswer[4]);
      dtoAnswerOfChildrens.setUser((Integer) rawAnswer[5]);
      // TODO refactorizar INICIO
      audit.debug("Fecha recuperada: " + rawAnswer[6]);
      Instant fecha = (Instant) rawAnswer[6];
      dtoAnswerOfChildrens.setCreated(fecha.atZone(ZoneId.of("America/Argentina/Ushuaia")).toOffsetDateTime());
      // TODO refactorizar FIN
      dtoAnswerOfChildrens.setStatus((Boolean) rawAnswer[7]);
      answerOfChildrens.add(dtoAnswerOfChildrens);
    }

    return answerOfChildrens;

  }

  public DTOAnswer get(UserInfo userInfo, Integer id) {

    audit.debug("Getting Answer " + id + ".");
    Answer answer = accessAnswer.get(id)
        .orElseThrow(() -> new HttpNoContentException("Answer not found."));

    if (!answer.getUserId().getUsername().equals(userInfo.getPreferredUserName())) {
      audit.warn("Mismatch: NOT AUTHORIZED TO GET ANSWER. User Claims doesn't match User data.");
      throw new AuthDenialSecurityException(
          "Mismatch: NOT AUTHORIZED TO GET ANSWER. User Claims doesn't match User data.");
    }

    audit.debug("Mapping Answer into DTO.");
    return mapperAnswer.entityToDto(answer);

  }

  @Deprecated(since = "1.1.0. User should not be in DTO.")
  @Transactional(Transactional.TxType.REQUIRED)
  public DTOAnswer create(DTOCreateAnswerOld dtoCreateAnswer) {

    Integer activityDto = dtoCreateAnswer.getActivity();
    Integer userDto = dtoCreateAnswer.getUserId();
    Boolean statusDto = dtoCreateAnswer.getStatus();

    Activity activity = accessActivity.get(activityDto)
        .orElseThrow(() -> new HttpNoContentException("Activity not found."));

    User user = accessUser.get(userDto)
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    audit.debug("Creating Answer.");
    Answer answer = new Answer(activity, user, statusDto);

    audit.debug("Saving Answer " + answer.getAnswerId() + ".");
    accessAnswer.save(answer)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist new Answer."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperAnswer.entityToDto(answer);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public DTOAnswer createAnswer(UserInfo userInfo, DTOCreateAnswer dtoCreateAnswer) {

    Integer activityDto = dtoCreateAnswer.getActivity();
    Boolean statusDto = dtoCreateAnswer.getStatus();

    Activity activity = accessActivity.get(activityDto)
        .orElseThrow(() -> new HttpNoContentException("Activity not found."));

    User user = accessUser.getByUsername(userInfo.getPreferredUserName())
        .orElseThrow(() -> new HttpNoContentException("User not found."));

    audit.debug("Creating Answer.");
    Answer answer = new Answer(activity, user, statusDto);

    audit.debug("Saving Answer " + answer.getAnswerId() + ".");
    accessAnswer.save(answer)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist new Answer."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperAnswer.entityToDto(answer);

  }

  @Transactional(Transactional.TxType.REQUIRED)
  public List<DTOAnswer> createBatchAnswers(DTOCreateBatchAnswer dtoCreateBatchAnswer) {

    Integer userDto = dtoCreateBatchAnswer.getUserId();

    List<DTOAnswer> answers = new ArrayList<>();
    for (DTOCreateBatchAnswer.DTOBatchAnswer batchAnswer : dtoCreateBatchAnswer.getAnswers()) {
      DTOCreateAnswerOld dtoCreateAnswer = new DTOCreateAnswerOld();
      dtoCreateAnswer.setUserId(userDto);
      dtoCreateAnswer.setActivity(batchAnswer.getActivity());
      dtoCreateAnswer.setStatus(batchAnswer.getStatus());
      answers.add(create(dtoCreateAnswer));
    }
    return answers;

  }

  @Deprecated(since = "1.0.3. The answers should not be modified.")
  @Transactional(Transactional.TxType.REQUIRED)
  public DTOAnswer updateStatus(Integer id, DTOUpdateAnswerStatus dtoUpdateAnswerStatus) {

    Answer answer = accessAnswer.get(id)
        .orElseThrow(() -> new HttpNoContentException("Answer not found."));

    audit.debug("Updating Answer " + id + ".");
    answer.setStatus(dtoUpdateAnswerStatus.getStatus());

    audit.debug("Saving Answer " + answer.getAnswerId() + ".");
    accessAnswer.save(answer)
        .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated Answer."));

    audit.debug("Mapping EntityType into DTO.");
    return mapperAnswer.entityToDto(answer);

  }

}
