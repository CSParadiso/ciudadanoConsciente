package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOUserRoleOrganization {

    @Schema
    private Integer uroId;

    @Schema
    private Integer user;

    @Schema
    private Integer role;

    @Schema
    private Integer organization;

    public Integer getUroId() {
        return uroId;
    }

    public void setUroId(Integer uroId) {
        this.uroId = uroId;
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

    public Integer getOrganization() {
        return organization;
    }

    public void setOrganization(Integer organization) {
        this.organization = organization;
    }
}
