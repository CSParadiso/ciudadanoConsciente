package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOCreateActivityType {

    @Schema(defaultValue = "")
    private String name;

    @Schema(defaultValue = "")
    private String description;

    @Schema(defaultValue = "")
    private Integer creator;

    @Schema(defaultValue = "")
    private String functionalTemplateUrl;

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

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getFunctionalTemplateUrl() {
        return functionalTemplateUrl;
    }

    public void setFunctionalTemplateUrl(String functionalTemplateUrl) {
        this.functionalTemplateUrl = functionalTemplateUrl;
    }

}
