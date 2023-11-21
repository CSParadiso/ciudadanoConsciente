package ciudadano.consciente.transferible;

import ciudadano.consciente.modelo.Nivel;
import ciudadano.consciente.modelo.Organizacion;
import jakarta.persistence.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class TransferibleRequestCrearNivel {

    @Schema(required = true)
    private String name;

    @Schema(required = true)
    private String description;

    @Schema(required = true)
    private Organizacion organization; // Corregir esto para no cargar la entidad entera, solo el ID

    @Schema(required = true)
    private Nivel parent; // Corregir esto para no cargar la entidad entera, solo el ID

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Organizacion getOrganization() {
        return organization;
    }

    public void setOrganization(Organizacion organization) {
        this.organization = organization;
    }

    public Nivel getParent() {
        return parent;
    }

    public void setParent(Nivel parent) {
        this.parent = parent;
    }
}
