package ciudadano.consciente.filter;

import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.server.ServerRequestFilter;

import java.util.stream.Collectors;

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
        //[HTTP_METHOD][ABSOLUTE_URI]
        audit.infov("[{0}][{1}]", routingContext.request().method(),
                routingContext.request().absoluteURI());

    }

}
