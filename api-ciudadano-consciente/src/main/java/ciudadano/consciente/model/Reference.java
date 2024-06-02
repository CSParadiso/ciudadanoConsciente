package ciudadano.consciente.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(schema = "app", name = "reference")
public class Reference implements Taggable {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "reference_id")
  private Integer referenceId;

  private String title;

  private String url;

  private String description;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "level_id", referencedColumnName = "level_id")
  private Level level;

  @Transient
  private List<Tag> tags = new ArrayList<>();

  public Integer getReferenceId() {
    return referenceId;
  }

  public void setReferenceId(Integer referenceId) {
    this.referenceId = referenceId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Level getLevel() {
    return level;
  }

  public void setLevel(Level level) {
    this.level = level;
  }

  @Override
  public Integer getTaggableId() {
    return this.referenceId;
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
