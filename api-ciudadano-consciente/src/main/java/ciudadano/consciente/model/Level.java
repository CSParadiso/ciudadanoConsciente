package ciudadano.consciente.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@Table(schema = "app", name = "levels")
@NamedNativeQueries(
        @NamedNativeQuery(name = "Level.getAllChildrens", query = "WITH RECURSIVE LevelHierarchy AS (\n" +
                "    SELECT level_id, name, description, organization, parent\n" +
                "    FROM app.levels\n" +
                "    WHERE level_id = :parentLevelId" +
                "\n" +
                "    UNION ALL\n" +
                "\n" +
                "    SELECT l.level_id, l.name, l.description, l.organization, l.parent\n" +
                "    FROM app.levels l\n" +
                "    INNER JOIN LevelHierarchy lh ON l.parent = lh.level_id\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM LevelHierarchy;\n", resultClass = Level.class)
)
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

    //private boolean isOrdered; // The childrens of the level are displayed in order in the webapp

    //private boolean orderRequired; // Previous levels must be completed before access the next one.

    //private Integer order; // The position of the level in the hierarchy if its parent requiere the childrens to be ordered


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
