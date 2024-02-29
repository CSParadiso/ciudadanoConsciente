package ciudadano.consciente.access;

import ciudadano.consciente.dto.DTOImage;
import ciudadano.consciente.model.Content;
import ciudadano.consciente.model.Image;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class AccessImage implements PanacheRepositoryBase<Image, Integer> {

    @Inject
    Logger audit;

    public List<Image> getImageByContent(Content content) {

        audit.debug("Triying to retrieve images of Content");
        return find("content", content).list();

    }

    public List<Image> saveInBatchMode(List<Image> imageList) {

        audit.debug("Trying to persist List of Images.");
        persist(imageList);
        return  find("content", imageList.get(0).getContent().getContentId()).stream().toList();

    }

    public Optional<Image> save(Image image) {

        audit.debug("Trying to persist Image " + image.getImageName() +
                " - Content " + image.getContent().getContentId() +
                " - ImageId " + image.getImageId());
        persist(image);
        audit.debug(image.getImageId());
        return findByIdOptional(image.getImageId());

    }

    public Optional<Image> get(Integer imageId) {

        audit.debug("Trying to retrieve Image...");
        return findByIdOptional(imageId);

    }

}
