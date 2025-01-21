package ciudadano.consciente.dto;

import jakarta.persistence.Column;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOFileNameRequired {

    @Schema(defaultValue = "")
    private Integer fileNameRequiredId;

    @Schema(defaultValue = "")
    private String fileName;

    @Schema(defaultValue = "")
    private String extension;

    @Schema(defaultValue = "")
    private String mimeType;

    @Schema(defaultValue = "")
    private String alias;

    @Schema(defaultValue = "")
    private Boolean inDb;

    public Integer getFileNameRequiredId() {
        return fileNameRequiredId;
    }

    public void setFileNameRequiredId(Integer fileNameRequiredId) {
        this.fileNameRequiredId = fileNameRequiredId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Boolean getInDb() {
        return inDb;
    }

    public void setInDb(Boolean inDb) {
        this.inDb = inDb;
    }
}
