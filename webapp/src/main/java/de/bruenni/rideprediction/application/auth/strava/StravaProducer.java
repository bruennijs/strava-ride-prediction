package de.bruenni.rideprediction.application.auth.strava;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class StravaProducer {

    @Produces
    public StravaAuthService createAuthService() {
        return new StravaAuthService();
    }
}
