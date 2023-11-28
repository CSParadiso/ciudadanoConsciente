package ciudadano.consciente.transferible;

import ciudadano.consciente.modelo.Nivel;
import jakarta.enterprise.context.RequestScoped;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.net.URL;

@RequestScoped
public class TransferibleReferencia {

    @Schema
    private Integer referenceId;

    @Schema
    private String title;

    @Schema
    private String url;

    @Schema
    private String description;

    @Schema
    private Nivel levelId;

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

    public Nivel getLevelId() {
        return levelId;
    }

    public void setLevelId(Nivel levelId) {
        this.levelId = levelId;
    }
}
