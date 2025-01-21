package ciudadano.consciente.dto;

import ciudadano.consciente.model.Organization;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.FormParam;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOCreateImage {

    @NotNull(message = "Content cannot be null.")
    @Min(value = 1, message = "Content should be bigger than 0.")
    @FormParam("content")
    @Schema(defaultValue = "", required = true)
    private Integer content;

    @NotNull(message = "Image name cannot be null.")
    @Size(min = 1, message = "Image name should be between 0 and 50 characters.")
    @FormParam("imageName")
    @Schema(defaultValue = "", required = true)
    private String imageName;

    @NotNull(message = "Image file cannot be null.")
    @FormParam("image")
    @Schema(defaultValue = "", required = true)
    private byte[] image;

    public @NotNull(message = "Content cannot be null.") @Min(value = 1, message = "Content should be bigger than 0.") Integer getContent() {
        return content;
    }

    public void setContent(@NotNull(message = "Content cannot be null.") @Min(value = 1, message = "Content should be bigger than 0.") Integer content) {
        this.content = content;
    }

    public @NotNull(message = "Image name cannot be null.") @Size(min = 1, message = "Image name should be between 0 and 50 characters.") String getImageName() {
        return imageName;
    }

    public void setImageName(@NotNull(message = "Image name cannot be null.") @Size(min = 1, message = "Image name should be between 0 and 50 characters.") String imageName) {
        this.imageName = imageName;
    }

    public @NotNull(message = "Image file cannot be null.") byte[] getImage() {
        return image;
    }

    public void setImage(@NotNull(message = "Image file cannot be null.") byte[] image) {
        this.image = image;
    }
}
