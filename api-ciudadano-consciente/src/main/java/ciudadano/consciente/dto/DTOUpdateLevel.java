package ciudadano.consciente.dto;

import ciudadano.consciente.model.Organization;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOUpdateLevel {

    @Schema(defaultValue = "")
    private Integer levelId;

    @Schema(defaultValue = "")
    private String name;

    @Schema(defaultValue = "")
    private String description;

    //@Schema(defaultValue = "")
    //private Integer organization;

    @Schema(defaultValue = "")
    private Integer parent;

    @Schema
    private Boolean hidden;

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

    //public Integer getOrganization() {
    //    return organization;
    //}

    //public void setOrganization(Integer organization) {
    //    this.organization = organization;
    //}

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }
}
