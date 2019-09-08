package de.bruenni.rideprediction.activityservice.infrastructure.strava.client.filter;

import de.bruenni.rideprediction.identity.api.AccessToken;
import de.bruenni.rideprediction.identity.api.AccessTokenNotAvailableException;
import de.bruenni.rideprediction.identity.api.TokenManagementException;
import de.bruenni.rideprediction.identity.api.TokenManagementService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import java.io.IOException;

/**
 * Injects strava access token. It is obtained from identity's token management.
 *
 * @author Oliver Brüntje
 */
@ApplicationScoped
public class AccessTokenInjectionClientRequestFilter implements ClientRequestFilter {

    @Inject
    private TokenManagementService tokenManagementService;

    protected AccessTokenInjectionClientRequestFilter() {
        // CDI
    }

    @Override public void filter(ClientRequestContext requestContext) throws IOException {

        try {
            AccessToken stravaAccessToken = tokenManagementService.getIdentityProviderAccessToken();

            requestContext.getHeaders().add("Authorization", "Bearer " + stravaAccessToken.getValue());
        } catch (Exception e) {
            throw new IOException("Cannot get strava identity provider's access token", e);
        }
    }
}
