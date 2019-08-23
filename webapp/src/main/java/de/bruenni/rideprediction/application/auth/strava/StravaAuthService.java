package de.bruenni.rideprediction.application.auth.strava;

import de.bruenni.rideprediction.infrastructure.oauth2.AuthorizationCode;
import de.bruenni.rideprediction.infrastructure.oauth2.UserProfile;
import org.apache.commons.lang3.NotImplementedException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

@ApplicationScoped
public class StravaAuthService {
    @Inject
    @ConfigProperty(name = "strava.oauth.clientid")
    private String clientId;

    @Inject
    @ConfigProperty(name = "strava.oauth.clientSecret")
    private String clientSecret;

    public UserProfile getAccessToken(@NotNull AuthorizationCode code) {
        throw new NotImplementedException("not yet");
    }
}
