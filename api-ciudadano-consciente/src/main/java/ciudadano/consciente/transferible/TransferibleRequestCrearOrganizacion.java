package ciudadano.consciente.transferible;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleRequestCrearOrganizacion {

    @Schema(required = true)
    private String name;

    @Schema(required = true)
    private String email;

    @Schema(required = true)
    private String description;

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
