package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.lang.model.type.NullType;

public class DTOUpdateActivityType {

    @Schema(defaultValue = "")
    private Integer activityTypeId;

    @Schema(defaultValue = "")
    private String name;

    @Schema(defaultValue = "")
    private String description;

    @Schema(defaultValue = "")
    private String functionalTemplateUrl;

    public Integer getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(Integer activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFunctionalTemplateUrl() {
        return functionalTemplateUrl;
    }

    public void setFunctionalTemplateUrl(String functionalTemplateUrl) {
        this.functionalTemplateUrl = functionalTemplateUrl;
    }
}
