package ciudadano.consciente.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(schema = "app", name = "activity_types")
public class ActivityType implements Taggable {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "activity_type_id")
  @Id
  private Integer activityTypeId;

  private String name;

  private String description;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "creator", referencedColumnName = "user_id")
  private User creator;

  @Transient
  private java.util.List<Tag> tags = new ArrayList<>();

  public Integer getActivityTypeId() {
    return activityTypeId;
  }

  public void setActivityTypeId(Integer activityTypeId) {
    this.activityTypeId = activityTypeId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  @Override
  public Integer getTaggableId() {
    return this.activityTypeId;
  }

  @Override
  public List<Tag> getTags() {
    return this.tags;
  }

  @Override
  public void setTags(List<Tag> tags) {
    this.tags.addAll(tags);
  }

}
