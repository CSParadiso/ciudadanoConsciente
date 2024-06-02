package ciudadano.consciente.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(schema = "app", name = "tagged")
public class Tagged {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tagged_id")
  @Id
  private Integer taggedId;

  @ManyToOne(fetch = FetchType.EAGER) // o FetchType.EAGER // LAZY: deferred Loading, EAGER: Loading along EntityType
  @JoinColumn(name = "tag_id", referencedColumnName = "tag_id") // @JoinColumn(name = nombreClaveForanea,
  private Tag tagId; // referencedColumnName = nombreClavePrimaria

  @ManyToOne(fetch = FetchType.EAGER) // o FetchType.EAGER // LAZY: deferred Loading, EAGER: Loading along EntityType
  @JoinColumn(name = "entity_type_id", referencedColumnName = "entity_type_id") // @JoinColumn(name =
                                                                                // nombreClaveForanea,
  private EntityType entityTypeId; // referencedColumnName = nombreClavePrimaria

  @Column(name = "entity_id")
  private Integer entityId;

  public Tagged() {
  }

  public Tagged(Tag tag, EntityType entityType, Integer entity) {
    this.tagId = tag;
    this.entityTypeId = entityType;
    this.entityId = entity;
  }

  public Integer getTaggedId() {
    return taggedId;
  }

  public void setTaggedId(Integer taggedId) {
    this.taggedId = taggedId;
  }

  public Tag getTagId() {
    return tagId;
  }

  public void setTagId(Tag tagId) {
    this.tagId = tagId;
  }

  public EntityType getEntityTypeId() {
    return entityTypeId;
  }

  public void setEntityTypeId(EntityType entityType) {
    this.entityTypeId = entityType;
  }

  public Integer getEntityId() {
    return entityId;
  }

  public void setEntityId(Integer entityId) {
    this.entityId = entityId;
  }

}
