package ciudadano.consciente.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Entity
// emailSender + ">"));
// emailSender + ">"));
@Table(schema = "app", name = "verify_token", uniqueConstraints = @UniqueConstraint(name =
        "verify_token_organization_key", columnNames = "organization"))
public class VerifyToken  {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "verify_token_id")
  private Integer verifyTokenId;

  @NotNull
  private String token;

  @NotNull
  @ManyToOne(fetch = FetchType.EAGER) // o FetchType.EAGER // LAZY: deferred Loading, EAGER: Loading along EntityType
  @JoinColumn(name = "organization", referencedColumnName = "organization_id") // @JoinColumn(name = nombreClaveForanea,
                                                                               // referencedColumnName =
                                                                               // nombreClavePrimaria referenciada)
  private Organization organization;

  public VerifyToken() {
  }

  public VerifyToken(Organization organization, String token) {
    this.organization = organization;
    this.token = token;
  }

  public Integer getVerifyTokenId() {
    return verifyTokenId;
  }

  public void setVerifyTokenId(Integer verifyTokenId) {
    this.verifyTokenId = verifyTokenId;
  }

  public @NotNull String getToken() {
    return token;
  }

  public void setToken(@NotNull String token) {
    this.token = token;
  }

  public @NotNull Organization getOrganization() {
    return organization;
  }

  public void setOrganization(@NotNull Organization organization) {
    this.organization = organization;
  }

  public static VerifyToken generateToken(Organization organization) {

    // Generar random token con 3354519684571452145664 combinaciones
    SecureRandom sr = new SecureRandom();
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder token = new StringBuilder(15);

    for (int i = 0; i < 15; i++) {
      token.append(characters.charAt(sr.nextInt(characters.length())));
    }
    //

    return new VerifyToken(organization, token.toString());

  }

}
