package de.bruenni.rideprediction.activityservice.domain.activity;

import de.bruenni.rideprediction.activityservice.domain.athlete.AthleteRepository;
import de.bruenni.rideprediction.activityservice.infrastructure.strava.client.StravaApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.*;
import javax.json.stream.JsonParserFactory;
import javax.ws.rs.core.Response;

import java.util.UUID;

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

        JsonArray activityArray = client.getActivities();

        // get id to put to _doc resource to avoid duplicate activities when oberlapping activities
        // are retrieved by strava API client.
        LOG.debug("Sync strava activties result [count=" + activityArray.size() + "]");

        for (JsonValue activity : activityArray) {
            activityRepository.create(UUID.randomUUID().toString(), activity.toString());
        }
    }
}
