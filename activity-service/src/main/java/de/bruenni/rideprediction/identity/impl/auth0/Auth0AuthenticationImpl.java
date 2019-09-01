package de.bruenni.rideprediction.identity.impl.auth0;

import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.Tokens;
import de.bruenni.rideprediction.identity.api.*;
import org.apache.commons.lang3.NotImplementedException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.net.URI;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

/**
 *  Auth0 implementation
 *
 * @author Oliver Brüntje
 */
@ApplicationScoped
public class Auth0AuthenticationImpl implements OidcAuthenticationApi {

    private static Logger LOG = LoggerFactory.getLogger(Auth0AuthenticationImpl.class);

    private AuthenticationController authenticationController;

    private String exchangeTokenUrl;

    private String scope;

    private String connection;

    @Inject
    public Auth0AuthenticationImpl(AuthenticationController authenticationController,
            @ConfigProperty(name = "oauth.tokenexchange.url") String exchangeTokenUrl,
            @ConfigProperty(name = "oauth.scope") String scope,
            @ConfigProperty(name = "oauth.auth0.connection") String connection) {
        this.authenticationController = notNull(authenticationController);
        this.exchangeTokenUrl = notEmpty(exchangeTokenUrl, "exchangeTokenUrl may not be null");
        this.scope = notEmpty(scope, "scope may not be empty");
        this.connection = notEmpty(connection, "connection may not be empty");
    }

    @Override public URI createAuthorizationUrl(HttpServletRequest request) {
        return URI.create(this.authenticationController.buildAuthorizeUrl(request, this.exchangeTokenUrl)
                .withConnection(this.connection)
                .withScope(this.scope)
                .build());
    }

    @Override public OidcTokens getTokens(@NotNull AuthorizationCode code) {
        throw new NotImplementedException("Use servlet method");
    }

    @Override public OidcTokens getTokens(@NotNull HttpServletRequest request) throws InvalidRequestException {
        try {
            Tokens tokens = this.authenticationController.handle(request);
            return new OidcTokens(new IdToken(tokens.getIdToken()), new AccessToken(tokens.getAccessToken()));
        } catch (IdentityVerificationException e) {
            LOG.error("Error in requesting access tokens", e);
            throw new InvalidRequestException("Error in requesting access tokens", e);
        }
    }
}
