package de.bruenni.rideprediction.identity.impl.strava;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@RunWith(JUnit4.class)
public class StravaTokenExchangeResponseTest {

    private static Jsonb JSONB;

    @BeforeClass
    public static void setUp() throws Exception {
        JSONB = JsonbBuilder.create();
    }

    @Test
    public void getAccessToken() {

        StravaTokenExchangeResponse response = JSONB.fromJson("{\n"
                + "  \"token_type\": \"Bearer\",\n"
                + "  \"access_token\": \"987654321234567898765432123456789\",\n"
                + "  \"athlete\": {\n"
                + "    \"id\":\"12345\"\n"
                + "  },\n"
                + "  \"refresh_token\": \"1234567898765432112345678987654321\",\n"
                + "  \"expires_at\": 1531378346,\n"
                + "  \"state\": \"STRAVA\"\n"
                + "}", StravaTokenExchangeResponse.class);

        Assert.assertEquals("987654321234567898765432123456789", response.getAccessToken().getValue());
    }
}
