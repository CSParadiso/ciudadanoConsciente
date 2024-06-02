package ciudadano.consciente.model;

import jakarta.persistence.*;

@Entity
@Table(schema = "app", name = "tags")
public class Tag {

    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column(name = "tag_id")
    @Id
    private Integer tagId;

    private String name;

    public Integer getTagId() {
        return tagId;
    }

    public String getName() {
        return name;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
