package de.bruenni.rideprediction.identity.api;

/**
 * Thrown of an access token could not be retrieved from an remote API.
 *
 * @author Oliver Brüntje
 */
public class AccessTokenNotAvailableException extends Exception {
    public AccessTokenNotAvailableException(String message) {
        super(message);
    }
}
