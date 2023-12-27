package ciudadano.consciente.model;

import jakarta.persistence.*;

@Entity
@Table(schema = "app", name = "roles")
public class Role {

    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column(name = "roles_id")
    @Id
    private Integer roleId;

    private String name;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
