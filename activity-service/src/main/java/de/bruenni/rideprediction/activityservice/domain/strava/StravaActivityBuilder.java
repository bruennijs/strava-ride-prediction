package de.bruenni.rideprediction.activityservice.domain.strava;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

public class StravaActivityBuilder {
    private JsonObjectBuilder builder;

    public StravaActivityBuilder(JsonObject loadFrom) {
        this.builder = Json.createObjectBuilder(loadFrom);
    }

    public static StravaActivityBuilder from(JsonObject json) {
        return new StravaActivityBuilder(json);
    }

    public void withHeartRateZones(JsonValue value) {
        this.builder.add("heart_rate_zones", value);
    }

    public StravaActivity build() {
        return new StravaActivity(builder.build());
    }

    public StravaActivityBuilder withStreams(JsonValue streams) {
        this.builder.add("streams", streams);
        return this;
    }
}
