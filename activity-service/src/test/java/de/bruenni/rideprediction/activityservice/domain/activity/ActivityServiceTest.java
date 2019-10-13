package de.bruenni.rideprediction.activityservice.domain.activity;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.skyscreamer.jsonassert.JSONAssert;

import de.bruenni.rideprediction.activityservice.domain.strava.StravaActivity;
import de.bruenni.rideprediction.activityservice.domain.strava.StravaActivityBuilder;

@RunWith(JUnit4.class)
public class ActivityServiceTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testStravaActivityDtoSetStreams() throws JSONException {
        JsonObject root = Json.createObjectBuilder().build();

        JsonArray streamsArray = createExampleArray();

        StravaActivity dto = new StravaActivityBuilder(root)
                .withStreams(streamsArray)
                .build();

        // THEN
        JSONAssert.assertEquals("{\"streams\": [5]}", dto.toJsonString(), false);
    }

    protected JsonArray createExampleArray() {
        JsonArrayBuilder streamsArrayBuilder = Json.createArrayBuilder();
        streamsArrayBuilder.add(5);
        return streamsArrayBuilder.build();
    }
}
