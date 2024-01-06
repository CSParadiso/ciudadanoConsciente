package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessLevel;
import ciudadano.consciente.access.AccessReference;
import ciudadano.consciente.dto.DTOReference;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.model.Level;
import ciudadano.consciente.model.Reference;
import ciudadano.consciente.dto.DTOUpdateReference;
import ciudadano.consciente.dto.DTOCreateReference;
import ciudadano.consciente.mapper.MapperReference;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;

@RequestScoped
public class ServiceReference {

    @Inject
    UtilityVerifyRequestField utilityVerifyRequestField;

    @Inject
    AccessLevel accessLevel;

    @Inject
    AccessReference accessReference;

    @Inject
    MapperReference mapperReference;

    @Inject
    Logger audit;

    public List<DTOReference> getAll() {

        audit.debug("Retrieving all References.");
        return mapperReference.entityToDto(accessReference.getAll());

    }

    public DTOReference get(Integer id) {

        audit.debug("Retrieving Reference " + id + ".");
        Reference reference = accessReference.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Reference not found.") );

        return mapperReference.entityToDto(reference);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOReference create(DTOCreateReference dtoCreateReference) {

        audit.debug("Creating Reference.");

        String title = dtoCreateReference.getTitle();
        String url = dtoCreateReference.getUrl();
        Integer levelDto = dtoCreateReference.getLevel();
        if(!utilityVerifyRequestField.isValidField(title) ||
                !utilityVerifyRequestField.isValidField(url) ||
                !utilityVerifyRequestField.isValidField(levelDto)) {
            throw new HttpBadRequestException("Title, URL and Level required.");
        }

        Level level = accessLevel.get(levelDto)
                .orElseThrow(()-> new HttpNotFoundException("Level not found."));

        audit.debug("Verifying if title " + title + " of Reference already exists in Level " + levelDto);
        if(accessReference.existsTitleInLevel(level, title)) {
            throw new HttpBadRequestException("Already exists a Reference with that title in Level.");
        }

        Reference reference = mapperReference.dtoToEntity(dtoCreateReference);

        // Esto quizás es innecesario, ya se mapea antes
        String description = dtoCreateReference.getDescription();
        if(utilityVerifyRequestField.isValidField(description)) {
            reference.setDescription(description);
        }

        audit.debug("Saving Reference " + reference.getReferenceId() + ".");
        accessReference.save(reference)
                .orElseThrow(()-> new HttpInternalServerException("Failed to persist new Reference."));

        audit.debug("Mapping Entity into DTO.");
        return mapperReference.entityToDto(reference);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOReference update(Integer id, DTOUpdateReference dtoUpdateReference) {

        audit.debug("Updating Reference " + id + ".");
        Reference reference = accessReference.get(id)
                .orElseThrow(()-> new HttpNotFoundException("Reference not found."));

        Integer level = dtoUpdateReference.getLevel();
        String title = dtoUpdateReference.getTitle();
        String url = dtoUpdateReference.getUrl();
        String description = dtoUpdateReference.getDescription();
        if(!utilityVerifyRequestField.isValidField(level) &&
                !utilityVerifyRequestField.isValidField(title) &&
                !utilityVerifyRequestField.isValidField(url) &&
                !utilityVerifyRequestField.isValidField(description)) {
            throw new HttpBadRequestException("No updates to make.");
        }

        if(utilityVerifyRequestField.isValidField(level)) {
            reference.setLevel(accessLevel.get(level)
                    .orElseThrow( ()-> new HttpNotFoundException("Level not found.") ));
        }

        if(utilityVerifyRequestField.isValidField(title)) {
            if(accessReference.existsTitleInLevel(reference.getLevel(), title)) {
                throw new HttpBadRequestException("Already exists a Reference with that title in Level.");
            }
            reference.setTitle(title);
        }

        if(utilityVerifyRequestField.isValidField(url)) {
            reference.setUrl(url);
        }

        if(utilityVerifyRequestField.isValidField(description)) {
            reference.setDescription(description);
        }

        audit.debug("Saving Reference " + reference.getReferenceId() + ".");
        accessReference.save(reference)
                .orElseThrow(()-> new HttpInternalServerException("Failed to persist updated Reference."));

        audit.debug("Mapping Entity into DTO.");
        return mapperReference.entityToDto(reference);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOReference delete(Integer id) {

        audit.debug("Deleting Reference " + id + ".");
        Reference reference = accessReference.get(id)
                .orElseThrow( ()-> new HttpNotFoundException("Reference not found.") );

        if(!accessReference.remove(reference.getReferenceId())) {
            throw new HttpInternalServerException("Failed to delete Reference");
        };

        audit.debug("Mapping Entity into DTO.");
        return mapperReference.entityToDto(reference);

    }

}
