package de.bruenni.rideprediction.identity.impl.strava;

import de.bruenni.rideprediction.identity.api.AuthorizationCode;
import de.bruenni.rideprediction.identity.api.IdToken;
import de.bruenni.rideprediction.identity.api.InvalidRequestException;
import de.bruenni.rideprediction.identity.api.OidcTokens;
import de.bruenni.rideprediction.identity.impl.GenericOicdAuthenticationBase;
import org.apache.commons.lang3.NotImplementedException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

/**
 * Strava native authentication api implementation.
 *
 * @author Oliver Brüntje - open knowledge GmbH
 */

@ApplicationScoped
@Alternative
public class StravaAuthenticationImpl extends GenericOicdAuthenticationBase {

    private String clientSecret;

    @Inject
    protected StravaAuthenticationImpl(@ConfigProperty(name = "oauth.clientid") String clientId,
            @ConfigProperty(name = "oauth.clientSecret")
                    String clientSecret,
            @ConfigProperty(name = "oauth.strava.authorize-url") String authorizeUrl,
            @ConfigProperty(name = "oauth.tokenexchange.url") String exchangeTokenUrl,
            @ConfigProperty(name = "oauth.scope") String scope) {
        super(authorizeUrl, clientId, exchangeTokenUrl, scope);
        this.clientSecret = clientSecret;
    }

    @Inject
    private StravaAuthClient client;

    /**
     * Gets the OAuth2 access token and additional user profile information.
     * @param code
     * @return User profile and access token
     */
    public OidcTokens getTokens(@NotNull AuthorizationCode code) {
        StravaTokenExchangeResponse tokenExchangeResponse = client
                .getAuthToken(this.clientId, this.clientSecret, code.getValue(), "authorization_code");

        return new OidcTokens(new IdToken(""), tokenExchangeResponse.getAccessToken());
    }

    @Override public OidcTokens getTokens(@NotNull HttpServletRequest request) throws InvalidRequestException {
        throw new NotImplementedException("Not needed here");
    }
}
