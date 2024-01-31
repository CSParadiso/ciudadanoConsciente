package ciudadano.consciente.model;

import jakarta.enterprise.inject.Default;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@Table(schema = "app", name = "answers")
public class Answer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "answer_id")
    private Integer answerId;

    private LocalDate created;

    @Column(name = "last_modified")
    private LocalDate lastModified;

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
        this.lastModified = null;
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

    public LocalDate getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
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
