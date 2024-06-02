package ciudadano.consciente.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "app", name = "concerns")
public class Concern implements Taggable, Votable {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "concern_id")
  private Integer concernId;

  private String description;

  private String explanation;

  private LocalDate date;

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

    this.date = LocalDate.now();

  }

  public Integer getConcernId() {
    return concernId;
  }

  public void setConcernId(Integer concernId) {
    this.concernId = concernId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getExplanation() {
    return explanation;
  }

  public void setExplanation(String explanation) {
    this.explanation = explanation;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
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
