package ciudadano.consciente.model;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(schema = "app", name = "votes")
@NamedNativeQueries(
        @NamedNativeQuery(name = "Vote.getMostVotedEntitiesByEntityType",
                query = "select entity_id, count(*) as votes from app.votes where entity_type = :entityType and active = 'true'" +
                        " group by (entity_id, entity_type) order by (count(*)) desc;")
)
public class Vote {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    @Id
    private Integer voteId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Column(name = "entity_id")
    private Integer entity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entity_type", referencedColumnName = "entity_type_id")
    private EntityType entityType;

    // For quick toogle option
    private boolean active;

    private OffsetDateTime date;

    public Vote() {}

    public Vote(User user, Integer entity, EntityType entityType) {

        this.user = user;
        this.entity = entity;
        this.entityType = entityType;
        this.date = OffsetDateTime.now();
        this.active = true;

    }

    public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getEntity() {
        return entity;
    }

    public void setEntity(Integer entity) {
        this.entity = entity;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

}
