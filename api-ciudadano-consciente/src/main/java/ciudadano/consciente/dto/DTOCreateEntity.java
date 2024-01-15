package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOCreateEntity {

    @Schema(defaultValue = "")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
