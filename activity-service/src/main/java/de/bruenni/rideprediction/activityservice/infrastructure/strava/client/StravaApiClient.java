package de.bruenni.rideprediction.activityservice.infrastructure.strava.client;

import java.time.temporal.ChronoUnit;

import javax.json.JsonArray;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.faulttolerance.Retry;

@Path("/")
//@RegisterRestClient
//@RegisterProvider(SomeClass.class)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface StravaApiClient {
    /**
     * Gets activities of the current user set by Authorization access token.
     * @return Activities of this athlete
     * See https://developers.strava.com/docs/reference/#api-Activities-getLoggedInAthleteActivities
     * @param perPage per_page
     * @param page current page number
     */
    @GET
    @Path("/athlete/activities")
    JsonArray getActivities(@QueryParam(value = "per_page") int perPage, @QueryParam(value = "page") int page);

    /**
     * Gets heart rate zones (see https://developers.strava.com/docs/reference/#api-models-ActivityZone)
     * @param activityId activity id
     * @return Array of ActivityZone objects
     */
    @GET
    @Path("/activities/{id}/zones")
    JsonArray getActivityZones(@PathParam(value = "id") Long activityId);

    /**
     * Gets activity streams by type.
     * See https://developers.strava.com/docs/reference/#api-Streams-getActivityStreams
     *
     * @param activityId
     * @param keys comma seperated list of keys
     * @param keyByType
     * @return
     */
    @GET
    @Path("/activities/{id}/streams")
    @Retry(maxRetries = 3, delay = 1000, delayUnit = ChronoUnit.MILLIS)
    JsonArray getActivityStreams(@PathParam(value = "id") Long activityId,
            @QueryParam(value = "keys") String keys,
            @QueryParam(value = "key_by_type") boolean keyByType);
}
