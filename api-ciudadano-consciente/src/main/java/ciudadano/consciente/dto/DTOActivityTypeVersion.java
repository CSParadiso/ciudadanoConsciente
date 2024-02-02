package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

public class DTOActivityTypeVersion {

    @Schema(defaultValue = "")
    private Integer activityTypeVersionId;

    @Schema(defaultValue = "")
    private Integer activityTypeId;

    @Schema(defaultValue = "")
    private Integer activityTypeVersionStatusId;

    @Schema(defaultValue = "")
    private Integer versionNumber;

    @Schema(defaultValue = "")
    private LocalDate stagedDate;

    @Schema(defaultValue ="")
    private LocalDate lastModifiedStatusDate;

    @Schema(defaultValue = "")
    private String githubUser;

    @Schema(defaultValue = "")
    private String githubRepo;

    @Schema(defaultValue = "")
    private String githubPath;

    @Schema(defaultValue = "")
    private String githubShaModel;

    @Schema(defaultValue = "")
    private String githubShaTemplate;

    @Schema(defaultValue = "")
    private String githubShaReadme;

    @Schema(defaultValue = "")
    private String githubShaThumbnail;

    public Integer getActivityTypeVersionId() {
        return activityTypeVersionId;
    }

    public void setActivityTypeVersionId(Integer activityTypeVersionId) {
        this.activityTypeVersionId = activityTypeVersionId;
    }

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

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public LocalDate getStagedDate() {
        return stagedDate;
    }

    public void setStagedDate(LocalDate stagedDate) {
        this.stagedDate = stagedDate;
    }

    public LocalDate getLastModifiedStatusDate() {
        return lastModifiedStatusDate;
    }

    public void setLastModifiedStatusDate(LocalDate lastModifiedStatusDate) {
        this.lastModifiedStatusDate = lastModifiedStatusDate;
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
