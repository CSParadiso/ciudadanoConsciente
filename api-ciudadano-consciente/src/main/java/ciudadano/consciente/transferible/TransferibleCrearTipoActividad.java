package ciudadano.consciente.transferible;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleCrearTipoActividad {

    @Schema(defaultValue = "null")
    private String name;

    @Schema(defaultValue = "null")
    private String description;

    @Schema(defaultValue = "null")
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

    public String getFunctionalTemplateUrl() {
        return functionalTemplateUrl;
    }

    public void setFunctionalTemplateUrl(String functionalTemplateUrl) {
        this.functionalTemplateUrl = functionalTemplateUrl;
    }

}
