package ciudadano.consciente.transferible;

import jakarta.annotation.Nullable;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleActualizarNivel {

    @Schema(defaultValue = "null")
    private Integer levelId;

    @Schema(defaultValue = "null")
    private String name;
    @Schema(defaultValue = "null")
    private String description;

    @Schema(defaultValue = "null")
    private Integer organization;
    @Schema(defaultValue = "null")
    private Integer parent;

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
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
