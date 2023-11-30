package ciudadano.consciente.transferible;

import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleCrearRol {

    @Schema(defaultValue = "null")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
