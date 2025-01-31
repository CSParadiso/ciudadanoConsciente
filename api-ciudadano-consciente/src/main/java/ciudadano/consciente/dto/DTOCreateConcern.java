package ciudadano.consciente.dto;

import ciudadano.consciente.model.Organization;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOCreateConcern {

    @NotNull(message = "Description cannot be null.")
    @Size(min = 1, max = 140)
    @Schema(defaultValue = "")
    private String description;

    @Schema(defaultValue = "")
    private String explanation;

    public @NotNull(message = "Description cannot be null.") @Size(min = 1, max = 140) String getDescription() {
        return description;
    }

    public void setDescription(@NotNull(message = "Description cannot be null.") @Size(min = 1, max = 140) String description) {
        this.description = description;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
