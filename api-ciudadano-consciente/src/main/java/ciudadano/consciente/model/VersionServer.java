package ciudadano.consciente.model;

import jakarta.persistence.*;

@Entity
@Table(schema ="app", name = "version_servers")
public class VersionServer {


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "version_server_id")
    Integer versionServerId;

    // No usar @Id sinó UNIQUEConstra
    String name;

    @Column(name = "metadata_url")
    String metadataUrl;

    @Column(name = "commits_url")
    String commitsUrl;

    @Column(name = "content_url")
    String contentUrl;

    public VersionServer() { }

    public Integer getVersionServerId() {
        return versionServerId;
    }

    public void setVersionServerId(Integer versionServerId) {
        this.versionServerId = versionServerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMetadataUrl() {
        return metadataUrl;
    }

    public void setMetadataUrl(String metadataUrl) {
        this.metadataUrl = metadataUrl;
    }

    public String getCommitsUrl() {
        return commitsUrl;
    }

    public void setCommitsUrl(String commitsUrl) {
        this.commitsUrl = commitsUrl;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

 }
