package ciudadano.consciente.model;

import ciudadano.consciente.utility.UtilityFileSystem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "app", name = "contents")
public class Content implements Taggable, Votable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "content_id")
  private Integer contentId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "activity_type_version", referencedColumnName = "activity_type_version_id")
  private ActivityTypeVersion activityTypeVersion;

  @JsonIgnoreProperties(ignoreUnknown = true)
  @JdbcTypeCode(SqlTypes.JSON) // To automatically use the table as jsonb
  @Column(name = "model", columnDefinition = "jsonb")
  private String model;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "creator", referencedColumnName = "user_id")
  private User creator;

  @Column(name = "public")
  private boolean publicContent;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "organization", referencedColumnName = "organization_id")
  private Organization organization;

  @Transient
  private List<Image> images;

  @Transient
  private List<Tag> tags = new ArrayList<>();

  @Transient
  private List<Vote> votes = new ArrayList<>();

  public Content() {
  }

  public Content(ActivityTypeVersion activityTypeVersion, String model) {

    this.activityTypeVersion = activityTypeVersion;
    this.model = model;

  }

  public Content(ActivityTypeVersion activityTypeVersion, String model, User creator, Organization organization, boolean publicContent) {
 	
    this.activityTypeVersion = activityTypeVersion;
    this.model = model;
    this.creator = creator;
    this.organization = organization;
    this.publicContent = publicContent;

  }

  public Integer getContentId() {
    return contentId;
  }

  public void setContentId(Integer contentId) {
    this.contentId = contentId;
  }

  public ActivityTypeVersion getActivityTypeVersion() {
    return activityTypeVersion;
  }

  public void setActivityTypeVersion(ActivityTypeVersion activityTypeVersion) {
    this.activityTypeVersion = activityTypeVersion;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public List<Image> getImages() {
    // List<Image> list = new AccessImage().getImageByContent(this);
    // for(Image image : list) {
    // image.setImage(new UtilityFileSystem().getContentImages(this.contentId + "."
    // + image.getImageName()));
    // }
    return this.images;
  }

  public void setImages(List<Image> images) {
    for (Image image : images) {
      // filename = ContentId.ImageName --> Example: "7.Red"
      new UtilityFileSystem().saveContentImageToFileSystem(this.contentId.toString(), image.getImageName(),
          image.getImage());
    }
  }

  public void setImages(Image image) {

    // filename = ContentId.ImageName --> Example: "7.Red"
    new UtilityFileSystem().saveContentImageToFileSystem(this.contentId.toString(), image.getImageName(),
        image.getImage());

  }

  @Override
  public Integer getId() {
    return this.contentId;
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

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  public boolean isPublicContent() {
    return publicContent;
  }

  public void setPublicContent(boolean publicContent) {
    this.publicContent = publicContent;
  }

  public Organization getOrganization() {
    return organization;
  }

  public void setOrganization(Organization organization) {
    this.organization = organization;
  }
}
