package ciudadano.consciente.transferible;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleAsignarRolUsuario {

    @Schema(defaultValue = "null")
    private Integer user;

    @Schema(defaultValue = "null")
    private Integer role;

    @Schema(defaultValue = "null")
    private Integer roleableEntity;

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

    public Integer getRoleableEntity() {
        return roleableEntity;
    }

    public void setRoleableEntity(Integer roleableEntity) {
        this.roleableEntity = roleableEntity;
    }
}
