package de.bruenni.rideprediction.activityservice.domain.activity;

import de.bruenni.rideprediction.activityservice.domain.athlete.AthleteRepository;
import de.bruenni.rideprediction.activityservice.domain.strava.StravaActivity;
import de.bruenni.rideprediction.activityservice.domain.strava.StravaActivityBuilder;
import de.bruenni.rideprediction.activityservice.infrastructure.strava.client.StravaApiClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.*;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Service managing activity related processing.
 *
 * @author Oliver Brüntje
 */
@ApplicationScoped
public class ActivityService {

    private static Logger LOG = LoggerFactory.getLogger(ActivityService.class);

    private StravaApiClient client;

    private AthleteRepository athleteRepository;

    private ActivityRepository activityRepository;

    @Inject
    protected ActivityService(StravaApiClient client, AthleteRepository athleteRepository, ActivityRepository activityRepository) {
        this.client = notNull(client, "client cannot be null");
        this.athleteRepository = notNull(athleteRepository, "athleteRepository cannot be null");
        this.activityRepository = notNull(activityRepository, "activityRepository cannot be null");
    }

    protected ActivityService() {
        // CDI
    }

    /**
     * Synchronizes using using JWT of current security context.
     * So can be called only within request scope of jax-rs call.
     */
    public void synchronize() {
        // todo: get latest download data and retrieve only newest

        int i=1;
        while(true) {
            JsonArray activityArray = client.getActivities(10, i);

            // get id to put to _doc resource to avoid duplicate activities when oberlapping activities
            // are retrieved by strava API client.
            LOG.debug("Sync strava activties result [count=" + activityArray.size() + "]");

            if (activityArray.size() > 0) {
                for (JsonValue activity : activityArray) {
                    JsonObject activityJsonObject = activity.asJsonObject();

                    StravaActivity stravaActivity = parseStravaActivity(activityJsonObject);

                    StravaActivityBuilder builder = new StravaActivityBuilder(activityJsonObject);
                    handleActivityStreams(stravaActivity.getId(), builder);

                    // handleActivityZones(stravaActivityDto);

                    stravaActivity = builder.build();
                    activityRepository.create(stravaActivity.getId().toString(), stravaActivity.toJsonString());
                }

                i++;
            } else {
                break;
            }
        }
    }

    protected StravaActivity parseStravaActivity(JsonObject activityJsonObject) {
        StravaActivityBuilder builder = new StravaActivityBuilder(activityJsonObject);
        return builder.build();
    }

    private void handleActivityStreams(Long activityId, StravaActivityBuilder builder) {
        String keys = "altitude,latlng,velocity_smooth,time,distance,cadence,grade_smooth,watts";
        try {
            JsonArray streams = client.getActivityStreams(activityId, keys, true);

            LOG.debug("set activity streams [activity id=" + activityId + "]");

            builder.withStreams(streams);
        } catch (Exception e) {
            LOG.error("get activity streams failed [activity id=" + activityId + ",keys=" + keys + "]");
        }
    }

    /**
     * Loads activity zones by activity id and sets to activity dto document.
     */
    /*
    protected void handleActivityZones(StravaActivityDto stravaActivityDto) {
        // get heart rate zones if activity has heart rates
        if( stravaActivityDto.hasHeartRate()) {
            // load heart rate zones
            try {
                JsonArray activityZones = client.getActivityZones(stravaActivityDto.getId());

                if (activityZones.size() > 0) {
                    LOG.debug("activity zones available [activity id=" + stravaActivityDto.getId() + "]");
                    stravaActivityDto.setHeartRateZones(activityZones);
                }
            } catch (Exception e) {
                LOG.error("get strava activity zones failed and will be ignored", e);
            }
        }
    }*/


}
