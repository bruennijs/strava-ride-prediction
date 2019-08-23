package de.bruenni.rideprediction.application.auth.strava;

import de.bruenni.rideprediction.infrastructure.oauth2.AccessToken;
import de.bruenni.rideprediction.infrastructure.oauth2.AuthorizationCode;
import de.bruenni.rideprediction.infrastructure.oauth2.UserProfile;
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

    @Inject
    private StravaAuthClient client;

    /**
     * Gets the OAuth2 access token and additional user profile information.
     * @param code
     * @return User profile and access token
     */
    public UserProfile getAccessToken(@NotNull AuthorizationCode code) {
        StravaTokenExchangeResponse tokenExchangeResponse = client
                .getAuthToken(this.clientId, this.clientSecret, code.getValue(), "authorization_code");

        return new UserProfile("id", tokenExchangeResponse.getAccessToken(), "username");
    }
}
