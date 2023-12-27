package ciudadano.consciente.service;

import ciudadano.consciente.access.AccessLevel;
import ciudadano.consciente.access.AccessReference;
import ciudadano.consciente.dto.DTOReference;
import ciudadano.consciente.exception.HttpBadRequestException;
import ciudadano.consciente.exception.HttpInternalServerException;
import ciudadano.consciente.exception.HttpNotFoundException;
import ciudadano.consciente.model.Reference;
import ciudadano.consciente.dto.DTOUpdateReference;
import ciudadano.consciente.dto.DTOCreateReference;
import ciudadano.consciente.mapper.MapperReference;
import ciudadano.consciente.utility.UtilityVerifyRequestField;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

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

    public List<DTOReference> obtenerTodos() {

        return mapperReference.entidadATransferible(accessReference.obtenerTodos());

    }

    public DTOReference obtener(Integer identificador) {

        Reference reference = accessReference.obtener(identificador)
                .orElseThrow( ()-> new HttpNotFoundException("La Referencia no existe.") );

        return mapperReference.entidadATransferible(reference);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOReference create(DTOCreateReference DTOCreateReference) {

        String title = DTOCreateReference.getTitle();
        String url = DTOCreateReference.getUrl();
        Integer level = DTOCreateReference.getLevel();
        if(!utilityVerifyRequestField.isValidField(title) ||
                !utilityVerifyRequestField.isValidField(url) ||
                !utilityVerifyRequestField.isValidField(level)) {
            throw new HttpBadRequestException("Los campos titulo, url y nivel son requeridos");
        }

        Reference reference = mapperReference.transferibleAEntidad(title, url);

        reference.setLevelId(accessLevel.obtener(level)
                .orElseThrow(()-> new HttpNotFoundException("El Nivel no existe.")));

        if(accessReference.existeTituloEnNivel(reference.getLevelId(), reference.getTitle())) {
            throw new HttpBadRequestException("Ya existe una referencia con ese título en este nivel.");
        }

        String description = DTOCreateReference.getDescription();
        if(utilityVerifyRequestField.isValidField(description)) {
            reference.setDescription(description);
        }

        reference = accessReference.persistir(reference)
                .orElseThrow(()-> new HttpInternalServerException("Problemas al persistir nueva Referencia."));

        return mapperReference.entidadATransferible(reference);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public DTOReference update(DTOUpdateReference DTOUpdateReference) {

        Integer referenceID = DTOUpdateReference.getReferenceId();
        if(!utilityVerifyRequestField.isValidField(referenceID)) {
            throw new HttpBadRequestException("El campo identificador es requerido.");
        }

        Reference reference = accessReference.obtener(referenceID)
                .orElseThrow(()-> new HttpNotFoundException("La Referencia no existe"));

        Integer levelId = DTOUpdateReference.getLevel();
        String title = DTOUpdateReference.getTitle();
        String url = DTOUpdateReference.getUrl();
        String description = DTOUpdateReference.getDescription();
        if(!utilityVerifyRequestField.isValidField(levelId) &&
                !utilityVerifyRequestField.isValidField(title) &&
                !utilityVerifyRequestField.isValidField(url) &&
                !utilityVerifyRequestField.isValidField(description)) {
            throw new HttpBadRequestException("Sin campos que update");
        }

        if(utilityVerifyRequestField.isValidField(levelId)) {
            reference.setLevelId(accessLevel.obtener(levelId)
                    .orElseThrow( ()-> new HttpNotFoundException("El nivel no existe.") ));
        }

        if(utilityVerifyRequestField.isValidField(title)) {
            if(accessReference.existeTituloEnNivel(reference.getLevelId(), title)) {
                throw new HttpBadRequestException("Ya existe una referencia con ese título en este nivel.");
            } else {
                reference.setTitle(title);
            };
        }

        if(utilityVerifyRequestField.isValidField(url)) {
            reference.setUrl(url);
        }

        if(utilityVerifyRequestField.isValidField(description)) {
            reference.setDescription(description);
        }

        reference = accessReference.persistir(reference)
                .orElseThrow(()-> new HttpInternalServerException("Problemas al persistir actualización de Referencia."));

        return mapperReference.entidadATransferible(reference);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void eliminar(Integer identificador) {

        if(!accessReference.eliminar(identificador)) {
            throw new HttpNotFoundException("Referencia a eliminar no existe");
        };

    }

}
