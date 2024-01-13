package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOUpdateConcern {

    @Schema(defaultValue = "")
    private Integer concernId;

    @Schema(defaultValue = "")
    private String description;

    @Schema(defaultValue = "")
    private String explanation;

    @Schema(defaultValue = "")
    private Integer user;

    public Integer getConcernId() {
        return concernId;
    }

    public void setConcernId(Integer concernId) {
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

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }
}
