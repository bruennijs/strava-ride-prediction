package de.bruenni.rideprediction.identity.api;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.net.URI;

/**
 * API for integrating authorization servers using open id connect API.
 *
 * @author Oliver Brüntje
 */
public interface OidcAuthenticationApi {
    /**
     * Builds redirect URI to OAuth2 authorization server containing client id and own redirect uri
     * @return
     */
    URI createAuthorizationUrl(HttpServletRequest request);

    /**
     * Gets the suer info access token and additional user profile information.
     * @param code
     * @return User profile and access token
     */
    OidcTokens getTokens(@NotNull AuthorizationCode code);

    /**
     * Handles Authorization code for auth code flow and returns tokens like
     * access token, id token, refresh token (if addressed in scope).
     * For servlet based implementations.
     * @param request
     * @return
     */
    OidcTokens getTokens(@NotNull HttpServletRequest request) throws InvalidRequestException;
}
