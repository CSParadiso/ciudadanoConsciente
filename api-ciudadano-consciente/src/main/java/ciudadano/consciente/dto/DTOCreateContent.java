package ciudadano.consciente.dto;

import ciudadano.consciente.model.Organization;
import jakarta.ws.rs.FormParam;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOCreateContent {

    @FormParam("activityTypeVersionId")
    @Schema(defaultValue = "", required = true)
    private Integer activityTypeVersionId;

    @FormParam("creator")
    @Schema(defaultValue = "", required = true)
    private Integer creator;

    @FormParam("organization")
    @Schema(defaultValue = "")
    private Integer organization;

    @FormParam("public")
    @Schema(defaultValue = "false", required = true)
    private boolean publicContent;

    @FormParam("model")
    @Schema(defaultValue = "", required = true)
    private byte[] model;

    public Integer getActivityTypeVersionId() {
        return activityTypeVersionId;
    }

    public void setActivityTypeVersionId(Integer activityTypeVersionId) {
        this.activityTypeVersionId = activityTypeVersionId;
    }

    public byte[] getModel() {
        return model;
    }

    public void setModel(byte[] model) {
        this.model = model;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getOrganization() {
        return organization;
    }

    public void setOrganization(Integer organization) {
        this.organization = organization;
    }

    public boolean isPublicContent() {
        return publicContent;
    }

    public void setPublicContent(boolean publicContent) {
        this.publicContent = publicContent;
    }
}
