package ciudadano.consciente.dto;

import jakarta.persistence.GeneratedValue;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.OffsetDateTime;

public class DTOActivityTypeVersion {

    @Schema(defaultValue = "")
    private Integer activityTypeVersionId;

    @Schema(defaultValue = "")
    private Integer activityTypeId;

    @Schema(defaultValue = "")
    private Integer activityTypeVersionStatusId;

    @GeneratedValue
    @Schema(defaultValue = "")
    private Integer versionNumber;

    @Schema(defaultValue = "")
    private OffsetDateTime stagedDate;

    @Schema(defaultValue ="")
    private OffsetDateTime lastModifiedStatusDate;

    @Schema(defaultValue = "")
    private String model;

    @Schema(defaultValue = "")
    private String template;

    @Schema(defaultValue = "")
    private String readme;

    public Integer getActivityTypeVersionId() {
        return activityTypeVersionId;
    }

    public void setActivityTypeVersionId(Integer activityTypeVersionId) {
        this.activityTypeVersionId = activityTypeVersionId;
    }

    public Integer getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(Integer activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public Integer getActivityTypeVersionStatusId() {
        return activityTypeVersionStatusId;
    }

    public void setActivityTypeVersionStatusId(Integer activityTypeVersionStatusId) {
        this.activityTypeVersionStatusId = activityTypeVersionStatusId;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public OffsetDateTime getStagedDate() {
        return stagedDate;
    }

    public void setStagedDate(OffsetDateTime stagedDate) {
        this.stagedDate = stagedDate;
    }

    public OffsetDateTime getLastModifiedStatusDate() {
        return lastModifiedStatusDate;
    }

    public void setLastModifiedStatusDate(OffsetDateTime lastModifiedStatusDate) {
        this.lastModifiedStatusDate = lastModifiedStatusDate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getReadme() {
        return readme;
    }

    public void setReadme(String readme) {
        this.readme = readme;
    }

}
