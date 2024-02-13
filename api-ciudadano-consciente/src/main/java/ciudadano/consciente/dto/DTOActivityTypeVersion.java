package ciudadano.consciente.dto;

import jakarta.persistence.GeneratedValue;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.time.LocalDate;

public class DTOActivityTypeVersion {

    @Schema(defaultValue = "")
    private Integer activityTypeVersionId;

    @Schema(defaultValue = "")
    private Integer activityTypeId;

    @Schema(defaultValue = "")
    private Integer activityTypeVersionStatusId;

    @GeneratedValue
    @Schema(defaultValue = "")
    private Integer versionNumber;

    @Schema(defaultValue = "")
    private Integer versionServer;

    @Schema(defaultValue = "")
    private LocalDate stagedDate;

    @Schema(defaultValue ="")
    private LocalDate lastModifiedStatusDate;

    @Schema(defaultValue = "")
    private String user;

    @Schema(defaultValue = "")
    private String repo;

    @Schema(defaultValue = "")
    private String branch;

    @Schema(defaultValue = "")
    private String path;

    @Schema(defaultValue = "")
    private String shaCommit;

    @Schema(defaultValue = "")
    private String modelDownloadUrl;

    @Schema(defaultValue = "")
    private String templateDownloadUrl;

    @Schema(defaultValue = "")
    private String readmeDownloadUrl;

    @Schema(defaultValue = "")
    private String thumbnailDownloadUrl;

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

    public Integer getVersionServer() {
        return versionServer;
    }

    public void setVersionServer(Integer versionServer) {
        this.versionServer = versionServer;
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

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getShaCommit() {
        return shaCommit;
    }

    public void setShaCommit(String shaCommit) {
        this.shaCommit = shaCommit;
    }

    public String getModelDownloadUrl() {
        return modelDownloadUrl;
    }

    public void setModelDownloadUrl(String modelDownloadUrl) {
        this.modelDownloadUrl = modelDownloadUrl;
    }

    public String getTemplateDownloadUrl() {
        return templateDownloadUrl;
    }

    public void setTemplateDownloadUrl(String templateDownloadUrl) {
        this.templateDownloadUrl = templateDownloadUrl;
    }

    public String getReadmeDownloadUrl() {
        return readmeDownloadUrl;
    }

    public void setReadmeDownloadUrl(String readmeDownloadUrl) {
        this.readmeDownloadUrl = readmeDownloadUrl;
    }

    public String getThumbnailDownloadUrl() {
        return thumbnailDownloadUrl;
    }

    public void setThumbnailDownloadUrl(String thumbnailDownloadUrl) {
        this.thumbnailDownloadUrl = thumbnailDownloadUrl;
    }
}
