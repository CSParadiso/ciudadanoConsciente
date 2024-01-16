package ciudadano.consciente.model;

import jakarta.persistence.*;


@jakarta.persistence.Entity
@Table(schema = "app", name = "entity_types")
public class EntityType {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entity_type_id")
    @Id
    private Integer entityTypeId;

    private String title;

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


}
