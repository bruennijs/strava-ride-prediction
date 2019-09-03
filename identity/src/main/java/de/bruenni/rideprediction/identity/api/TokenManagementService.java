package de.bruenni.rideprediction.identity.api;

/**
 * Manages tokens for given user a nd underlying configured identity hiub (e.g. auth0 or keycloak).
 * Possibly access tokens and refresh tokens are cached and stored by this application.
 *
 * @author Oliver Brüntje
 */
public interface TokenManagementService {
    /**
     * Returns an identity hub access token for access to auth0.
     * Infers user of current security context (MP-JWT).
     * @return Access token of identity hub (e.g. auth0, keycloak)
     */
    AccessToken getAccessToken() throws AccessTokenNotAvailableException;

    /**
     * Retrieves access token of connected identity provider, e.g. strava
     * for the current user. This implementation needs an injected JsonWebToken
     * by the SecurityContext.
     * @return Access token of identity provider (e.g. strava, guthub, facebook)
     */
    AccessToken getIdentityProviderAccessToken() throws TokenManagementException, AccessTokenNotAvailableException;
}
