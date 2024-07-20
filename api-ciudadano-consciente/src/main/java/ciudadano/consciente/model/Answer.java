package ciudadano.consciente.model;

import jakarta.enterprise.inject.Default;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@Table(schema = "app", name = "answers")
@NamedNativeQueries({
        @NamedNativeQuery(name = "Answer.getAllChildrenLevelsAnswers",
                query = "WITH RECURSIVE LevelHierarchy AS " +
                        "(SELECT level_id, parent FROM app.levels WHERE level_id = :parentLevelId UNION ALL " +
                        "SELECT l.level_id, l.parent FROM app.levels l INNER JOIN " +
                        "LevelHierarchy lh ON l.parent = lh.level_id) " +
                        "SELECT LevelHierarchy.level_id, LevelHierarchy.parent as parent_id, ac.activity_id, ac.content as content_id," +
                        " s.answer_id, s.user_id, s.created, s.status FROM LevelHierarchy join " +
                        "app.activities ac on (ac.level_id = LevelHierarchy.level_id) inner join " +
                        "app.answers s on (s.activity = ac.activity_id);"),
        @NamedNativeQuery(name = "Answer.getAllChildrenLevelsAnswersOfUser",
                query = "WITH RECURSIVE LevelHierarchy AS " +
                        "(SELECT level_id, parent FROM app.levels WHERE level_id = :parentLevelId UNION ALL" +
                        "                        SELECT l.level_id, l.parent FROM app.levels l INNER JOIN" +
                        "                        LevelHierarchy lh ON l.parent = lh.level_id)  " +
                        "                        SELECT LevelHierarchy.level_id, LevelHierarchy.parent as parent_id, ac.activity_id, ac.content as content_id," +
                        "                        s.answer_id, s.user_id, s.created, s.status FROM LevelHierarchy join" +
                        "                        app.activities ac on (ac.level_id = LevelHierarchy.level_id) inner join" +
                        "                        app.answers s on (s.activity = ac.activity_id and s.user_id = :userId);")
})
public class Answer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "answer_id")
    private Integer answerId;

    private LocalDate created;

    // @JoinColumn(nombreClaveForanea en Modelo, nombreClavePrimaria en BD)
    @OneToOne
    @JoinColumn(name = "activity", referencedColumnName = "activity_id")
    private Activity activity;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User userId;

    private Boolean status;

    public Answer() {}

    public Answer(Activity activity, User userId, Boolean status) {
        this.activity = activity;
        this.userId = userId;
        this.status = status;
        this.created = LocalDate.now();
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
