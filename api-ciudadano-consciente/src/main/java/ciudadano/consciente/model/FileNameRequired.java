package ciudadano.consciente.model;

import jakarta.persistence.*;

@Entity
@Table(schema = "app", name = "file_name_required")
public class FileNameRequired {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "file_name_required_id")
    private Integer fileNameRequiredId;

    @Column(name = "file_name")
    private String fileName;

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

}
