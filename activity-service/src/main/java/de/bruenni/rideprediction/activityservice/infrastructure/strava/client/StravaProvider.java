package de.bruenni.rideprediction.activityservice.infrastructure.strava.client;

import de.bruenni.rideprediction.identity.impl.strava.StravaAuthClient;
import de.bruenni.rideprediction.identity.infrastructure.rest.client.filter.LogHeaderClientRequestFilter;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.net.URI;

/**
 * Provides REST clients for strava
 *
 * @author Oliver Brüntje
 */
@ApplicationScoped
public class StravaProvider {

    @Produces
    public StravaAuthClient createClient() {
        return RestClientBuilder.newBuilder()
                .baseUri(URI.create("https://www.strava.com"))
                .register(LogHeaderClientRequestFilter.class)
                .build(StravaAuthClient.class);
    }
}
