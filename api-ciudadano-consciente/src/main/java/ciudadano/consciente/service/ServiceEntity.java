package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessEntity;
import ciudadano.consciente.dto.DTOAnswersStatus;
import ciudadano.consciente.dto.DTOCreateEntity;
import ciudadano.consciente.dto.DTOEntity;
import ciudadano.consciente.dto.DTOUpdateEntity;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.mapper.MapperEntity;
import ciudadano.consciente.model.AnswersStatus;
import ciudadano.consciente.model.Entity;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceEntity {

    @Inject
    Logger audit;

    @Inject
    MapperEntity mapperEntity;

    @Inject
    AccessEntity accessEntity;

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    public List<DTOEntity> getAll() {

        audit.debug("Retrieving all categories of Entities...");
        return mapperEntity.entityToDto(accessEntity.getAll());

    }

    public DTOEntity get(Integer id) {

        audit.debug("Getting Entity " + id + ".");
        Entity entity = accessEntity.get(id)
                .orElseThrow( () -> new HttpNotFoundException("Entity not found."));

        audit.debug("Mapping Entity into DTO.");
        return mapperEntity.entityToDto(entity);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOEntity create(DTOCreateEntity dtoCreateEntity) {

        String title = dtoCreateEntity.getTitle();
        if(accessEntity.existTitle(title)) {
            throw new HttpBadRequestException("The title already exists.");
        }

        audit.debug("Creating category of Entity.");
        Entity entity = mapperEntity.dtoToEntity(dtoCreateEntity);

        audit.debug("Saving category of Entity " + entity.getEntityId() + ".");
        accessEntity.save(entity)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new category of Entity") );

        audit.debug("Mapping Entity into DTO.");
        return mapperEntity.entityToDto(entity);    
        
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOEntity update(Integer id, DTOUpdateEntity dtoUpdateEntity) {

        audit.debug("Updating category of Entity " + id + ".");
        String title = dtoUpdateEntity.getTitle();

        Entity entity = accessEntity.get(id)
                .orElseThrow( () -> new HttpNotFoundException("Category of Entity not found."));

        if(utilityVerifyRequestField.isValidField(title)) {
            if(accessEntity.existTitle(title)) {
                throw new HttpBadRequestException("The title already exists.");
            }
            entity.setTitle(title);
        }

        audit.debug("Saving updated category of Entity " + entity.getEntityId() + ".");
        accessEntity.save(entity)
                .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated category of Entity."));

        audit.debug("Mapping Entity into DTO.");
        return mapperEntity.entityToDto(entity);
        
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOEntity delete(Integer id) {

        audit.debug("Deleting category of Entity " + id + ".");
        Entity entity = accessEntity.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Entity not found."));

        if(!accessEntity.remove(entity.getEntityId())) {
            throw new HttpInternalServerException("Failed to delete Entity");
        }

        audit.debug("Mapping Entity into DTO.");
        return mapperEntity.entityToDto(entity);

    }
    
}
