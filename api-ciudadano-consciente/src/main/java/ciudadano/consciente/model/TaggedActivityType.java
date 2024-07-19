package ciudadano.consciente.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "app", name = "tagged_activity_types")
public class TaggedActivityType {

  @Column(name = "tagged_id")
  @Id
  private Integer taggedId;

  @Column(name = "tag_id")
  private Integer tagId;

  @Column(name = "tag_name")
  private String tagname;

  @Column(name = "activity_type_id")
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
