package ciudadano.consciente.model;

import jakarta.enterprise.inject.Default;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(schema = "app", name = "activity_type_version",
        uniqueConstraints = {
                @UniqueConstraint( // We cant have a version with the same identifiers of GithubMetadata
                        name = "unique_info",
                        columnNames = {"username", "repo", "directory_path", "sha_model", "sha_template", "sha_readme", "sha_thumbnail"}
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

    @Column(name = "staged_date")
    private LocalDate stagedDate;

    @Column(name = "last_modified_status_date")
    private LocalDate lastModifiedStatusDate;

    @Column(name = "username")
    private String user;

    @Column()
    private String repo;

    @Column(name = "directory_path")
    private String path;

    @Column(name = "sha_model")
    private String shaModel;

    @Column(name = "sha_template")
    private String shaTemplate;

    @Column(name = "sha_readme")
    private String shaReadme;

    @Column(name = "sha_thumbnail")
    private String shaThumbnail;

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
