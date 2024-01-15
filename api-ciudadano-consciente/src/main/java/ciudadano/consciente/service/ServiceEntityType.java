package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessEntityType;
import ciudadano.consciente.dto.DTOCreateEntityType;
import ciudadano.consciente.dto.DTOEntityType;
import ciudadano.consciente.dto.DTOUpdateEntityType;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.mapper.MapperEntityType;
import ciudadano.consciente.model.EntityType;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceEntityType {

    @Inject
    Logger audit;

    @Inject
    MapperEntityType mapperEntityType;

    @Inject
    AccessEntityType accessEntityType;

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    public List<DTOEntityType> getAll() {

        audit.debug("Retrieving all categories of Entities...");
        return mapperEntityType.entityToDto(accessEntityType.getAll());

    }

    public DTOEntityType get(Integer id) {

        audit.debug("Getting EntityType " + id + ".");
        EntityType entityType = accessEntityType.get(id)
                .orElseThrow( () -> new HttpNotFoundException("EntityType not found."));

        audit.debug("Mapping EntityType into DTO.");
        return mapperEntityType.entityToDto(entityType);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOEntityType create(DTOCreateEntityType dtoCreateEntityType) {

        String title = dtoCreateEntityType.getTitle();
        if(accessEntityType.existTitle(title)) {
            throw new HttpBadRequestException("The title already exists.");
        }

        audit.debug("Creating category of EntityType.");
        EntityType entityType = mapperEntityType.dtoToEntity(dtoCreateEntityType);

        audit.debug("Saving category of EntityType " + entityType.getEntityTypeId() + ".");
        accessEntityType.save(entityType)
                .orElseThrow( ()-> new HttpInternalServerException("Failed to persist new category of EntityType") );

        audit.debug("Mapping EntityType into DTO.");
        return mapperEntityType.entityToDto(entityType);
        
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOEntityType update(Integer id, DTOUpdateEntityType dtoUpdateEntityType) {

        audit.debug("Updating category of EntityType " + id + ".");
        String title = dtoUpdateEntityType.getTitle();

        EntityType entityType = accessEntityType.get(id)
                .orElseThrow( () -> new HttpNotFoundException("Category of EntityType not found."));

        if(utilityVerifyRequestField.isValidField(title)) {
            if(accessEntityType.existTitle(title)) {
                throw new HttpBadRequestException("The title already exists.");
            }
            entityType.setTitle(title);
        }

        audit.debug("Saving updated category of EntityType " + entityType.getEntityTypeId() + ".");
        accessEntityType.save(entityType)
                .orElseThrow(() -> new HttpInternalServerException("Failed to persist updated category of EntityType."));

        audit.debug("Mapping EntityType into DTO.");
        return mapperEntityType.entityToDto(entityType);
        
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOEntityType delete(Integer id) {

        audit.debug("Deleting category of EntityType " + id + ".");
        EntityType entityType = accessEntityType.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("EntityType not found."));

        if(!accessEntityType.remove(entityType.getEntityTypeId())) {
            throw new HttpInternalServerException("Failed to delete EntityType");
        }

        audit.debug("Mapping EntityType into DTO.");
        return mapperEntityType.entityToDto(entityType);

    }
    
}
