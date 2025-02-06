package ciudadano.consciente.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.FormParam;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOUpdateNotificationTemplate {

    @FormParam("notificationTemplateId")
    @Schema(defaultValue = "", required = true)
    private Integer notificationTemplateId;

    @FormParam("title")
    @Schema(defaultValue = "")
    private String title;

    @FormParam("template")
    @Schema(defaultValue = "")
    private byte[] template;

    public Integer getNotificationTemplateId() {
        return notificationTemplateId;
    }

    public void setNotificationTemplateId(Integer notificationTemplateId) {
        this.notificationTemplateId = notificationTemplateId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getTemplate() {
        return template;
    }

    public void setTemplate(byte[] template) {
        this.template = template;
    }
}
