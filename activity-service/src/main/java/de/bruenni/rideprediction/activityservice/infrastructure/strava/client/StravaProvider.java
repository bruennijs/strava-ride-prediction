package de.bruenni.rideprediction.activityservice.infrastructure.strava.client;

import de.bruenni.rideprediction.activityservice.infrastructure.strava.client.filter.AccessTokenInjectionClientRequestFilter;
import de.bruenni.rideprediction.identity.impl.strava.StravaAuthClient;
import de.bruenni.rideprediction.identity.infrastructure.rest.client.filter.LogHeaderClientRequestFilter;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.net.URI;

/**
 * Provides REST clients for strava
 *
 * @author Oliver Brüntje
 */
@ApplicationScoped
public class StravaProvider {

    @Inject
    private AccessTokenInjectionClientRequestFilter accessTokenInjectionClientRequestFilter;

    @Produces
    public StravaApiClient createClient() {
        return RestClientBuilder.newBuilder()
                .baseUri(URI.create("https://www.strava.com/api/v3/"))
                .register(LogHeaderClientRequestFilter.class)
                .register(accessTokenInjectionClientRequestFilter)
                .build(StravaApiClient.class);
    }
}
