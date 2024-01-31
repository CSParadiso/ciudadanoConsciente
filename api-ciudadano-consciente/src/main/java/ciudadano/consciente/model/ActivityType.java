package ciudadano.consciente.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

@Entity
@Table(schema = "app", name = "activity_types")
public class ActivityType {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_type_id")
    @Id
    private Integer activityTypeId;

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator", referencedColumnName = "user_id")
    private User creator;

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

    public Integer getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(Integer activityTypeId) {
        this.activityTypeId = activityTypeId;
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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
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
