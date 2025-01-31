package ciudadano.consciente.dto;

import ciudadano.consciente.model.Organization;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOOrganizationStatistics {

    // Los mismos nombres que en el modelo
    @Schema
    private Integer organizationId;

    @Schema
    private String name;

    @Schema
    private String email;

    @Schema
    private String description;

    @Schema
    private Integer moderators;

    @Schema
    private Integer divulgators;

    @Schema
    private Integer paths;

    @Schema
    private Integer levels;

    @Schema
    private Integer activities;

    @Schema
    private Integer contents;

    public DTOOrganizationStatistics(Organization organization, Integer moderators, Integer divulgators,
                                     Integer paths, Integer levels, Integer activities, Integer contents) {
        this.organizationId = organization.getOrganizationId();
        this.name = organization.getName();
        this.email = organization.getEmail();
        this.description = organization.getDescription();;
        this.moderators = moderators;
        this.divulgators = divulgators;
        this.paths = paths;
        this.levels = levels;
        this.activities = activities;
        this.contents = contents;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getModerators() {
        return moderators;
    }

    public void setModerators(Integer moderators) {
        this.moderators = moderators;
    }

    public Integer getDivulgators() {
        return divulgators;
    }

    public void setDivulgators(Integer divulgators) {
        this.divulgators = divulgators;
    }

    public Integer getPaths() {
        return paths;
    }

    public void setPaths(Integer paths) {
        this.paths = paths;
    }

    public Integer getLevels() {
        return levels;
    }

    public void setLevels(Integer levels) {
        this.levels = levels;
    }

    public Integer getActivities() {
        return activities;
    }

    public void setActivities(Integer activities) {
        this.activities = activities;
    }

    public Integer getContents() {
        return contents;
    }

    public void setContents(Integer contents) {
        this.contents = contents;
    }
}

