package ciudadano.consciente.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(schema = "app", name = "images", uniqueConstraints = {
        @UniqueConstraint(name = "images_image_name_content_key", columnNames = {"image_name", "content"})
})
public class Image {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Integer imageId;

    @NotNull
    @Length(min = 1, max = 50)
    @Column(name = "image_name")
    private String imageName;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "content", referencedColumnName = "content_id")
    private Content content;

    // Managed from the Entity that uses the image
    @Transient
    private byte[] image;

    public Image() {
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public @NotNull @Length(min = 1, max = 50) String getImageName() {
        return imageName;
    }

    public void setImageName(@NotNull @Length(min = 1, max = 50) String imageName) {
        this.imageName = imageName;
    }

    public @NotNull Content getContent() {
        return content;
    }

    public void setContent(@NotNull Content content) {
        this.content = content;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
