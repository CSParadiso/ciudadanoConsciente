package ciudadano.consciente.dto;

import ciudadano.consciente.model.Organization;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.FormParam;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOCreateContent {

    @NotNull(message = "Activity Type Version ID cannot be null.")
    @Min(value = 1, message = "Activity Type Version should be bigger than 0.")
    @FormParam("activityTypeVersionId")
    @Schema(defaultValue = "", required = true)
    private Integer activityTypeVersionId;

    @Min(value = 1, message = "Organization should be bigger than 0.")
    @FormParam("organization")
    @Schema(defaultValue = "")
    private Integer organization;

    @NotNull(message = "Public content flag cannot be null.")
    @FormParam("publicContent")
    @Schema(defaultValue = "false", required = true)
    private boolean publicContent;

    @NotNull(message = "Model cannot be null.")
    @FormParam("model")
    @Schema(defaultValue = "", required = true)
    private byte[] model;

    @NotNull(message = "Description cannot be null.")
    @Size(min = 1, max = 140, message = "Description must be between 1 and 140 characters.")
    @FormParam("description")
    @Schema(defaultValue = "", required = true)
    private String description;

    public @NotNull(message = "Activity Type Version ID cannot be null.") @Min(value = 1, message = "Activity Type Version should be bigger than 0.") Integer getActivityTypeVersionId() {
        return activityTypeVersionId;
    }

    public void setActivityTypeVersionId(@NotNull(message = "Activity Type Version ID cannot be null.") @Min(value = 1, message = "Activity Type Version should be bigger than 0.") Integer activityTypeVersionId) {
        this.activityTypeVersionId = activityTypeVersionId;
    }

    public @Min(value = 1, message = "Organization should be bigger than 0.") Integer getOrganization() {
        return organization;
    }

    public void setOrganization(@Min(value = 1, message = "Organization should be bigger than 0.") Integer organization) {
        this.organization = organization;
    }

    public @NotNull(message = "Public content flag cannot be null.") boolean isPublicContent() {
        return publicContent;
    }

    public void setPublicContent(@NotNull(message = "Public content flag cannot be null.") boolean publicContent) {
        this.publicContent = publicContent;
    }

    public @NotNull(message = "Model cannot be null.") byte[] getModel() {
        return model;
    }

    public void setModel(@NotNull(message = "Model cannot be null.") byte[] model) {
        this.model = model;
    }

    public @NotNull(message = "Description cannot be null.") @Size(min = 1, max = 140, message = "Description must be between 1 and 140 characters.") String getDescription() {
        return description;
    }

    public void setDescription(@NotNull(message = "Description cannot be null.") @Size(min = 1, max = 140, message = "Description must be between 1 and 140 characters.") String description) {
        this.description = description;
    }
}
