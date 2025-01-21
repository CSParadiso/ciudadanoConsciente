package ciudadano.consciente.filter;

import io.quarkus.oidc.UserInfo;
import io.quarkus.security.identity.SecurityIdentity;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

public class KCFilter {

    @Inject
    Logger audit;

    @Inject
    SecurityIdentity securityIdentity;

    @ServerRequestFilter(preMatching = true, priority = 4500)
    public void logUser() {

        if(securityIdentity.isAnonymous()) {
            audit.infov("Anonymous User Access.");
            // TODO Here we can do some kind of counter or to make a following to retrieve metrics of the anonymous use.
        } else {
            try {
                UserInfo userInfo = securityIdentity.getAttribute("userinfo");
                // [USER_AUTH_ID]
                audit.infov("[{0}]", userInfo.getSubject());
            } catch (Exception e) {
                audit.infov("Failed to retrieve User from Access Token.");
            }
        }

    }

}
