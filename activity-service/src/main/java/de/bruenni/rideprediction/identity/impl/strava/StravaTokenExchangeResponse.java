package de.bruenni.rideprediction.identity.impl.strava;

import de.bruenni.rideprediction.identity.api.AccessToken;

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
