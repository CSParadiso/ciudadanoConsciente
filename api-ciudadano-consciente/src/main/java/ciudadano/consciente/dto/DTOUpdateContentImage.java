package ciudadano.consciente.dto;

import jakarta.ws.rs.FormParam;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOUpdateContentImage {

    @FormParam("content")
    @Schema(defaultValue = "", required = true)
    private Integer content;

    @FormParam("image")
    @Schema(defaultValue = "", required = true)
    private Integer image;

    @FormParam("imageFile")
    @Schema(defaultValue = "", required = true)
    private byte[] imageFile;

    public Integer getContent() {
        return content;
    }

    public void setContent(Integer content) {
        this.content = content;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public byte[] getImageFile() {
        return imageFile;
    }

    public void setImageFile(byte[] imageFile) {
        this.imageFile = imageFile;
    }
}
