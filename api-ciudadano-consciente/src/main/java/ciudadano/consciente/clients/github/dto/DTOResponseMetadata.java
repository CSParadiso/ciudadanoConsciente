package ciudadano.consciente.clients.github.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *  Contains all the fields that a Response from a Version Server may have.
 *  It only requieres "name". */
@JsonIgnoreProperties(ignoreUnknown = true) // Allows to declare the known ones and ignore the others
public class DTOResponseMetadata {

    //@JsonProperty("type")
    //private String type;

    //@JsonProperty("size")
    //private Integer size;

    @JsonProperty("name")
    private String name;

    //@JsonProperty("path")
    //private String path;

//    @JsonProperty("sha")
//    private String sha;

    //@JsonProperty("url")
    //private String url;

    //@JsonProperty("git_url")
    //private String gitUrl;

    //@JsonProperty("html_url")
    //private String htmlUrl;

    //@JsonProperty("download_url")
    //private String downloadUrl;

//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public Integer getSize() {
//        return size;
//    }
//
//    public void setSize(Integer size) {
//        this.size = size;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }

//    public String getSha() {
//        return sha;
//    }
//
//    public void setSha(String sha) {
//        this.sha = sha;
//    }

//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public String getGitUrl() {
//        return gitUrl;
//    }
//
//    public void setGitUrl(String gitUrl) {
//        this.gitUrl = gitUrl;
//    }
//
//    public String getHtmlUrl() {
//        return htmlUrl;
//    }
//
//    public void setHtmlUrl(String htmlUrl) {
//        this.htmlUrl = htmlUrl;
//    }
//
//    public String getDownloadUrl() {
//        return downloadUrl;
//    }
//
//    public void setDownloadUrl(String downloadUrl) {
//        this.downloadUrl = downloadUrl;
//    }

}
