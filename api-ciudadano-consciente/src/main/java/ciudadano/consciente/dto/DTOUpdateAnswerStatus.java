package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Deprecated(since = "1.0.3. The answers should not be modified.")
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
