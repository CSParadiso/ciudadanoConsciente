package ciudadano.consciente.dto;

import jakarta.ws.rs.FormParam;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.jboss.resteasy.reactive.RestForm;

public class DTOCreateImage {

    @FormParam("content")
    @Schema(defaultValue = "", required = true)
    private Integer content;

    @FormParam("imageName")
    @Schema(defaultValue = "", required = true)
    private String imageName;

    @FormParam("image")
    @Schema(defaultValue = "", required = true)
    private byte[] image;

    public Integer getContent() {
        return content;
    }

    public void setContent(Integer content) {
        this.content = content;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
