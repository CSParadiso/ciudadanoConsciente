package ciudadano.consciente.model;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public interface Taggable {

  Integer getTaggableId();

  List<Tag> getTags();

  void setTags(List<Tag> tags);

}
