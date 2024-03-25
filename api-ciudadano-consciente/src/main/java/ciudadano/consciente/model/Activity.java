package ciudadano.consciente.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

@Entity
@Table(schema = "app", name = "activities")
@NamedNativeQueries(//{
        @NamedNativeQuery(name = "Activity.getTemplateFromActivityTypeVersion",
                query = "select c.template from app.activities as A inner join app.contents as B " +
                        "on(a.content = b.content_id) inner join app.activity_type_version as C " +
                        "on(b.activity_type_version = c.activity_type_version_id) AND a.activity_id = :activityId",
                resultClass = String.class)
//} TODO Se podría optimizar la consulta e incluso hacerla más granular
)
public class Activity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    @Id
    private Integer activityId;

    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "level_id")
    private Level level;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "content", referencedColumnName = "content_id")
    private Content content;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
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

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
