package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOCreateActivityTypeVersion {

    @Schema(defaultValue = "")
    private Integer activityTypeId;

    @Schema(defaultValue = "")
    private Integer activityTypeVersionStatusId;

    @Schema
    private String githubUser;

    @Schema
    private String githubRepo;

    @Schema
    private String githubPath;

    @Schema
    private String githubShaModel;

    @Schema
    private String githubShaTemplate;

    @Schema
    private String githubShaReadme;

    @Schema
    private String githubShaThumbnail;

    public Integer getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(Integer activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public Integer getActivityTypeVersionStatusId() {
        return activityTypeVersionStatusId;
    }

    public void setActivityTypeVersionStatusId(Integer activityTypeVersionStatusId) {
        this.activityTypeVersionStatusId = activityTypeVersionStatusId;
    }

    public String getGithubUser() {
        return githubUser;
    }

    public void setGithubUser(String githubUser) {
        this.githubUser = githubUser;
    }

    public String getGithubRepo() {
        return githubRepo;
    }

    public void setGithubRepo(String githubRepo) {
        this.githubRepo = githubRepo;
    }

    public String getGithubPath() {
        return githubPath;
    }

    public void setGithubPath(String githubPath) {
        this.githubPath = githubPath;
    }

    public String getGithubShaModel() {
        return githubShaModel;
    }

    public void setGithubShaModel(String githubShaModel) {
        this.githubShaModel = githubShaModel;
    }

    public String getGithubShaTemplate() {
        return githubShaTemplate;
    }

    public void setGithubShaTemplate(String githubShaTemplate) {
        this.githubShaTemplate = githubShaTemplate;
    }

    public String getGithubShaReadme() {
        return githubShaReadme;
    }

    public void setGithubShaReadme(String githubShaReadme) {
        this.githubShaReadme = githubShaReadme;
    }

    public String getGithubShaThumbnail() {
        return githubShaThumbnail;
    }

    public void setGithubShaThumbnail(String githubShaThumbnail) {
        this.githubShaThumbnail = githubShaThumbnail;
    }
}
