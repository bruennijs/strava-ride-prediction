package de.bruenni.rideprediction.application.auth;

import de.bruenni.rideprediction.application.auth.strava.StravaAuthService;
import de.bruenni.rideprediction.infrastructure.oauth2.AuthorizationCode;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.net.URI;

@ApplicationScoped
@Path("/auth")
public class Oauth2Resource {

    @Inject
    private StravaAuthService service;

    /**
     * OAuth redirect uri for grant type 'code' to POST
     * access token. APplication must now retrieve
     * @return
     */
    @POST
    @Path("/code")
    public Response handleAuthorizationCode(@QueryParam(value = "code") AuthorizationCode code) {
        //new Response.ResponseBuilder().cookie(new NewCookie())
        return Response.temporaryRedirect(URI.create("rideprediction")).build();
    }
}
