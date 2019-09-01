package de.bruenni.rideprediction.activityservice.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
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

    @GET
    @Path("/overview")
    public Response getOverview() {
        return Response.status(200).build();
    }
}
