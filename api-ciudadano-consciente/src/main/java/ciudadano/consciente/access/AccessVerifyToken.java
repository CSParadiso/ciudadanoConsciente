package ciudadano.consciente.access;

import ciudadano.consciente.model.Level;
import ciudadano.consciente.model.Organization;
import ciudadano.consciente.model.User;
import ciudadano.consciente.model.VerifyToken;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessVerifyToken implements PanacheRepositoryBase<VerifyToken, Integer> {

  @Inject
  Logger audit;

  @Inject
  EntityManager entityManager;

  public List<VerifyToken> getAll() {

    audit.debug("Trying to retrieve all tokens.");
    return findAll(Sort.by("verifyTokenId")).stream().toList();

  }

  public Optional<VerifyToken> get(Integer id) {

    audit.debug("Trying to retrieve token " + id + ".");
    return findByIdOptional(id);

  }

  public boolean remove(Integer id) {

    audit.debug("Trying to delete Token  " + id + ".");
    // TODO is not deleting the token after the validation
    return deleteById(id);

  }

  public Optional<VerifyToken> save(VerifyToken verifyToken) {

    audit.debug("Trying to persist Token.");
    persist(verifyToken);
    return findByIdOptional(verifyToken.getVerifyTokenId());

  }

  public Optional<VerifyToken> getByOrganization(Organization organization) {

    return find("organization", organization).stream().findAny();

  }

}
