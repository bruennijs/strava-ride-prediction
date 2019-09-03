package de.bruenni.rideprediction.identity.impl.strava;

import de.bruenni.rideprediction.identity.infrastructure.rest.client.filter.LogHeaderClientRequestFilter;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
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
