package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOCreateEntityType {

    @Schema(defaultValue = "")
    private String title;

    @Schema(defaultValue = "false")
    private Boolean votable;

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
