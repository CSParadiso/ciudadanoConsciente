package ciudadano.consciente.dto;

public class DTOTagged {

    private Integer taggedId;

    private Integer tagId;

    private Integer entityTypeId;

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

    public Integer getEntityTypeId() {
        return entityTypeId;
    }

    public void setEntityTypeId(Integer entityTypeId) {
        this.entityTypeId = entityTypeId;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }
}
