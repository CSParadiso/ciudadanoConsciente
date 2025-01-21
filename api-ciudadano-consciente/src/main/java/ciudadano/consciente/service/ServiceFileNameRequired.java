package ciudadano.consciente.service;

import ciudadano.consciente.access.*;
import ciudadano.consciente.dto.*;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNoContentException;
import ciudadano.consciente.mapper.*;
import ciudadano.consciente.model.*;
import ciudadano.consciente.utility.UtilityFileSignature;
import ciudadano.consciente.utility.UtilityFileSystem;
import ciudadano.consciente.utility.UtilityMetadataClasses;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import io.quarkus.oidc.UserInfo;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.DataException;
import org.jboss.logging.Logger;

import java.util.Arrays;
import java.util.List;

@RequestScoped
public class ServiceFileNameRequired {

  final String ENTITY_NAME = UtilityMetadataClasses.getTableName(FileNameRequired.class);

  @Inject
  Logger audit;

  @Inject
  AccessFileNameRequired accessFileNameRequired;

  @Inject
  MapperFileNameRequired mapperFileNameRequired;

  public List<DTOFileNameRequired> getAll() {

    audit.debug("Retrieving all File Name Required.");
    return mapperFileNameRequired.entityToDto(accessFileNameRequired.getFileNames());

  }

}
