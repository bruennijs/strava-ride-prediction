package de.bruenni.rideprediction.identity.impl.auth0;

import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.mgmt.users.Identity;
import com.auth0.json.mgmt.users.User;
import com.auth0.net.Request;
import de.bruenni.rideprediction.identity.api.AccessManagementException;
import de.bruenni.rideprediction.identity.api.AccessToken;
import de.bruenni.rideprediction.identity.api.AccessTokenNotAvailableException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Manages access tokens for users by caching them or retrieving them newly
 * if expired.
 * @author Oliver Brüntje
 */
@RequestScoped
public class AccessManagementService {

    @Inject
    private ManagementAPICdi managementApi;

    @Inject
    @Claim(standard = Claims.sub)
    private String subject;

    @Inject
    @ConfigProperty(name = "oauth.auth0.connection")
    private String connection;

    protected AccessManagementService() {
    }

    /**
     * Retrieves access token of connected identity provider, e.g. strava
     * for the current user. This implementation needs an injected JsonWebToken
     * by the SecurityContext.
     * @return
     */
    public AccessToken GetIdentityProviderToken() throws AccessManagementException, AccessTokenNotAvailableException {
        Request<User> userRequest = managementApi.users().get(subject, null);
        try {
            User user = userRequest.execute();

            Optional<Identity> identityOfConfiguredConnection = user.getIdentities().stream()
                    .filter(identity -> identity.getConnection().equals(this.connection))
                    .findFirst();

            return identityOfConfiguredConnection
                    .map(identity -> new AccessToken(identity.getAccessToken()))
                    .orElseThrow(() -> new AccessTokenNotAvailableException("Could not get access token for connection [" + connection + "]"));
        } catch (Auth0Exception e) {
            throw new AccessManagementException("GetIdentityProviderToken failed", e);
        }
    }
}
