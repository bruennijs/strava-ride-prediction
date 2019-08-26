package de.bruenni.rideprediction.activityservice.application.auth.strava;

import de.bruenni.rideprediction.activityservice.infrastructure.oauth2.AccessToken;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;

/**
 * Represents an oauth2 strava response containing access_token and user information.
 * https://developers.strava.com/docs/authentication/
 *
 * @author Oliver Brüntje
 */
public class StravaTokenExchangeResponse {

    private AccessToken accessToken;

    @JsonbCreator
    public StravaTokenExchangeResponse(@JsonbProperty(value = "access_token") String accessToken) {
        this.accessToken = new AccessToken(accessToken);
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }
}