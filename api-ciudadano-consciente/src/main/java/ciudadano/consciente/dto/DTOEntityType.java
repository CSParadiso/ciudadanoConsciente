package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOEntityType {

    @Schema(defaultValue = "")
    private Integer entityTypeId;

    @Schema(defaultValue = "")
    private String title;

    @Schema(defaultValue = "")
    private Boolean votable;

    public Integer getEntityTypeId() {
        return entityTypeId;
    }

    public void setEntityTypeId(Integer entityTypeId) {
        this.entityTypeId = entityTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVotable() {
        return votable;
    }

    public void setVotable(Boolean votable) {
        this.votable = votable;
    }
}
