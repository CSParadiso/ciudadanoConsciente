package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

public class DTOCreateBatchAnswer {

    @Schema(description = "")
    private Integer userId;

    private List<DTOBatchAnswer> answers;

    public DTOCreateBatchAnswer() {
    }

    public List<DTOBatchAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<DTOBatchAnswer> answers) {
        this.answers = answers;
    }

    public static class DTOBatchAnswer {

        @Schema(description = "")
        private Boolean status;

        @Schema(description = "")
        private Integer activity;

        public DTOBatchAnswer() {
        }

        public Integer getActivity() {
            return activity;
        }

        public void setActivity(Integer activity) {
            this.activity = activity;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
