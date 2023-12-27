package ciudadano.consciente.access;

import ciudadano.consciente.model.Organization;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessOrganization implements PanacheRepositoryBase<Organization, Integer> {

    @Inject
    Logger auditor;

    public List<Organization> obtenerTodos() {

        auditor.debug("Intentando recuperar todas las organizaciones.");
        return findAll().stream().toList();

    }

    public Optional<Organization> obtener(Integer identificador) {

        auditor.debug("Intentando recuparar organización " + identificador);
        return findByIdOptional(identificador);

    }

    public Optional<Organization> persistir(Organization organization) {

        auditor.debug("Intentando persistir organización " + organization.getOrganizationId());
        persist(organization);
        return findByIdOptional(organization.getOrganizationId());

    }

    public boolean eliminar(Integer identificador) {

        auditor.debug("Intentando eliminar organización " + identificador);
        return deleteById(identificador);

    }

    public boolean existeNombre(String name) {

        return count("name", name) > 0;

    }

    public boolean existeEmail(String email) {

        return count("email", email) > 0;

    }
}
