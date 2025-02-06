package ciudadano.consciente.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOVerifyOrganization {

    @NotNull(message = "Organization cannot be null.")
    @Min(1)
    @Schema(defaultValue = "")
    private Integer organizationId;

    @NotNull(message = "Token cannot be null.")
    @Size(min = 15, max = 15, message = "Token should have 15 characters.")
    @Schema(defaultValue = "")
    private String token;

    public @NotNull(message = "Organization cannot be null.") @Min(1) Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(@NotNull(message = "Organization cannot be null.") @Min(1) Integer organizationId) {
        this.organizationId = organizationId;
    }

    public @NotNull(message = "Token cannot be null.") @Size(min = 15, max = 15, message = "Token should have 15 characters.") String getToken() {
        return token;
    }

    public void setToken(@NotNull(message = "Token cannot be null.") @Size(min = 15, max = 15, message = "Token should have 15 characters.") String token) {
        this.token = token;
    }
}
