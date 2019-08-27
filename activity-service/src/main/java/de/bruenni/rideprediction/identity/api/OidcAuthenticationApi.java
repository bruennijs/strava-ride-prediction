package de.bruenni.rideprediction.identity.api;

import javax.validation.constraints.NotNull;
import java.net.URI;

/**
 * API for integrating authorization servers using open id connect API.
 *
 * @author Oliver Brüntje - open knowledge GmbH
 */
public interface OidcAuthenticationApi {
    /**
     * Builds redirect URI to OAuth2 authorization server containing client id and own redirect uri
     * @return
     */
    URI createAuthorizationUrl();

    /**
     * Gets the suer info access token and additional user profile information.
     * @param code
     * @return User profile and access token
     */
    UserProfile getAccessToken(@NotNull AuthorizationCode code);
}
