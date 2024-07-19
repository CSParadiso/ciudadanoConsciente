package ciudadano.consciente.model;

import jakarta.persistence.*;

@Entity
@Table(schema = "app", name = "random_streak", uniqueConstraints = {
        @UniqueConstraint( // Only a Streak for user
                name = "unique_user_id", columnNames = { "user_id" })
})
public class RandomStreak {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "streak_id")
  @Id
  private Integer streakId;

  @Column(name = "max_streak")
  private Integer maxStreak;

  @Column(name = "actual_streak")
  private Integer actualStreak;

  @Column(name = "streak_count")
  private Integer streakCount;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user;

  public RandomStreak() {  }

  public RandomStreak(User user, int actualStreak) {

      this.user = user;
      this.actualStreak = actualStreak;
      this.maxStreak = actualStreak;
      this.streakCount = actualStreak;

  }

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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int incrementCount(int actualStreak) {
    return this.streakCount += actualStreak;
  }

}
