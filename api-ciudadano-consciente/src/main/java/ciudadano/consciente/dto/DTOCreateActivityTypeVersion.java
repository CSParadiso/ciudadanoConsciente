package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOCreateActivityTypeVersion {

    @Schema(defaultValue = "", required = true)
    private Integer activityTypeId;

    @Schema(defaultValue = "", required = true)
    private String user;

    @Schema(defaultValue = "", required = true)
    private String repo;

    @Schema(defaultValue = "", required = true)
    private String path;

    @Schema(defaultValue = "main", required = true)
    private String branch;

    public Integer getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(Integer activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getPath() {
        return path;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
