package ciudadano.consciente.dto;

import jakarta.ws.rs.FormParam;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOCreateActivityTypeVersion {

    @FormParam("activityTypeId")
    @Schema(defaultValue = "", required = true)
    private Integer activityTypeId;

    @FormParam("model")
    @Schema(defaultValue = "", required = true)
    private byte[] model;

    @FormParam("template")
    @Schema(defaultValue = "", required = true)
    private byte[] template;

    @FormParam("readme")
    @Schema(defaultValue = "", required = true)
    private byte[] readme;

    @FormParam("thumbnail")
    @Schema(defaultValue = "", required = true)
    private byte[] thumbnail;

    public Integer getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(Integer activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public byte[] getModel() {
        return model;
    }

    public void setModel(byte[] model) {
        this.model = model;
    }

    public byte[] getTemplate() {
        return template;
    }

    public void setTemplate(byte[] template) {
        this.template = template;
    }

    public byte[] getReadme() {
        return readme;
    }

    public void setReadme(byte[] readme) {
        this.readme = readme;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }
}
