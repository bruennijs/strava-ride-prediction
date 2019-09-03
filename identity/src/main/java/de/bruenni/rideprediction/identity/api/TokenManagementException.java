package de.bruenni.rideprediction.identity.api;

import com.auth0.exception.Auth0Exception;

/**
 * General exception in access management with proprietary API. See inner
 * exception for details.
 *
 * @author Oliver Brüntje
 */
public class TokenManagementException extends Exception {
    public TokenManagementException(String message, Throwable e) {
        super(message, e);
    }
}
