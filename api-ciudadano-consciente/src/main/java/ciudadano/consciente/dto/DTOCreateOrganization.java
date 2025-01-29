package ciudadano.consciente.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOCreateOrganization {

    @NotNull(message = "Name cannot be null.")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters.")
    @Schema(defaultValue = "")
    private String name;

    @NotNull(message = "Email cannot be null.")
    @Size(min = 1, max = 100, message = "Email must be between 1 and 100 characters.")
    @Schema(defaultValue = "")
    private String email;

    @Size(min = 1, max = 140, message = "Description must be between 1 and 100 characters.")
    @Schema(defaultValue = "")
    private String description;

    public @NotNull(message = "Name cannot be null.") @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters.") String getName() {
        return name;
    }

    public void setName(@NotNull(message = "Name cannot be null.") @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters.") String name) {
        this.name = name;
    }

    public @NotNull(message = "Email cannot be null.") @Size(min = 1, max = 100, message = "Email must be between 1 and 100 characters.") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "Email cannot be null.") @Size(min = 1, max = 100, message = "Email must be between 1 and 100 characters.") String email) {
        this.email = email;
    }

    public @Size(min = 1, max = 140, message = "Description must be between 1 and 100 characters.") String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 1, max = 140, message = "Description must be between 1 and 100 characters.") String description) {
        this.description = description;
    }
}
