package de.bruenni.rideprediction.identity.application;

import de.bruenni.rideprediction.identity.api.OidcAuthenticationApi;
import de.bruenni.rideprediction.identity.impl.strava.StravaAuthService;
import de.bruenni.rideprediction.identity.api.AuthorizationCode;
import de.bruenni.rideprediction.identity.api.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.net.URI;

@ApplicationScoped
@Path("/auth")
public class Oauth2Resource {

    private static Logger LOG = LoggerFactory.getLogger(Oauth2Resource.class);

    @Inject
    private OidcAuthenticationApi service;

    /**
     * OAuth redirect uri for grant type 'code' to POST
     * access token. APplication must now retrieve
     * @return
     */
    @GET
    @Path("/login")
    @Valid
    public Response login(@QueryParam(value = "scope") @Null String scope) {

        if (scope != null) {
            LOG.info("desired scope=" + scope);
        }

        URI redirectUrl = service.createAuthorizationUrl();

        return Response
                .temporaryRedirect(redirectUrl)
                .build();
    }

    /**
     * OAuth redirect uri for grant type 'code' to POST
     * access token. Application must now retrieve
     * @return
     */
    @GET
    @Path("/tokenexchange")
    @Valid
    public Response handleAuthorizationCode(@QueryParam(value = "code") @NotNull AuthorizationCode code) {

        LOG.info("Auth_code=" + code);

        UserProfile profile = service.getAccessToken(code);
        LOG.info("access_token=" + profile.getAccessToken());
        return Response
                .temporaryRedirect(URI.create("rideprediction"))
                .cookie(new NewCookie("accesstoken", profile.getAccessToken().getValue()))
                .build();
    }
}
