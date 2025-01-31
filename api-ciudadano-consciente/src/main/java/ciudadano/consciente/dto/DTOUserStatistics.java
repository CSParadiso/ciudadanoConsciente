package ciudadano.consciente.dto;

import ciudadano.consciente.model.User;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOUserStatistics {


    @Schema
    private Integer userId;

    @Schema
    private String username;

    @Schema
    private String email;

    @Schema
    private Integer answersOK;

    @Schema
    private Integer answers;

    @Schema
    private Integer levelsCompleted;

    @Schema
    private Integer votes;

    @Schema
    private Integer concerns;

    @Schema
    private Integer contents;

    public DTOUserStatistics(User user, Integer answersOK, Integer answers, Integer levelsCompleted,
                             Integer votes, Integer concerns, Integer contents) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.answersOK = answersOK;
        this.answers = answers;
        this.levelsCompleted = levelsCompleted;
        this.votes = votes;
        this.concerns = concerns;
        this.contents = contents;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAnswersOK() {
        return answersOK;
    }

    public void setAnswersOK(Integer answersOK) {
        this.answersOK = answersOK;
    }

    public Integer getAnswers() {
        return answers;
    }

    public void setAnswers(Integer answers) {
        this.answers = answers;
    }

    public Integer getLevelsCompleted() {
        return levelsCompleted;
    }

    public void setLevelsCompleted(Integer levelsCompleted) {
        this.levelsCompleted = levelsCompleted;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Integer getConcerns() {
        return concerns;
    }

    public void setConcerns(Integer concerns) {
        this.concerns = concerns;
    }

    public Integer getContents() {
        return contents;
    }

    public void setContents(Integer contents) {
        this.contents = contents;
    }
}
