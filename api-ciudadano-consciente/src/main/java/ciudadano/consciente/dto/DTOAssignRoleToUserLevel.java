package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOAssignRoleToUserLevel {

    @Schema(defaultValue = "")
    private Integer user;

    @Schema(defaultValue = "")
    private Integer role;

    @Schema(defaultValue = "")
    private Integer level;

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

    public void setLevel(Integer levelId) {
        this.level = levelId;
    }
}
