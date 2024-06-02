package ciudadano.consciente.model;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public interface Votable {

  Integer getId();

  List<Vote> getVotes();

  void setVote(Vote vote);

}
