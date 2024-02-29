package ciudadano.consciente.utility;

import ciudadano.consciente.dto.DTOCreateImage;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.RequestScoped;

import java.util.List;

@RequestScoped
public class UtilityVerifyRequestField {

    public boolean isValidField(Integer field) {
        return field != null;
    }

    public boolean isValidField(String field) {
        return field != null &&  !"null".equals(field) && !field.trim().isEmpty();
    }

    public boolean isValidField(Boolean field) {
        return field != null;
    }

    public boolean isValidField(byte[] field) {
        return field != null && field.length != 0;
    }

    public boolean isValidField(JsonObject field) {
        return field != null;
    }

    public boolean isValidField(List<byte[]> images) {

        for(byte[] image : images) {
            if(image == null || image.length == 0) { return false; }
        }

        return true;

    }

}
