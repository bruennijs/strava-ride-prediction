package de.bruenni.rideprediction.activityservice.application;

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

    @GET
    @Path("/overview")
    public Response getOverview() {

        LOG.info("jwt.sub=" + jwtSubject.get());

        return Response.status(200).build();
    }
}
