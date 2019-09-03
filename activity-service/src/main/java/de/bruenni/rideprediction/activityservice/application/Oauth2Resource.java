package de.bruenni.rideprediction.activityservice.application;

import de.bruenni.rideprediction.activityservice.infrastructure.JwtLogger;
import de.bruenni.rideprediction.identity.api.InvalidRequestException;
import de.bruenni.rideprediction.identity.api.OidcAuthenticationApi;
import de.bruenni.rideprediction.identity.api.AuthorizationCode;
import de.bruenni.rideprediction.identity.api.OidcTokens;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@ApplicationScoped
@Path("/auth")
public class Oauth2Resource {

    private static Logger LOG = LoggerFactory.getLogger(Oauth2Resource.class);

    @Inject
    private Instance<OidcAuthenticationApi> service;

    private JwtLogger jwtLogger = new JwtLogger();

    /**
     * OAuth redirect uri for grant type 'code' to POST
     * access token. APplication must now retrieve
     * @return
     */
    @GET
    @Path("/login")
    @Valid
    public Response login(@Context HttpServletRequest request, @QueryParam(value = "scope") @Null String connection) {

        if (connection != null) {
            LOG.info("desired connection=" + connection);
        }

        URI redirectUrl = service.get().createAuthorizationUrl(request);

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
    public Response handleAuthorizationCode(@Context HttpServletRequest request,
            @Context UriInfo uriInfo,
            @QueryParam(value = "code") @NotNull AuthorizationCode code)
            throws InvalidRequestException {

        LOG.info("Auth_code=" + code);

        OidcTokens tokens = service.get().getTokens(request);

        logTokens(tokens);

        return Response
                .temporaryRedirect(uriInfo.getBaseUri())
                .cookie(new NewCookie("idtoken",
                        tokens.getIdToken().getValue(),
                        "/",
                        "",
                        "no comment",
                        NewCookie.DEFAULT_MAX_AGE,
                        false))
                .build();
    }

    private void logTokens(OidcTokens tokens) {
        jwtLogger.log(tokens.getIdToken());
        jwtLogger.log(tokens.getAccessToken());
    }
}
