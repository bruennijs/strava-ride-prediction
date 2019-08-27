package de.bruenni.rideprediction.identity.impl.strava;

import de.bruenni.rideprediction.identity.api.AuthorizationCode;
import de.bruenni.rideprediction.identity.api.UserProfile;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

@ApplicationScoped
@Alternative
public class StravaAuthService extends GenericOicdAuthenticationBase {

    private String clientSecret;

    @Inject
    public StravaAuthService(@ConfigProperty(name = "oauth.clientid") String clientId,
            @ConfigProperty(name = "oauth.clientSecret")
                    String clientSecret,
            @ConfigProperty(name = "oauth.authorize.url") String authorizeUrl,
            @ConfigProperty(name = "oauth.tokenexchange.url") String exchangeTokenUrl,
            @ConfigProperty(name = "oauth.scope") String scope) {
        super(authorizeUrl, clientId, exchangeTokenUrl, scope);
        this.clientSecret = clientSecret;
    }

    protected StravaAuthService() {
        super("", "", "", "");
    }

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
