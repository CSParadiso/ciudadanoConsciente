package ciudadano.consciente.dto;

import jakarta.ws.rs.FormParam;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOUpdateContent {

    @FormParam("content")
    @Schema(defaultValue = "", required = true)
    private Integer content;

    @FormParam("model")
    @Schema(defaultValue = "", required = true)
    private byte[] model;

    public Integer getContent() {
        return content;
    }

    public void setContent(Integer content) {
        this.content = content;
    }

    public byte[] getModel() {
        return model;
    }

    public void setModel(byte[] model) {
        this.model = model;
    }
}
