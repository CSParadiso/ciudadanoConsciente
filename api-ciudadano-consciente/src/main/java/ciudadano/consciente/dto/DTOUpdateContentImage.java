package ciudadano.consciente.dto;

import ciudadano.consciente.model.Organization;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.FormParam;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOUpdateContentImage {

    @NotNull(message = "Content cannot be null.")
    @Min(1)
    @FormParam("content")
    @Schema(defaultValue = "", required = true)
    private Integer content;

    @NotNull(message = "Image cannot be null.")
    @Min(1)
    @FormParam("image")
    @Schema(defaultValue = "", required = true)
    private Integer image;

    @NotNull(message = "Image file cannot be null.")
    @FormParam("imageFile")
    @Schema(defaultValue = "", required = true)
    private byte[] imageFile;

    public @NotNull(message = "Content cannot be null.") @Min(1) Integer getContent() {
        return content;
    }

    public void setContent(@NotNull(message = "Content cannot be null.") @Min(1) Integer content) {
        this.content = content;
    }

    public @NotNull(message = "Image cannot be null.") @Min(1) Integer getImage() {
        return image;
    }

    public void setImage(@NotNull(message = "Image cannot be null.") @Min(1) Integer image) {
        this.image = image;
    }

    public @NotNull(message = "Image file cannot be null.") byte[] getImageFile() {
        return imageFile;
    }

    public void setImageFile(@NotNull(message = "Image file cannot be null.") byte[] imageFile) {
        this.imageFile = imageFile;
    }
}
