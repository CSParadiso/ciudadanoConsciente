package ciudadano.consciente.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(schema = "app", name = "activity_types_version",
        uniqueConstraints = {
                @UniqueConstraint( // We cant have a version with the same identifiers of GithubMetadata
                        name = "activity_types_version_username_repo_branch_directory_path__key",
                        columnNames = {"username", "repo", "branch", "directory_path", "sha_commit", "activity_type_id"}
                )
        })
public class ActivityTypeVersion {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "activity_type_version_id")
    private Integer activityTypeVersionId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_type_id", referencedColumnName = "activity_type_id")
    private ActivityType activityTypeId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_type_version_status_id", referencedColumnName = "activity_type_version_status_id")
    private ActivityTypeVersionStatus activityTypeVersionStatusId;

    @Column(name = "version_number")
    private Integer versionNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "version_server", referencedColumnName = "version_server_id")
    private VersionServer versionServer;

    @Column(name = "staged_date")
    private LocalDate stagedDate;

    @Column(name = "last_modified_status_date")
    private LocalDate lastModifiedStatusDate;

    @Column(name = "username")
    private String user;

    private String repo;

    private String branch;

    @Column(name = "directory_path")
    private String path;

    @Column(name = "sha_commit")
    private String shaCommit;

    @Column(name = "model_download_url")
    private String modelDownloadUrl;

    @Column(name = "template_download_url")
    private String templateDownloadUrl;

    @Column(name = "readme_download_url")
    private String readmeDownloadUrl;

    @Column(name = "thumbnail_download_url")
    private String thumbnailDownloadUrl;

    public ActivityTypeVersion() {
        this.stagedDate = LocalDate.now();
        this.lastModifiedStatusDate = LocalDate.now();
    }

    public Integer getActivityTypeVersionId() {
        return activityTypeVersionId;
    }

    public void setActivityTypeVersionId(Integer activityTypeVersionId) {
        this.activityTypeVersionId = activityTypeVersionId;
    }

    public ActivityType getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(ActivityType activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public ActivityTypeVersionStatus getActivityTypeVersionStatusId() {
        return activityTypeVersionStatusId;
    }

    public void setActivityTypeVersionStatusId(ActivityTypeVersionStatus activityTypeVersionStatusId) {
        this.activityTypeVersionStatusId = activityTypeVersionStatusId;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public VersionServer getVersionServer() {
        return versionServer;
    }

    public void setVersionServer(VersionServer versionServer) {
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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
