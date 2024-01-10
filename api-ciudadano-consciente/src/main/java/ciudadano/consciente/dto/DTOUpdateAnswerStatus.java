package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOUpdateAnswerStatus {

    @Schema(defaultValue = "")
    private Integer answerStatusId;

    @Schema
    private Integer status;

    public Integer getAnswerStatusId() {
        return answerStatusId;
    }

    public void setAnswerStatusId(Integer answerStatusId) {
        this.answerStatusId = answerStatusId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
