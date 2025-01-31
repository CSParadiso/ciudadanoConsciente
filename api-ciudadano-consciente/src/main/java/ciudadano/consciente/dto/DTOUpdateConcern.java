package ciudadano.consciente.dto;

import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOUpdateConcern {

    @NotNull
    @Schema(defaultValue = "", required = true)
    private Integer concernId;

    @Schema(defaultValue = "")
    private String description;

    @Schema(defaultValue = "")
    private String explanation;

    public @NotNull Integer getConcernId() {
        return concernId;
    }

    public void setConcernId(@NotNull Integer concernId) {
        this.concernId = concernId;
    }

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
}
