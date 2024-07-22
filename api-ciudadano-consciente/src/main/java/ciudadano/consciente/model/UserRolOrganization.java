package ciudadano.consciente.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@Table(schema = "app", name = "users_roles_organizations", uniqueConstraints = {
        @UniqueConstraint( // Only a Role per User in Organization
                name = "users_roles_organizations_user_id_organization_key",
                columnNames = { "user_id", "organization_id" })
})
public class UserRolOrganization {

    //@JoinColumn (nombreEnTablaPropia, nombreEnTablaAjena)

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uro_id")
    @Id
    private Integer uroId;

    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User user;

    @JoinColumn(name = "role_id", referencedColumnName = "roles_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Role role;

    @JoinColumn(name = "organization_id", referencedColumnName = "organization_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Organization organization;

    public Integer getUroId() {
        return uroId;
    }

    public void setUroId(Integer uroId) {
        this.uroId = uroId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

}
