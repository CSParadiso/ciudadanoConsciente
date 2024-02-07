package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOVerifiedFilesFromVersionServer {

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
