package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOUpdateAnswersStatus {

    @Schema(defaultValue = "")
    private Integer answersStatusId;

    @Schema(defaultValue = "")
    private String title;

    @Schema(defaultValue = "")
    private String description;

    public Integer getAnswersStatusId() {
        return answersStatusId;
    }

    public void setAnswersStatusId(Integer answersStatusId) {
        this.answersStatusId = answersStatusId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
