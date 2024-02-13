package ciudadano.consciente.clients.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) // Allows to declare the known ones and ignore the others
public class DTOResponseContent {

    @JsonProperty
    private DTOResponseStringContent model;

    @JsonProperty
    private DTOResponseStringContent template;

    @JsonProperty
    private DTOResponseStringContent readme;

    private byte[] thumbnail;

    public DTOResponseStringContent getModel() {
        return model;
    }

    public void setModel(DTOResponseStringContent model) {
        this.model = model;
    }

    public DTOResponseStringContent getTemplate() {
        return template;
    }

    public void setTemplate(DTOResponseStringContent template) {
        this.template = template;
    }

    public DTOResponseStringContent getReadme() {
        return readme;
    }

    public void setReadme(DTOResponseStringContent readme) {
        this.readme = readme;
    }

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    @JsonIgnoreProperties(ignoreUnknown = true) // Allows to declare the known ones and ignore the others
    public static class DTOResponseStringContent {

        @JsonProperty
        private String content;

        public DTOResponseStringContent(String content) {

            this.content = content;

        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}
