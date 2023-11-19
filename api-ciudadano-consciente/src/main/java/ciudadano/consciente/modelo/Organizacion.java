package ciudadano.consciente.modelo;

import jakarta.persistence.*;

@Entity
@Table(schema = "app", name = "organizations")
public class Organizacion {

    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column(name = "organization_id")
    @Id
    private Integer organizationId;

    private String name;

    private String email;

    private  String description;

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
