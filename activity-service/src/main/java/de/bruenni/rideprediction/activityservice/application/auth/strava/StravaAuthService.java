package de.bruenni.rideprediction.activityservice.application.auth.strava;

import de.bruenni.rideprediction.activityservice.infrastructure.oauth2.AuthorizationCode;
import de.bruenni.rideprediction.activityservice.infrastructure.oauth2.UserProfile;
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

    private final String scope;

    @Inject
    public StravaAuthService(@ConfigProperty(name = "oauth.clientid") String clientId,
            @ConfigProperty(name = "oauth.clientSecret")
                    String clientSecret,
            @ConfigProperty(name = "oauth.authorize.url") String authorizeUrl,
            @ConfigProperty(name = "oauth.tokenexchange.url") String exchangeTokenUrl,
            @ConfigProperty(name = "oauth.scope") String scope) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authorizeUrl = authorizeUrl;
        this.exchangeTokenUrl = exchangeTokenUrl;
        this.scope = scope;
    }

    protected StravaAuthService() {
        scope = "";
        exchangeTokenUrl = "";
        authorizeUrl = "";
        clientSecret = "";
        clientId = "";
    }

    @Inject
    private StravaAuthClient client;

    /**
     * Builds redirect URI to OAuth2 authorization server containing client id and own redirect uri
     * @return
     */
    public URI createAuthorizationUrl() {
        String format = String.format("%s?client_id=%s&redirect_uri=%s&response_type=code&scope=%s", authorizeUrl, clientId, exchangeTokenUrl, scope);
        return URI.create(format);
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
