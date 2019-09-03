package de.bruenni.rideprediction.identity.api;

/**
 * Thrown if OIDC authentication api throws exceptions on
 * requesting access tokens by a given authorization code.
 * @author Oliver Brüntje
 */
public class InvalidRequestException extends Exception {
    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
