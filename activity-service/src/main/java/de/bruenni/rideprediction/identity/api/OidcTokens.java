package de.bruenni.rideprediction.identity.api;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * COntains tokens (refresh tokens, access tokens, id tokens) returned by oidc
 * token endpoint call (e.g. by a given authorization_code in corresponding flow)
 *
 * @author Oliver Brüntje -
 */
public class OidcTokens {

    private IdToken idToken;

    private AccessToken accessToken;

    public OidcTokens(IdToken idToken, AccessToken accessToken) {
        this.idToken = notNull(idToken, "id token may not be null");
        this.accessToken = notNull(accessToken, "accessToken may not be empty");
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public IdToken getIdToken() {
        return idToken;
    }
}
