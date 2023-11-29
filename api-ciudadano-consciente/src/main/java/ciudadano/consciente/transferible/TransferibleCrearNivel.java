package ciudadano.consciente.transferible;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleCrearNivel {

    @Schema(required = true, defaultValue = "null")
    private String name;
    @Schema(required = true, defaultValue = "null")
    private String description;

    @Schema(required = true, defaultValue = "null")
    private Integer organization;
    @Schema(required = true, defaultValue = "null")
    private Integer parent;

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

    public Integer getOrganization() {
        return organization;
    }

    public void setOrganization(Integer organization) {
        this.organization = organization;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }
}
