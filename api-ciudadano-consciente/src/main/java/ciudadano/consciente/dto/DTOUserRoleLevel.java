package ciudadano.consciente.dto;

import ciudadano.consciente.model.Level;
import ciudadano.consciente.model.Role;
import ciudadano.consciente.model.User;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOUserRoleLevel {

    @Schema
    private Integer urlId;

    @Schema
    private User user;

    @Schema
    private Role role;

    @Schema
    private Level level;

    public Integer getUrlId() {
        return urlId;
    }

    public void setUrlId(Integer urlId) {
        this.urlId = urlId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
