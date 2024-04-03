package ciudadano.consciente.dto;

import ciudadano.consciente.model.Organization;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOActivity {

    @Schema
    private Integer activityId;

    @Schema
    private String description;

    @Schema
    private Integer level;

    @Schema
    private Integer content;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getContent() {
        return content;
    }

    public void setContent(Integer content) {
        this.content = content;
    }

}
