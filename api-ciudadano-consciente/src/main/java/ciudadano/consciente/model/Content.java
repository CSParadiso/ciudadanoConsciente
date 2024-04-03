package ciudadano.consciente.model;

import ciudadano.consciente.utility.UtilityFileSystem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(schema = "app", name = "contents")
public class Content {

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

    @Transient
    private List<Image> images;

    public Content() {}

    public Content(ActivityTypeVersion activityTypeVersion, String model) {

        this.activityTypeVersion = activityTypeVersion;
        this.model = model;

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
//        List<Image> list = new AccessImage().getImageByContent(this);
//        for(Image image : list) {
//            image.setImage(new UtilityFileSystem().getContentImages(this.contentId + "." + image.getImageName()));
//        }
        return this.images;
    }

    public void setImages(List<Image> images) {
        for(Image image : images) {
            // filename = ContentId.ImageName --> Example: "7.Red"
            new UtilityFileSystem().saveContentImageToFileSystem(this.contentId.toString(), image.getImageName(), image.getImage());
        }
    }

    public void setImages(Image image) {

        // filename = ContentId.ImageName --> Example: "7.Red"
        new UtilityFileSystem().saveContentImageToFileSystem(this.contentId.toString(), image.getImageName(), image.getImage());

    }

}
