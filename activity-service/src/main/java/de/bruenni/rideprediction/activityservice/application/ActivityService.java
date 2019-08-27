package de.bruenni.rideprediction.activityservice.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/")
public class ActivityService {

    private static Logger LOG = LoggerFactory.getLogger(ActivityService.class);

    @GET
    @Path("/overview")
    public Response getActivityOverview() {
        return Response.status(200).build();
    }
}
