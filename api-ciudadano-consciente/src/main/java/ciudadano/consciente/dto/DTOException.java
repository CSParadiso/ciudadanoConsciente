package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * @author cayetanosimonparadiso@protonmail.com
 */
@Schema(
        name = "DTOException",
        description = "Implementation of standard 'problem details' RFC7807",
        externalDocs = @ExternalDocumentation(
                url = "https://www.rfc-editor.org/rfc/rfc7807.html"
        )
)
public class DTOException {

    @Schema(description = "HTTP status code.")
    private int status;
    @Schema(description = "URL to explanation page (default about:blank).")
    private String type;
    @Schema(description = "Message to user.")
    private String title;
    @Schema(description = "Message to developers.")
    private String detail;
    @Schema(description = "URL that fires exception.")
    private String instance;

    /**
     * Gets status.
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets detail.
     *
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Sets detail.
     *
     * @param detail the detail
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public String getInstance() {
        return instance;
    }

    /**
     * Sets instance.
     *
     * @param instance the instance
     */
    public void setInstance(String instance) {
        this.instance = instance;
    }


}
