package ciudadano.consciente;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

public class KCFilter {

    @Inject
    Logger audit;

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    JsonWebToken jsonWebToken;

    @ServerRequestFilter(preMatching = true)
    public void logUser() {

        audit.debug("KC-Cliente : " + jsonWebToken.getClaim("azp"));
        audit.debug("KC-Id: " + jsonWebToken.getClaim("sub"));
        audit.debug("KC-Username: " + securityIdentity.getPrincipal().getName());

    }

}
