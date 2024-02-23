package ciudadano.consciente.utility;

import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.RequestScoped;

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

}
