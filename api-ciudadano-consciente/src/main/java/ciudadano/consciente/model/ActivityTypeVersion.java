package ciudadano.consciente.model;

import jakarta.enterprise.inject.Default;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(schema = "app", name = "activity_type_version",
        uniqueConstraints = {
                @UniqueConstraint( // We cant have a version with the same identifiers of Github
                        name = "unique_github_info",
                        columnNames = {"github_user", "github_repo", "github_path", "github_sha_model", "github_sha_template", "github_sha_readme", "github_sha_thumbnail"}
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

    @Column(name = "github_user")
    private String githubUser;

    @Column(name = "github_repo")
    private String githubRepo;

    @Column(name = "github_path")
    private String githubPath;

    @Column(name = "github_sha_model")
    private String githubShaModel;

    @Column(name = "github_sha_template")
    private String githubShaTemplate;

    @Column(name = "github_sha_readme")
    private String githubShaReadme;

    @Column(name = "github_sha_thumbnail")
    private String githubShaThumbnail;

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
