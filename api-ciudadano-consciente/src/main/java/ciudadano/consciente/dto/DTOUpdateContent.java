package ciudadano.consciente.dto;

import ciudadano.consciente.model.Organization;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.FormParam;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOUpdateContent {

    @NotNull(message = "Content cannot be null.")
    @Min(1)
    @FormParam("content")
    @Schema(defaultValue = "", required = true)
    private Integer content;

    @FormParam("model")
    @Schema(defaultValue = "", required = false)
    private byte[] model;

    @Size(min = 1, max = 140)
    @FormParam("description")
    @Schema(defaultValue = "", required = false)
    private String description;

    @FormParam("publicContent")
    @Schema(defaultValue = "false", required = false)
    private Boolean publicContent;

    public @NotNull(message = "Content cannot be null.") @Min(1) Integer getContent() {
        return content;
    }

    public void setContent(@NotNull(message = "Content cannot be null.") @Min(1) Integer content) {
        this.content = content;
    }

    public byte[] getModel() {
        return model;
    }

    public void setModel(byte[] model) {
        this.model = model;
    }

    public @Size(min = 1, max = 140) String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 1, max = 140) String description) {
        this.description = description;
    }

    public Boolean getPublicContent() {
        return publicContent;
    }

    public void setPublicContent(Boolean publicContent) {
        this.publicContent = publicContent;
    }
}
