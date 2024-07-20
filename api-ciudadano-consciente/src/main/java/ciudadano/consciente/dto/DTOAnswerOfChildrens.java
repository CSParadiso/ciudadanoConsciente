package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.sql.Date;
import java.time.LocalDate;

public class DTOAnswerOfChildrens {

    @Schema
    private Integer answer;

    @Schema
    private Integer user;

    @Schema
    private Date created;

    @Schema
    private Boolean status;

    @Schema
    private Integer level;

    @Schema
    private Integer parent;

    @Schema
    private Integer activity;

    @Schema
    private Integer content;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }

    public Integer getContent() {
        return content;
    }

    public void setContent(Integer content) {
        this.content = content;
    }

    public Integer getAnswer() {
        return answer;
    }

    public void setAnswer(Integer answer) {
        this.answer = answer;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
