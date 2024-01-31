package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOUpdateAnswerStatus {

    @Schema(defaultValue = "")
    private Integer answerStatusId;

    @Schema
    private Boolean status;

    public Integer getAnswerStatusId() {
        return answerStatusId;
    }

    public void setAnswerStatusId(Integer answerStatusId) {
        this.answerStatusId = answerStatusId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
