package de.bruenni.rideprediction.application.auth;

import de.bruenni.rideprediction.application.auth.strava.StravaAuthService;
import de.bruenni.rideprediction.infrastructure.oauth2.AuthorizationCode;
import de.bruenni.rideprediction.infrastructure.oauth2.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.net.URI;

@ApplicationScoped
@Path("/oauth")
public class Oauth2Resource {

    private static Logger LOG = LoggerFactory.getLogger(Oauth2Resource.class);

    @Inject
    private StravaAuthService service;

    /**
     * OAuth redirect uri for grant type 'code' to POST
     * access token. APplication must now retrieve
     * @return
     */
    @GET
    @Path("/login")
    public Response login() {

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
    public Response handleAuthorizationCode(@QueryParam(value = "code") AuthorizationCode code) {

        LOG.info("Auth_code=" + code);

        UserProfile profile = service.getAccessToken(code);
        LOG.info("access_token=" + profile.getAccessToken());
        return Response
                .temporaryRedirect(URI.create("rideprediction"))
                .cookie(new NewCookie("accesstoken", profile.getAccessToken().getValue()))
                .build();
    }
}