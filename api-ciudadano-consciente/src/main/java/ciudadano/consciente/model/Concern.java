package ciudadano.consciente.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "app", name = "concerns")
public class Concern implements Taggable, Votable {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "concern_id")
  private Integer concernId;

  @NotNull
  @Size(min = 1, max = 140)
  private String description;

  private String explanation;

  private OffsetDateTime date;

  @NotNull
  // @JoinColumn(name = nombreClaveForanea, referencedColumnName =
  // nombreClavePrimaria referenciada)
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user;

  @Transient
  private List<Tag> tags = new ArrayList<>();

  @Transient
  private List<Vote> votes = new ArrayList<>();

  public Concern() {

    this.date = OffsetDateTime.now();

  }

  public Integer getConcernId() {
    return concernId;
  }

  public void setConcernId(Integer concernId) {
    this.concernId = concernId;
  }

  public @NotNull @Size(min = 1, max = 140) String getDescription() {
    return description;
  }

  public void setDescription(@NotNull @Size(min = 1, max = 140) String description) {
    this.description = description;
  }

  public String getExplanation() {
    return explanation;
  }

  public void setExplanation(String explanation) {
    this.explanation = explanation;
  }

  public OffsetDateTime getDate() {
    return date;
  }

  public void setDate(OffsetDateTime date) {
    this.date = date;
  }

  public @NotNull User getUser() {
    return user;
  }

  public void setUser(@NotNull User user) {
    this.user = user;
  }

  @Override
  public Integer getId() {
    return this.concernId;
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
