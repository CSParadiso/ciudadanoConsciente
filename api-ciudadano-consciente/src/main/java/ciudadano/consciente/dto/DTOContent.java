package ciudadano.consciente.dto;

import ciudadano.consciente.model.Organization;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

public class DTOContent {

    private Integer contentId;

    private Integer activityTypeVersionId;

    private Integer creator;

    private Integer organization;

    private boolean publicContent;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JdbcTypeCode(SqlTypes.JSON) // To automatically use the table as jsonb
    private String model;

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getActivityTypeVersionId() {
        return activityTypeVersionId;
    }

    public void setActivityTypeVersionId(Integer activityTypeVersionId) {
        this.activityTypeVersionId = activityTypeVersionId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getOrganization() {
        return organization;
    }

    public void setOrganization(Integer organization) {
        this.organization = organization;
    }

    public boolean isPublicContent() {
        return publicContent;
    }

    public void setPublicContent(boolean publicContent) {
        this.publicContent = publicContent;
    }
}
