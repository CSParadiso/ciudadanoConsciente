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
    public void logUser(RoutingContext rc) {

        /*
           HERE JUST FOR DISCOVERY OF DEV TOOLS PROVIDED FOR JAVA
           THIS SHOULD COULD ADD userInfo.getSubject() in each HEADER to be able to deal with persistence of User Activity
           This API allows the user to access certain features without being logged or registered.
           To access the full scope of features the user has to be registered and logged.

         */
        if(securityIdentity.isAnonymous()) {
            audit.debug("Anonymous User Access.");
            // TODO Here we can do some kind of counter or to make a following to retrieve metrics of the anonymous use.
        } else {
            // This is an option of dealing with the User
//            try {
//                audit.debug("securityIdentity.getPrincipal().toString(): " + securityIdentity.getPrincipal().toString());
//                DefaultJWTCallerPrincipal accessToken = (DefaultJWTCallerPrincipal) securityIdentity.getPrincipal();
//                audit.debug("accessToken.getSubject(): " + accessToken.getSubject());
//                audit.debug("accessToken.getDescription(): " + accessToken.getDescription());
//                audit.debug("accessToken.getClaim('email'): " + accessToken.getClaim("email"));
//            } catch (ClassCastException e) {
//                System.out.println("Problemas al castear.");
//            }
            // This is the chose option to deal with the User
            try {
                UserInfo userInfo = securityIdentity.getAttribute("userinfo");
                audit.debug("SecurityIdentity.getAttribute('userinfo'): " + userInfo.getUserInfoString());
                audit.debug("userInfo.getSubject(): " + userInfo.getSubject());
                audit.debug("userInfo.getPreferredUserName(): " + userInfo.getPreferredUserName());
                audit.debug("userInfo.getEmail(): " + userInfo.getEmail());
            } catch (Exception e) {
                audit.debug("Failed to retrieve User from Access Token.");
            }
        }

    }

}
