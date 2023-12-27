package ciudadano.consciente.dto;

import ciudadano.consciente.model.Level;
import ciudadano.consciente.model.Organization;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOLevel {

    @Schema
    private Integer levelId;

    @Schema
    private String name;

    @Schema
    private String description;

    @Schema
    private Organization organizationId;

    @Schema
    private Level parent;

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

    public Organization getOrganization() {
        return organizationId;
    }

    public void setOrganization(Organization organization) {
        this.organizationId = organization;
    }

    public Level getParent() {
        return parent;
    }

    public void setParent(Level parent) {
        this.parent = parent;
    }
}
