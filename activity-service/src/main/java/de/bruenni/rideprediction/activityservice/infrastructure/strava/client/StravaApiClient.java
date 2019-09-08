package de.bruenni.rideprediction.activityservice.infrastructure.strava.client;

import javax.json.JsonArray;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
//@RegisterRestClient
//@RegisterProvider(SomeClass.class)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface StravaApiClient {
    /**
     * Gets activities of the current user set by Authorization access token.
     * @return Activities of this athlete
     */
    @GET
    @Path("/athlete/activities")
    JsonArray getActivities();
}
