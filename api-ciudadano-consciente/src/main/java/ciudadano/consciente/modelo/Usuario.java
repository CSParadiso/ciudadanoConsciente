package ciudadano.consciente.modelo;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema="app", name = "users")
//@UserDefinition
public class Usuario {

    @Column(name = "user_id")
    @Id
    public Integer userId;

    //@Username
    public String username;

    @Column(name = "pass_word")
    //@Password
    public String password;

    public String email;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
