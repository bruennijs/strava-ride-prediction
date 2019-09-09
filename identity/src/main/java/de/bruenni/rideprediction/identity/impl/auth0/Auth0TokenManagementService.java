package de.bruenni.rideprediction.identity.impl.auth0;

import com.auth0.exception.Auth0Exception;
import com.auth0.json.mgmt.users.Identity;
import com.auth0.json.mgmt.users.User;
import com.auth0.net.Request;
import de.bruenni.rideprediction.identity.api.AccessToken;
import de.bruenni.rideprediction.identity.api.AccessTokenNotAvailableException;
import de.bruenni.rideprediction.identity.api.TokenManagementException;
import de.bruenni.rideprediction.identity.api.TokenManagementService;
import de.bruenni.rideprediction.identity.infrastructure.Aggregator;
import org.apache.commons.lang3.NotImplementedException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Manages access tokens for users by caching them or retrieving them newly
 * if expired.
 * @author Oliver Brüntje
 */
@RequestScoped
public class Auth0TokenManagementService implements TokenManagementService {

    private static Logger LOG = LoggerFactory.getLogger(Auth0TokenManagementService.class);

    @Inject
    private ManagementAPICdi managementApi;

    @Inject
    @Claim(standard = Claims.sub)
    private String subject;

    @Inject
    @ConfigProperty(name = "oauth.auth0.connection")
    private String connection;

    protected Auth0TokenManagementService() {
    }

    /**
     * Returns an identity hub access token for access to auth0.
     * Infers user of current security context (MP-JWT).
     * @return
     */
    @Override public AccessToken getAccessToken() {
        throw new NotImplementedException("Not implemented yet");
    }

    /**
     * Retrieves access token of connected identity provider, e.g. strava
     * for the current user. This implementation needs an injected JsonWebToken
     * by the SecurityContext.
     * @return
     */
    @Override public AccessToken getIdentityProviderAccessToken() throws TokenManagementException, AccessTokenNotAvailableException {
        Request<User> userRequest = managementApi.users().get(subject, null);
        try {
            User user = userRequest.execute();

            LOG.info("Auth0 federated identity user [props=" + new Aggregator().serialize(user.getValues()) + "]");

            Optional<Identity> identityOfConfiguredConnection = user.getIdentities().stream()
                    .filter(identity -> identity.getConnection().equals(this.connection))
                    .findFirst();

            return identityOfConfiguredConnection
                    .map(identity -> new AccessToken(identity.getAccessToken()))
                    .orElseThrow(() -> new AccessTokenNotAvailableException("Could not get access token for connection [" + connection + "]"));
        } catch (Auth0Exception e) {
            throw new TokenManagementException("GetIdentityProviderToken failed", e);
        }
    }
}
