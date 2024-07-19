package ciudadano.consciente.dto;

public class DTORandomStreak {

    private Integer streakId;

    private Integer maxStreak;

    private Integer actualStreak;

    private Integer streakCount;

    private DTOUser user;

    public Integer getStreakId() {
        return streakId;
    }

    public void setStreakId(Integer streakId) {
        this.streakId = streakId;
    }

    public Integer getMaxStreak() {
        return maxStreak;
    }

    public void setMaxStreak(Integer maxStreak) {
        this.maxStreak = maxStreak;
    }

    public Integer getActualStreak() {
        return actualStreak;
    }

    public void setActualStreak(Integer actualStreak) {
        this.actualStreak = actualStreak;
    }

    public Integer getStreakCount() {
        return streakCount;
    }

    public void setStreakCount(Integer streakCount) {
        this.streakCount = streakCount;
    }

    public DTOUser getUser() {
        return user;
    }

    public void setUser(DTOUser user) {
        this.user = user;
    }
}
