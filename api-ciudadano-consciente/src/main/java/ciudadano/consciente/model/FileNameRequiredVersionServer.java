package ciudadano.consciente.model;

import jakarta.persistence.*;

@Entity
@Table(schema = "app", name = "file_names_required_version_server")
public class FileNameRequiredVersionServer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "fnrvs_id")
    private Integer fileNameRequiredVersionServerId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_name_required", referencedColumnName = "file_name_required_id")
    private FileNameRequired fileNameRequired;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "version_server", referencedColumnName = "version_server_id")
    private VersionServer versionServer;

    private String purpose;

    public Integer getFileNameRequiredVersionServerId() {
        return fileNameRequiredVersionServerId;
    }

    public void setFileNameRequiredVersionServerId(Integer fileNameRequiredVersionServerId) {
        this.fileNameRequiredVersionServerId = fileNameRequiredVersionServerId;
    }

    public FileNameRequired getFileNameRequired() {
        return fileNameRequired;
    }

    public void setFileNameRequired(FileNameRequired fileNameRequired) {
        this.fileNameRequired = fileNameRequired;
    }

    public VersionServer getVersionServer() {
        return versionServer;
    }

    public void setVersionServer(VersionServer versionServer) {
        this.versionServer = versionServer;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
