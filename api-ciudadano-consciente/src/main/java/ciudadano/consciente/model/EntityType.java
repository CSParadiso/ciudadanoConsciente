package ciudadano.consciente.model;

import jakarta.persistence.*;


@jakarta.persistence.Entity
@Table(schema = "app", name = "entity_types")
public class EntityType {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "entity_type_id")
    private Integer entityTypeId;

    private String title;

    private Boolean votable;

    public Integer getEntityTypeId() {
        return entityTypeId;
    }

    public void setEntityTypeId(Integer entityTypeId) {
        this.entityTypeId = entityTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVotable() {
        return votable;
    }

    public void setVotable(Boolean votable) {
        this.votable = votable;
    }
}
