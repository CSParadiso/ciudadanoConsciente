package ciudadano.consciente.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(schema = "app", name = "users_roles_levels", uniqueConstraints = {
        @UniqueConstraint( // Only a Role per User in Level
                name = "users_roles_levels_user_id_level_id_key",
                columnNames = { "user_id", "level_id" })
}
)
@NamedQuery(name = "UserRoleLevel", query = "from UserRoleLevel as u where u.user = :user and u.role = :role and u.level = :level")
@NamedNativeQuery(name = "URL.getGenealogyByRole", query = "SELECT *\n" +
        "FROM app.users_roles_levels AS url\n" +
        "WHERE url.level_id IN (SELECT child FROM app.get_genealogy(:levelId))", resultClass = UserRoleLevel.class)
public class UserRoleLevel {

    //@JoinColumn (nombreEnTablaPropia, nombreEnTablaAjena)

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_id")
    @Id
    private Integer urlId;

    @NotNull
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User user;

    @NotNull
    @JoinColumn(name = "role_id", referencedColumnName = "roles_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Role role;

    @NotNull
    @JoinColumn(name = "level_id", referencedColumnName = "level_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Level level;

    public Integer getUrlId() {
        return urlId;
    }

    public void setUrlId(Integer urlId) {
        this.urlId = urlId;
    }

    public @NotNull User getUser() {
        return user;
    }

    public void setUser(@NotNull User user) {
        this.user = user;
    }

    public @NotNull Role getRole() {
        return role;
    }

    public void setRole(@NotNull Role role) {
        this.role = role;
    }

    public @NotNull Level getLevel() {
        return level;
    }

    public void setLevel(@NotNull Level level) {
        this.level = level;
    }
}
