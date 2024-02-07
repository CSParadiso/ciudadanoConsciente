package ciudadano.consciente.model;

import jakarta.persistence.*;

@Entity
@Table(schema = "app", name = "version_servers")
public class VersionServer {

    @Id // TODO Añadir anotación de UNIQUE CONSTRAINT
    private String name;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "version_server_id")
    private Integer versionServerId;

    @Column(name = "metadata_url")
    private String metadataUrl;

    @Column(name = "content_url")
    private String contentUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersionServerId() {
        return versionServerId;
    }

    public void setVersionServerId(Integer versionServerId) {
        this.versionServerId = versionServerId;
    }

    public String getMetadataUrl() {
        return metadataUrl;
    }

    public void setMetadataUrl(String metadataUrl) {
        this.metadataUrl = metadataUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

}
