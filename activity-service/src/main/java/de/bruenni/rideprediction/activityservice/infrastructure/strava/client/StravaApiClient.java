package de.bruenni.rideprediction.activityservice.infrastructure.strava.client;

import de.bruenni.rideprediction.identity.impl.strava.StravaTokenExchangeResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.Response;

@Path("/oauth/token")
//@RegisterRestClient
//@RegisterProvider(SomeClass.class)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface StravaApiClient {
    /**
     * Gets activities of the current user set by Authorization access token.
     * @return
     */
    @POST
    @Path("activities")
    Response<String> getActivities();
}
