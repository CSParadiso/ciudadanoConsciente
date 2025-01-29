package ciudadano.consciente.dto;

import ciudadano.consciente.model.Organization;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOAssignRoleToUserLevel {

    @NotNull(message = "User cannot be null.")
    @Min(value = 1, message = "User must be greater than one.")
    @Schema(defaultValue = "", required = true)
    private Integer user;

    @NotNull(message = "Role cannot be null.")
    @Min(value = 1, message = "Role must be greater than one.")
    @Schema(defaultValue = "", required = true)
    private Integer role;

    @NotNull(message = "Level cannot be null.")
    @Min(value = 1, message = "Level must be greater than one.")
    @Schema(defaultValue = "", required = true)
    private Integer level;

    public @NotNull(message = "User cannot be null.") @Min(value = 1, message = "User must be greater than one.") Integer getUser() {
        return user;
    }

    public void setUser(@NotNull(message = "User cannot be null.") @Min(value = 1, message = "User must be greater than one.") Integer user) {
        this.user = user;
    }

    public @NotNull(message = "Role cannot be null.") @Min(value = 1, message = "Role must be greater than one.") Integer getRole() {
        return role;
    }

    public void setRole(@NotNull(message = "Role cannot be null.") @Min(value = 1, message = "Role must be greater than one.") Integer role) {
        this.role = role;
    }

    public @NotNull(message = "Level cannot be null.") @Min(value = 1, message = "Level must be greater than one.") Integer getLevel() {
        return level;
    }

    public void setLevel(@NotNull(message = "Level cannot be null.") @Min(value = 1, message = "Level must be greater than one.") Integer level) {
        this.level = level;
    }
}
