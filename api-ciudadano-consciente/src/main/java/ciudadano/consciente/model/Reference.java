package ciudadano.consciente.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

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
    private Level level;

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
}
