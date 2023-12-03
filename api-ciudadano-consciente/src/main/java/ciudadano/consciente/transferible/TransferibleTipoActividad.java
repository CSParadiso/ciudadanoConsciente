package ciudadano.consciente.transferible;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleTipoActividad {

    @Schema
    private Integer activityTypeId;

    @Schema
    private String name;

    @Schema
    private String description;

    @Schema
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
