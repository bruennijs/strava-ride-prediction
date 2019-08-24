package de.bruenni.rideprediction.application.auth.strava;

import de.bruenni.rideprediction.infrastructure.oauth2.AuthorizationCode;
import de.bruenni.rideprediction.infrastructure.oauth2.UserProfile;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.net.URI;

@ApplicationScoped
public class StravaAuthService {

    private final String clientId;

    private final String clientSecret;

    private final String authorizeUrl;

    private final String exchangeTokenUrl;

    public StravaAuthService(@ConfigProperty(name = "strava.oauth.clientid") String clientId,
            @ConfigProperty(name = "strava.oauth.clientSecret")
            String clientSecret,
            @ConfigProperty(name = "oauth.authorize.url") String authorizeUrl,
            @ConfigProperty(name = "oauth.tokenexchange.redirect-url")
             String exchangeTokenUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authorizeUrl = authorizeUrl;
        this.exchangeTokenUrl = exchangeTokenUrl;
    }

    @Inject
    private StravaAuthClient client;

    /**
     * Builds redirect URI to OAuth2 authorization server containing client id and own redirect uri
     * @return
     */
    public URI createAuthorizationUrl() {
        return URI.create(String.format("{0}?client_id={1}&redirect_uri={2}", authorizeUrl, clientId, exchangeTokenUrl));
    }

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
