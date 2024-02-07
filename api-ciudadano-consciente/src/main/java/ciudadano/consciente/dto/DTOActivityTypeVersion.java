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
    private LocalDate stagedDate;

    @Schema(defaultValue ="")
    private LocalDate lastModifiedStatusDate;

    @Schema(defaultValue = "")
    private String user;

    @Schema(defaultValue = "")
    private String repo;

    @Schema(defaultValue = "")
    private String path;

    @Schema(defaultValue = "")
    private String shaModel;

    @Schema(defaultValue = "")
    private String shaTemplate;

    @Schema(defaultValue = "")
    private String shaReadme;

    @Schema(defaultValue = "")
    private String shaThumbnail;

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

    public void setPath(String path) {
        this.path = path;
    }

    public String getShaModel() {
        return shaModel;
    }

    public void setShaModel(String shaModel) {
        this.shaModel = shaModel;
    }

    public String getShaTemplate() {
        return shaTemplate;
    }

    public void setShaTemplate(String shaTemplate) {
        this.shaTemplate = shaTemplate;
    }

    public String getShaReadme() {
        return shaReadme;
    }

    public void setShaReadme(String shaReadme) {
        this.shaReadme = shaReadme;
    }

    public String getShaThumbnail() {
        return shaThumbnail;
    }

    public void setShaThumbnail(String shaThumbnail) {
        this.shaThumbnail = shaThumbnail;
    }
}
