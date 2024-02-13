package ciudadano.consciente.utility;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.jboss.logging.Logger;

@ApplicationScoped
public class APILifeCycle {

    private static final Logger LOGGER = Logger.getLogger(APILifeCycle.class);

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("\n" +
                "   ___ _           _           _                       ___                     _            _       \n" +
                "  / __(_)_   _  __| | __ _  __| | __ _ _ __   ___     / __\\___  _ __  ___  ___(_) ___ _ __ | |_ ___ \n" +
                " / /  | | | | |/ _` |/ _` |/ _` |/ _` | '_ \\ / _ \\   / /  / _ \\| '_ \\/ __|/ __| |/ _ \\ '_ \\| __/ _ \\\n" +
                "/ /___| | |_| | (_| | (_| | (_| | (_| | | | | (_) | / /__| (_) | | | \\__ \\ (__| |  __/ | | | ||  __/\n" +
                "\\____/|_|\\__,_|\\__,_|\\__,_|\\__,_|\\__,_|_| |_|\\___/  \\____/\\___/|_| |_|___/\\___|_|\\___|_| |_|\\__\\___|\n" +
                "                                                                                                    \n");
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The api CIUDADANO CONSCIENTE is stopping...");
    }

}
