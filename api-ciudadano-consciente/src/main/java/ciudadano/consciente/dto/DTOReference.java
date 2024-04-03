package ciudadano.consciente.dto;

import ciudadano.consciente.model.Organization;
import jakarta.enterprise.context.RequestScoped;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@RequestScoped
public class DTOReference {

    @Schema
    private Integer referenceId;

    @Schema
    private String title;

    @Schema
    private String url;

    @Schema
    private String description;

    @Schema
    private Integer level;

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
