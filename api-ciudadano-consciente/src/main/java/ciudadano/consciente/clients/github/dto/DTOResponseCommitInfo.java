package ciudadano.consciente.clients.github.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) // Allows to declare the known ones and ignore the others
public class DTOResponseCommitInfo {

    @JsonProperty("name")
    private String name;

    @JsonProperty("commit")
    private DTOCommitInfo commitInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DTOCommitInfo getCommitInfo() {
        return commitInfo;
    }

    public void setCommitInfo(DTOCommitInfo commitInfo) {
        this.commitInfo = commitInfo;
    }

    @JsonIgnoreProperties(ignoreUnknown = true) // Allows to declare the known ones and ignore the others
    public class DTOCommitInfo {

        @JsonProperty("sha")
        private String shaCommit;

        @JsonProperty("message")
        private String message;

        public String getShaCommit() {
            return shaCommit;
        }

        public void setShaCommit(String shaCommit) {
            this.shaCommit = shaCommit;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
