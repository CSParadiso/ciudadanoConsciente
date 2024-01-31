package ciudadano.consciente.utility;

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

}
