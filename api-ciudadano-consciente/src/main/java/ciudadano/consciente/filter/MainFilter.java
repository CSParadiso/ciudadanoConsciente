package ciudadano.consciente.filter;

import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

public class MainFilter {

    @Inject
    Logger audit;

    /**
     * Priority is ASCENDING
     * prematching determines if the body of the request is readed before apply the filter or not
     * @param routingContext
     */
    @ServerRequestFilter(preMatching = true, priority = 4000)
    public void mainFilter(RoutingContext routingContext) {

        // This item allow us to retrieve information related to the request
        // TODO: THINK POSSIBILITIES WITH THIS FEATURE
        audit.debug("RoutingContext.normalizedPath(): " + routingContext.normalizedPath());

    }

}
