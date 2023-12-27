package ciudadano.consciente.model;

import jakarta.persistence.*;

@Entity
@Table(schema = "app", name = "reference")
public class Reference {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "reference_id")
    private Integer referenceId;

    private String title;

    private String url;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "level_id", referencedColumnName = "level_id")
    private Level levelId;

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

    public Level getLevelId() {
        return levelId;
    }

    public void setLevelId(Level levelId) {
        this.levelId = levelId;
    }
}
