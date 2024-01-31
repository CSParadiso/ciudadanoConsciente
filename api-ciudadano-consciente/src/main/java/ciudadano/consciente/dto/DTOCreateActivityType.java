package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOCreateActivityType {

    @Schema(defaultValue = "")
    private String name;

    @Schema(defaultValue = "")
    private String description;

    @Schema(defaultValue = "")
    private Integer creator;

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

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
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
