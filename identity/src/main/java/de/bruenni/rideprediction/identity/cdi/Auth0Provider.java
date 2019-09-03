package de.bruenni.rideprediction.identity.cdi;

import com.auth0.AuthenticationController;
import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.Auth0Exception;
import de.bruenni.rideprediction.identity.api.AccessToken;
import de.bruenni.rideprediction.identity.impl.auth0.AuthAPICdi;
import de.bruenni.rideprediction.identity.impl.auth0.ManagementAPICdi;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 * Provides API clients for auth0
 *
 * @author Oliver Brüntje
 */
@ApplicationScoped
public class Auth0Provider {

    private String clientId;

    private String clientSecret;

    private String domain;

    private String mgmtApiIdentifier;

    @Inject
    public Auth0Provider(@ConfigProperty(name = "oauth.clientid") String clientId,
            @ConfigProperty(name = "oauth.clientSecret") String clientSecret,
            @ConfigProperty(name = "oauth.auth0.domain") String domain,
            @ConfigProperty(name = "oauth.auth0.management-api.identifier") String mgmtApiIdentifier) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.domain = domain;
        this.mgmtApiIdentifier = mgmtApiIdentifier;
    }

    @Produces
    public AuthenticationController authenticationController() {
        return AuthenticationController.newBuilder(this.domain, this.clientId, this.clientSecret)
                .build();
    }

    @ApplicationScoped
    @Produces
    public AuthAPICdi authenticationApi() {
        return new AuthAPICdi(this.domain, this.clientId, this.clientSecret);
    }

    @RequestScoped
    @Produces
    @ManagementApi
    public AccessToken managementApiToken(AuthAPICdi authAPI) throws Auth0Exception {
        String accessToken = authAPI.requestToken(this.mgmtApiIdentifier).execute().getAccessToken();
        return new AccessToken(accessToken);
    }

    @RequestScoped
    @Produces
    public ManagementAPICdi mgmtApi(@ManagementApi AccessToken apiToken) {
        return new ManagementAPICdi(this.domain, apiToken.getValue());
    }
}
