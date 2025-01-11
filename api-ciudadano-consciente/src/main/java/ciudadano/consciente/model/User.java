package ciudadano.consciente.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

@Entity
@Table(schema="app", name = "users", uniqueConstraints = {
        @UniqueConstraint( // Keycloak ID must be unique
                name = "auth_server_id_key", columnNames = { "auth_server_id" }),
        @UniqueConstraint( // Email must be unique
                name = "users_email_key", columnNames = { "email" })
})
//@UserDefinition
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY usa el mecanismo de sequencia de la DB
    @Column(name = "user_id")
    @Id
    private Integer userId;

    @Column(name = "auth_server_id")
    private String authServerId;

    private String username;

    private String email;

    public User() {}

    public User(String authServerId, String username, String email) {
        this.authServerId = authServerId;
        this.username = username;
        this.email = email;
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

    public String getAuthServerId() {
        return authServerId;
    }

    public void setAuthServerId(String authServerId) {
        this.authServerId = authServerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
