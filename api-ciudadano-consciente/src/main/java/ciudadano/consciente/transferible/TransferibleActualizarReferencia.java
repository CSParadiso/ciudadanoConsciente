package ciudadano.consciente.transferible;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleActualizarReferencia {

    @Schema(defaultValue = "null")
    private Integer referenceId;

    @Schema(defaultValue = "null")
    private String title;

    @Schema(defaultValue = "null")
    private String url;

    @Schema(defaultValue = "null")
    private String description;

    @Schema(defaultValue = "null")
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
