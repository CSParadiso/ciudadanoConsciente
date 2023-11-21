package ciudadano.consciente.modelo;

import jakarta.persistence.*;

@Entity
@Table(schema = "app", name = "levels")
public class Nivel {

    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "level_id")
    private Integer levelId;

    private String name;

    private String description;

    // LA REQUEST RECUPERA TODOS LOS DATOS DE LA CLAVE FORÁNEA. CORROBORAR SI LO VAMOS A USAR O SOBRECARGA LA REQUEST
    @ManyToOne(fetch = FetchType.LAZY) // o FetchType.EAGER // LAZY: deferred Loading, EAGER: Loading along Entity
    @JoinColumn(name = "organization", referencedColumnName = "organization_id") // @JoinColumn(nombreClaveForanea en Organizacion, nombreClavePrimaria en Organizacion)
    private Organizacion organization;

    @ManyToOne(fetch = FetchType.EAGER) // or FetchType.EAGER
    @JoinColumn(name = "parent", referencedColumnName = "level_id")
    private Nivel parent;

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

    public Organizacion getOrganization() {
        return organization;
    }

    public void setOrganization(Organizacion organization) {
        this.organization = organization;
    }

    public Nivel getParent() {
        return parent;
    }

    public void setParent(Nivel parent) {
        this.parent = parent;
    }
}
