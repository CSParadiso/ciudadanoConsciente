package ciudadano.consciente.dto;

import ciudadano.consciente.model.Organization;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOUserRoleLevel {

    @Schema
    private Integer urlId;

    @Schema
    private DTOUser user;

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

    public DTOUser getUser() {
        return user;
    }

    public void setUser(DTOUser user) {
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
