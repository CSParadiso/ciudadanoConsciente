package ciudadano.consciente.model;

import jakarta.persistence.*;

@Entity
@Table(schema = "app", name = "users_roles_levels", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id", "level_id"}))
@NamedQuery(name = "UserRoleLevel", query = "from UserRoleLevel as u where u.user = :user and u.role = :role and u.level = :level")
public class UserRoleLevel {

    //@JoinColumn (nombreEnTablaPropia, nombreEnTablaAjena)

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_id")
    @Id
    private Integer urlId;

    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User user;

    @JoinColumn(name = "role_id", referencedColumnName = "roles_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Role role;

    @JoinColumn(name = "level_id", referencedColumnName = "level_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Level level;

    public Integer getUrlId() {
        return urlId;
    }

    public void setUrlId(Integer urlId) {
        this.urlId = urlId;
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

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
