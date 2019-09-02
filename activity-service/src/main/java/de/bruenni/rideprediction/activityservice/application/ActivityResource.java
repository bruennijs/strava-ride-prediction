package de.bruenni.rideprediction.activityservice.application;

import de.bruenni.rideprediction.identity.api.AccessManagementException;
import de.bruenni.rideprediction.identity.api.AccessToken;
import de.bruenni.rideprediction.identity.api.AccessTokenNotAvailableException;
import de.bruenni.rideprediction.identity.impl.auth0.AccessManagementService;
import de.bruenni.rideprediction.identity.infrastructure.JwtLogger;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author Oliver Brüntje
 */
@ApplicationScoped
@Path("/activity-service")
@RolesAllowed("strava:athlete")
public class ActivityResource {

    private static Logger LOG = LoggerFactory.getLogger(ActivityResource.class);

    @Inject
    @Claim(standard = Claims.sub)
    private Instance<String> jwtSubject;

    @Inject
    private AccessManagementService accessManagementService;

    @GET
    @Path("/overview")
    public Response getOverview() {

        LOG.info("jwt.sub=" + jwtSubject.get());

        try {
            AccessToken accessToken = this.accessManagementService.GetIdentityProviderToken();

            new JwtLogger().log(accessToken);

            return Response.status(200).build();
        } catch (Exception e) {
            LOG.error("activity service get overview failed", e);
            return Response.serverError().build();
        }
    }
}
