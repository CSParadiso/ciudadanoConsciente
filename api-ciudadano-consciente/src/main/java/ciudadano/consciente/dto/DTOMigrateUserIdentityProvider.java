package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOMigrateUserIdentityProvider {

    @Schema(defaultValue = "")
    private String actualAuthServerId;
    @Schema(defaultValue = "")
    private String newAuthServerId;

    public String getActualAuthServerId() {
        return actualAuthServerId;
    }

    public void setActualAuthServerId(String actualAuthServerId) {
        this.actualAuthServerId = actualAuthServerId;
    }

    public String getNewAuthServerId() {
        return newAuthServerId;
    }

    public void setNewAuthServerId(String newAuthServerId) {
        this.newAuthServerId = newAuthServerId;
    }
}
