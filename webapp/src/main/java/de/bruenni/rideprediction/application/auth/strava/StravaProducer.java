package de.bruenni.rideprediction.application.auth.strava;

import de.bruenni.rideprediction.infrastructure.rest.client.filter.filter.LogHeaderClientRequestFilter;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.pac4j.oauth.client.StravaClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.net.URI;

@ApplicationScoped
public class StravaProducer {

    @Produces
    public StravaAuthClient createClient() {
        return RestClientBuilder.newBuilder()
                .baseUri(URI.create("https://www.strava.com"))
                .register(LogHeaderClientRequestFilter.class)
                .build(StravaAuthClient.class);
    }
}
