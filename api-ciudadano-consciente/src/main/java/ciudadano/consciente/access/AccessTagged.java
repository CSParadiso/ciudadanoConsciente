package ciudadano.consciente.access;

import ciudadano.consciente.model.Tagged;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessTagged implements PanacheRepositoryBase<Tagged, Integer> {

  @Inject
  Logger audit;

  public List<Tagged> getAll() {
    audit.debug("Trying to retrieve all Tagged.");
    return findAll(Sort.by("taggedId")).stream().toList();
  }

  public Optional<Tagged> get(Integer id) {
    audit.debug("Trying to retrieve Tagged " + id + ".");
    return findByIdOptional(id);
  }

  public Optional<Tagged> save(Tagged tagged) {

    audit.debug("Trying to persist Tagged " + tagged.getTaggedId() + ".");
    persist(tagged);
    return findByIdOptional(tagged.getTaggedId());

  }

  public boolean remove(Integer taggedId) {

    audit.debug("Trying to delete Tagged.");
    return deleteById(taggedId);

  }

}
