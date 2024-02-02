package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOUpdateActivityTypeVersion {

    @Schema(defaultValue = "")
    private Integer activityTypeVersionId;

    @Schema(defaultValue = "")
    private Integer activityTypeVersionStatusId;

    public Integer getActivityTypeVersionId() {
        return activityTypeVersionId;
    }

    public void setActivityTypeVersionId(Integer activityTypeVersionId) {
        this.activityTypeVersionId = activityTypeVersionId;
    }

    public Integer getActivityTypeVersionStatusId() {
        return activityTypeVersionStatusId;
    }

    public void setActivityTypeVersionStatusId(Integer activityTypeVersionStatusId) {
        this.activityTypeVersionStatusId = activityTypeVersionStatusId;
    }
}
