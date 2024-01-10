package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

public class DTOCreateAnswer {

    @Schema(description = "")
    private Integer activity;

    @Schema(description = "")
    private Integer userId;

    @Schema(description = "")
    private Integer answersStatus;

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAnswersStatus() {
        return answersStatus;
    }

    public void setAnswersStatus(Integer answersStatus) {
        this.answersStatus = answersStatus;
    }
}
