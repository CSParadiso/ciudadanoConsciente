package ciudadano.consciente.dto;

import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOUpdateOrganization {

    @NotNull(message = "Organization cannot be null.")
    @Schema(defaultValue = "")
    private Integer organizationId;

    @Schema(defaultValue = "")
    private String email;
    @Schema(defaultValue = "")
    private String description;

    public @NotNull(message = "Organization cannot be null.") Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(@NotNull(message = "Organization cannot be null.") Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
