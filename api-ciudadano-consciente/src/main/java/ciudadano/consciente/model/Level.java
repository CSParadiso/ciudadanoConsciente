package ciudadano.consciente.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

@Entity
@Table(schema = "app", name = "levels")
public class Level {

    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "level_id")
    private Integer levelId;

    private String name;

    private String description;

    // LA REQUEST RECUPERA TODOS LOS DATOS DE LA CLAVE FORÁNEA. CORROBORAR SI LO VAMOS A USAR O SOBRECARGA LA REQUEST
    @ManyToOne(fetch = FetchType.EAGER) // o FetchType.EAGER // LAZY: deferred Loading, EAGER: Loading along EntityType
    @JoinColumn(name = "organization", referencedColumnName = "organization_id") // @JoinColumn(name = nombreClaveForanea, referencedColumnName = nombreClavePrimaria referenciada)
    private Organization organization;

    @ManyToOne(fetch = FetchType.EAGER) // or FetchType.EAGER
    @JoinColumn(name = "parent", referencedColumnName = "level_id")
    private Level parent;

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Level getParent() {
        return parent;
    }

    public void setParent(Level parent) {
        this.parent = parent;
    }
}
