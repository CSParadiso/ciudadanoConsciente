package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOEntity {

    @Schema(defaultValue = "")
    private Integer entityId;

    @Schema(defaultValue = "")
    private String title;

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
