package de.bruenni.rideprediction.activityservice.application;

import de.bruenni.rideprediction.activityservice.domain.activity.ActivityService;
import de.bruenni.rideprediction.activityservice.infrastructure.JwtLogger;
import de.bruenni.rideprediction.identity.api.AccessToken;
import de.bruenni.rideprediction.identity.api.TokenManagementService;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author Oliver Brüntje
 */
@ApplicationScoped
@Path("/activity-service/activities")
@RolesAllowed("strava:athlete")
public class ActivityResource {

    private static Logger LOG = LoggerFactory.getLogger(ActivityResource.class);

    @Inject
    private TokenManagementService tokenManagementService;

    @Inject
    private ActivityService activityService;

    @GET
    @Path("/overview")
    public Response getOverview() {

        try {
            return Response.status(200).build();
        } catch (Exception e) {
            LOG.error("activity service get overview failed", e);
            return Response.serverError().build();
        }
    }

    /**
     * Starts synchronization process with strava activities.
     * @return
     */
    @POST
    @Path("/sync")
    public Response startSync() {

        try {
            activityService.synchronize();

            return Response.status(200).build();
        } catch (Exception e) {
            LOG.error("activity service get overview failed", e);
            return Response.serverError().build();
        }
    }
}
