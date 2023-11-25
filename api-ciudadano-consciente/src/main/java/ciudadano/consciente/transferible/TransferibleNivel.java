package ciudadano.consciente.transferible;

import ciudadano.consciente.modelo.Nivel;
import ciudadano.consciente.modelo.Organizacion;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleNivel {

    @Schema
    private Integer levelId;

    @Schema
    private String name;

    @Schema
    private String description;

    @Schema
    private Organizacion organizationId;

    @Schema
    private Nivel parent;

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

    public Organizacion getOrganization() {
        return organizationId;
    }

    public void setOrganization(Organizacion organization) {
        this.organizationId = organization;
    }

    public Nivel getParent() {
        return parent;
    }

    public void setParent(Nivel parent) {
        this.parent = parent;
    }
}
