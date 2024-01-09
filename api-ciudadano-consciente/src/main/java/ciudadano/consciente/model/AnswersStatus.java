package ciudadano.consciente.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(schema ="app", name = "answers_status")
public class AnswersStatus {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "answers_status_id")
    private Integer answersStatusId;

    private String title;

    private String description;

    public Integer getAnswersStatusId() {
        return answersStatusId;
    }

    public void setAnswersStatusId(Integer answersStatusId) {
        this.answersStatusId = answersStatusId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
