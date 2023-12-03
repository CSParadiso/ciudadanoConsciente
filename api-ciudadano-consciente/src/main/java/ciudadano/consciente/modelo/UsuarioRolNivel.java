package ciudadano.consciente.modelo;

import jakarta.persistence.*;

@Entity
@Table(schema = "app", name = "users_roles_levels", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id", "level_id"}))
public class UsuarioRolNivel {

    //@JoinColumn (nombreEnTablaPropia, nombreEnTablaAjena)

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "url_id")
    @Id
    private Integer urlId;

    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Usuario user;

    @JoinColumn(name = "role_id", referencedColumnName = "roles_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Rol role;

    @JoinColumn(name = "level_id", referencedColumnName = "level_id")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Nivel level;

    public Integer getUrlId() {
        return urlId;
    }

    public void setUrlId(Integer urlId) {
        this.urlId = urlId;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Rol getRole() {
        return role;
    }

    public void setRole(Rol role) {
        this.role = role;
    }

    public Nivel getLevel() {
        return level;
    }

    public void setLevel(Nivel level) {
        this.level = level;
    }
}
