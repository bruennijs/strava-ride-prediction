package de.bruenni.rideprediction.identity.impl.auth0;

import de.bruenni.rideprediction.identity.api.AccessToken;
import de.bruenni.rideprediction.identity.api.AuthorizationCode;
import de.bruenni.rideprediction.identity.api.OidcAuthenticationApi;
import de.bruenni.rideprediction.identity.api.UserProfile;
import de.bruenni.rideprediction.identity.impl.strava.GenericOicdAuthenticationBase;
import de.bruenni.rideprediction.identity.impl.strava.StravaAuthService;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.net.URI;

/**
 *  Auth0 implementation
 *
 * @author Oliver Brüntje
 */
@ApplicationScoped
public class Auth0AuthenticationImpl extends GenericOicdAuthenticationBase {
    private String clientSecret;

    @Inject
    public Auth0AuthenticationImpl(@ConfigProperty(name = "oauth.clientid") String clientId,
            @ConfigProperty(name = "oauth.clientSecret")
                    String clientSecret,
            @ConfigProperty(name = "oauth.authorize.url") String authorizeUrl,
            @ConfigProperty(name = "oauth.tokenexchange.url") String exchangeTokenUrl,
            @ConfigProperty(name = "oauth.scope") String scope) {
        super(authorizeUrl, clientId, exchangeTokenUrl, scope);
        this.clientSecret = clientSecret;
    }

    protected Auth0AuthenticationImpl() {
        super("", "", "", "");
    }

    @Override public UserProfile getAccessToken(@NotNull AuthorizationCode code) {
        return new UserProfile("", new AccessToken("sometoken"), "username");
    }
}
