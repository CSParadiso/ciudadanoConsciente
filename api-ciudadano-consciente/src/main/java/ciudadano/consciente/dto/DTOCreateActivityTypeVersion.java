package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOCreateActivityTypeVersion {

    @Schema(defaultValue = "", required = true)
    private Integer activityTypeId;

    @Schema(defaultValue = "", required = true)
    private String user;

    @Schema(defaultValue = "", required = true)
    private String repo;

    @Schema(defaultValue = "", required = true)
    private String path;

    @Schema(defaultValue = "", required = true)
    private String commit;

    @Deprecated // Esto quizás puede generar inconvenientes porque deberíamos almacenarlo. FEATURE PAGA, ya que el repo es privado.
    @Schema(defaultValue = "")
    private String token;

    public Integer getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(Integer activityTypeId) {
        this.activityTypeId = activityTypeId;
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

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    @Deprecated
    public String getToken() {
        return token;
    }

    @Deprecated
    public void setToken(String token) {
        this.token = token;
    }
}
