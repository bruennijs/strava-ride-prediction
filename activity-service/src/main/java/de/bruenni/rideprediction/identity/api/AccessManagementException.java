package de.bruenni.rideprediction.identity.api;

import com.auth0.exception.Auth0Exception;

/**
 * General exception in access management with proprietary API. See inner
 * exception for details.
 *
 * @author Oliver Brüntje
 */
public class AccessManagementException extends Exception {
    public AccessManagementException(String getIdentityProviderToken_failed, Auth0Exception e) {}
}
