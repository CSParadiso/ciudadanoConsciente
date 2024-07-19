package ciudadano.consciente.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class DTOCreateRandomStreak {

    @Schema
    private Integer actualStreak;

    @Schema
    private Integer userId;

    public Integer getActualStreak() {
        return actualStreak;
    }

    public void setActualStreak(Integer actualStreak) {
        this.actualStreak = actualStreak;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
