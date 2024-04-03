package ciudadano.consciente.dto;

import ciudadano.consciente.model.Organization;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOCreateConcern {

    @Schema(defaultValue = "")
    private String description;

    @Schema(defaultValue = "")
    private String explanation;

    @Schema(defaultValue = "")
    private Integer user;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }
}
