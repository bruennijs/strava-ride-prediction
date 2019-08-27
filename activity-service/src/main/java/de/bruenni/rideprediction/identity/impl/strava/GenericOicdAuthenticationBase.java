package de.bruenni.rideprediction.identity.impl.strava;

import de.bruenni.rideprediction.identity.api.OidcAuthenticationApi;

import java.net.URI;

/**
 * Vase class for all OIDC implementations.
 *
 * @author Oliver Brüntje - open knowledge GmbH
 */
public abstract class GenericOicdAuthenticationBase implements OidcAuthenticationApi {
    protected String authorizeUrl;

    protected final String clientId;

    protected final String redirectUri;

    protected final String scope;

    protected GenericOicdAuthenticationBase(String authorizeUrl, String clientId, String redirectUri,
            String scope) {
        this.authorizeUrl = authorizeUrl;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.scope = scope;
    }

    /**
     * Builds redirect URI to OAuth2 authorization server containing client id and own redirect uri
     * @return
     */
    @Override public URI createAuthorizationUrl() {
        String format = String.format("%s?client_id=%s&redirect_uri=%s&response_type=code&scope=%s", authorizeUrl, clientId, redirectUri, scope);
        return URI.create(format);
    }
}
