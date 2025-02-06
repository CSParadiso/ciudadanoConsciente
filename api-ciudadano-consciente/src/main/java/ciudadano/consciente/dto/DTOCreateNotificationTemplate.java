package ciudadano.consciente.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.FormParam;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOCreateNotificationTemplate {

    @NotNull(message = "Title cannot be null.")
    @FormParam("title")
    @Size(min = 1, max = 50)
    @Schema(defaultValue = "", required = true)
    private String title;

    @NotNull(message = "Template cannot be null.")
    @FormParam("template")
    @Schema(defaultValue = "", required = true)
    private byte[] template;

    public @NotNull(message = "Title cannot be null.") @Size(min = 1, max = 50) String getTitle() {
        return title;
    }

    public void setTitle(@NotNull(message = "Title cannot be null.") @Size(min = 1, max = 50) String title) {
        this.title = title;
    }

    public @NotNull(message = "Template cannot be null.") byte[] getTemplate() {
        return template;
    }

    public void setTemplate(@NotNull(message = "Template cannot be null.") byte[] template) {
        this.template = template;
    }
}
