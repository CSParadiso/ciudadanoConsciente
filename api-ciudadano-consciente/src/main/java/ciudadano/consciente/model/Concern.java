package ciudadano.consciente.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@Table(schema = "app", name = "concerns")
public class Concern {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "concern_id")
    private Integer concernId;

    private String description;

    private String explanation;

    private LocalDate date;

    // @JoinColumn(name = nombreClaveForanea, referencedColumnName = nombreClavePrimaria referenciada)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    public Concern() {}

    public Concern(String description, User user) {

        this.description = description;
        this.user = user;
        this.date = LocalDate.now();

    }

    public Integer getConcernId() {
        return concernId;
    }

    public void setConcernId(Integer concernId) {
        this.concernId = concernId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
