package ciudadano.consciente.dto;

import ciudadano.consciente.model.Organization;
import jakarta.ws.rs.FormParam;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOCreateContent {

    @FormParam("activityTypeVersionId")
    @Schema(defaultValue = "", required = true)
    private Integer activityTypeVersionId;

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

}
