package de.bruenni.rideprediction.activityservice.infrastructure.strava.client;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import de.bruenni.rideprediction.activityservice.infrastructure.rest.client.filter.LogHeaderClientRequestFilter;
import de.bruenni.rideprediction.activityservice.infrastructure.strava.client.filter.AccessTokenInjectionClientRequestFilter;

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
    @ApplicationScoped
    public StravaApiClient createClient() {
        return RestClientBuilder.newBuilder()
                .baseUri(URI.create("https://www.strava.com/api/v3/"))
                .register(new LogHeaderClientRequestFilter(true, false))
                .register(accessTokenInjectionClientRequestFilter)
                .build(StravaApiClient.class);
    }
}
