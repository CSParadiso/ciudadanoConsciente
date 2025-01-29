package ciudadano.consciente.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(schema = "app", name = "organizations")
public class Organization implements Taggable, Votable {

  @Min(1)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "organization_id")
  @Id
  private Integer organizationId;

  @NotNull(message = "Name cannot be null.")
  @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters.")
  private String name;

  @NotNull(message = "Email cannot be null.")
  @Size(min = 1, max = 100, message = "Email must be between 1 and 100 characters.")
  private String email;

  @Size(min = 1, max = 140, message = "Description must be between 1 and 100 characters.")
  private String description;

  @Transient
  List<Tag> tags = new ArrayList<>();

  @Transient
  List<Vote> votes = new ArrayList<>();

  public @Min(1) Integer getOrganizationId() {
    return organizationId;
  }

  public void setOrganizationId(@Min(1) Integer organizationId) {
    this.organizationId = organizationId;
  }

  public @NotNull(message = "Name cannot be null.") @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters.") String getName() {
    return name;
  }

  public void setName(@NotNull(message = "Name cannot be null.") @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters.") String name) {
    this.name = name;
  }

  public @NotNull(message = "Email cannot be null.") @Size(min = 1, max = 100, message = "Email must be between 1 and 100 characters.") String getEmail() {
    return email;
  }

  public void setEmail(@NotNull(message = "Email cannot be null.") @Size(min = 1, max = 100, message = "Email must be between 1 and 100 characters.") String email) {
    this.email = email;
  }

  public @Size(min = 1, max = 140, message = "Description must be between 1 and 100 characters.") String getDescription() {
    return description;
  }

  public void setDescription(@Size(min = 1, max = 140, message = "Description must be between 1 and 100 characters.") String description) {
    this.description = description;
  }

  @Override
  public Integer getId() {
    return this.organizationId;
  }

  @Override
  public List<Tag> getTags() {
    return this.tags;
  }

  @Override
  public void setTag(Tag tag) {
    this.tags.add(tag);
  }

  @Override
  public List<Vote> getVotes() {
    return this.votes;
  }

  @Override
  public void setVote(Vote vote) {
    this.votes.add(vote);

  }

}
