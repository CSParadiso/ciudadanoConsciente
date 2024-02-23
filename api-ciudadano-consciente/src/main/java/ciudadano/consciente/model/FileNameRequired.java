package ciudadano.consciente.model;

import jakarta.persistence.*;

@Entity
@Table(schema = "app", name = "file_name_required",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "file_name_required_file_name_key",
                        columnNames = {"file_name"}
                )
})
public class FileNameRequired {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "file_name_required_id")
    private Integer fileNameRequiredId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "extension")
    private String extension;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "alias")
    private String alias;

    @Column(name = "in_db")
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
