package ciudadano.consciente.dto;

public class DTOTaggedEntity {

    private Integer taggedId;

    private Integer tagId;

    private String tagname;

    private Integer entityId;

    public Integer getTaggedId() {
        return taggedId;
    }

    public void setTaggedId(Integer taggedId) {
        this.taggedId = taggedId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }
}
