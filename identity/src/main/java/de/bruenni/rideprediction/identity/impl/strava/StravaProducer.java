package de.bruenni.rideprediction.identity.impl.strava;

import java.net.URI;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

/**
 *
 *
 * @author Oliver Brüntje
 */
@ApplicationScoped
public class StravaProducer {

    @Produces
    public StravaAuthClient createClient() {
        return RestClientBuilder.newBuilder()
                .baseUri(URI.create("https://www.strava.com"))
                //.register(LogHeaderClientRequestFilter.class)
                .build(StravaAuthClient.class);
    }
}
