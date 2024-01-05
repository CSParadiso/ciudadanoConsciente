package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOUpdateRoleUserLevel {

    @Schema
    private Integer urlId;

    @Schema
    private Integer user;

    @Schema
    private Integer role;

    @Schema
    private Integer level;

    public Integer getUrlId() {
        return urlId;
    }

    public void setUrlId(Integer urlId) {
        this.urlId = urlId;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
