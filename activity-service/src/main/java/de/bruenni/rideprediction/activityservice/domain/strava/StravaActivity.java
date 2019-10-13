package de.bruenni.rideprediction.activityservice.domain.strava;

import static org.apache.commons.lang3.Validate.notNull;

import javax.json.JsonObject;

/**
 * Represents a activty in bounded context of 'strava'.
 *
 * @author Oliver Brüntje
 */
public class StravaActivity {
    private JsonObject json;

    /**
     * CTor.
     * @param json json read from strava
     */
    public StravaActivity(JsonObject json) {
        this.json = notNull(json, "json cannot be null");
    }

    public Long getId() {
        return Long.valueOf(this.json.getJsonNumber("id").longValue());
    }

    public boolean hasHeartRate() {
        return this.json.getBoolean("has_heartrate", false);
    }

    public String toJsonString() {
        return json.toString();
    }
}
