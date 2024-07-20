package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.sql.Date;
import java.time.LocalDate;

public class DTOLevelPathUsedRecentlyByUser {

    @Schema
    private Integer levelId;

    @Schema
    private String name;

    @Schema
    private String description;

    @Schema
    private Integer organizationId;

    @Schema
    private Date created;

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
        return organizationId;
    }

    public void setOrganization(Integer organization) {
        this.organizationId = organization;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
