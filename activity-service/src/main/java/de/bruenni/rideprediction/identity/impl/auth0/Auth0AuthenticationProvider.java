package de.bruenni.rideprediction.identity.impl.auth0;

import com.auth0.AuthenticationController;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class Auth0AuthenticationProvider {

    private String clientId;

    private String clientSecret;

    private String domain;

    @Inject
    public Auth0AuthenticationProvider(@ConfigProperty(name = "oauth.clientid") String clientId,
            @ConfigProperty(name = "oauth.clientSecret") String clientSecret,
            @ConfigProperty(name = "oauth.auth0.domain") String domain) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;

        this.domain = domain;
    }

    @Produces
    public AuthenticationController authenticationController() {
        return AuthenticationController.newBuilder(this.domain, this.clientId, this.clientSecret)
                .build();
    }
}
