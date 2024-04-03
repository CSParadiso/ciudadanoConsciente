package ciudadano.consciente.dto;

import ciudadano.consciente.model.Organization;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOCreateVote {

    @Schema(defaultValue = "")
    private Integer user;

    @Schema(defaultValue = "")
    private Integer entity;

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getEntity() {
        return entity;
    }

    public void setEntity(Integer entity) {
        this.entity = entity;
    }

}
