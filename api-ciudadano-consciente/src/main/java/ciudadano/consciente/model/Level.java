package ciudadano.consciente.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(schema = "app", name = "levels")
@NamedNativeQueries({
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
        "FROM LevelHierarchy;\n", resultClass = Level.class),

    @NamedNativeQuery(name = "Level.getLatestsUserPaths", query = "select distinct L.level_id, L.name, L.description, L.organization, b.created, b.last_modified "
        +
        "from app.activities as A inner join app.answers as B on (B.activity = A.activity_id and B.user_id = :user) " +
        "inner join app.levels as L on (a.level_id = L.level_id and L.parent is null) " +
        "order by b.last_modified DESC, b.created DESC;")
})
public class Level implements Taggable {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "level_id")
  private Integer levelId;

  private String name;

  private String description;

  // LA REQUEST RECUPERA TODOS LOS DATOS DE LA CLAVE FORÁNEA. CORROBORAR SI LO
  // VAMOS A USAR O SOBRECARGA LA REQUEST
  @ManyToOne(fetch = FetchType.EAGER) // o FetchType.EAGER // LAZY: deferred Loading, EAGER: Loading along EntityType
  @JoinColumn(name = "organization", referencedColumnName = "organization_id") // @JoinColumn(name = nombreClaveForanea,
                                                                               // referencedColumnName =
                                                                               // nombreClavePrimaria referenciada)
  private Organization organization;

  @ManyToOne(fetch = FetchType.EAGER) // or FetchType.EAGER
  @JoinColumn(name = "parent", referencedColumnName = "level_id")
  private Level parent;

  // private boolean isOrdered; // The childrens of the level are displayed in
  // order in the webapp

  // private boolean orderRequired; // Previous levels must be completed before
  // access the next one.

  // private Integer order; // The position of the level in the hierarchy if its
  // parent requiere the childrens to be ordered

  @Transient
  private List<Tag> tags = new ArrayList<>();

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

  @Override
  public Integer getTaggableId() {
    return this.levelId;
  }

  @Override
  public List<Tag> getTags() {
    return this.tags;
  }

  @Override
  public void setTags(List<Tag> tags) {
    this.tags.addAll(tags);
  }

}
