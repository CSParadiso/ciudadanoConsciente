package ciudadano.consciente.transferible;

import ciudadano.consciente.modelo.Nivel;
import ciudadano.consciente.modelo.Rol;
import ciudadano.consciente.modelo.Usuario;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleUsuarioRolNivel {

    @Schema
    private Integer urlId;

    @Schema
    private Usuario user;

    @Schema
    private Rol role;

    @Schema
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
