package ciudadano.consciente.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(schema = "app", name = "organizations")
public class Organization implements Taggable {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "organization_id")
  @Id
  private Integer organizationId;

  private String name;

  private String email;

  private String description;

  @Transient
  List<Tag> tags = new ArrayList<>();

  public Integer getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(Integer organizationId) {
    this.organizationId = organizationId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public Integer getTaggableId() {
    return this.organizationId;
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
