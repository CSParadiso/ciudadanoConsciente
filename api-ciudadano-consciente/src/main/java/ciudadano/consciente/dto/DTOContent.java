package ciudadano.consciente.dto;

import ciudadano.consciente.access.AccessImage;
import ciudadano.consciente.model.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

public class DTOContent {

    private Integer contentId;

    private Integer activityTypeVersionId;

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

}
