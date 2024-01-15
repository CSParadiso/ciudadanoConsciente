package ciudadano.consciente.model;

import jakarta.persistence.*;

@jakarta.persistence.Entity
@Table(schema = "app", name = "entities")
public class Entity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entity_id")
    @Id
    private Integer entityId;

    private String title;

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
