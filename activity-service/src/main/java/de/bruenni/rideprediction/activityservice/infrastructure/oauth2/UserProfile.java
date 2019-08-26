package de.bruenni.rideprediction.activityservice.infrastructure.oauth2;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

public class UserProfile {
    private String id;

    private AccessToken accessToken;

    private String username;

    public UserProfile(String id, AccessToken accessToken, String username) {
        this.id = notEmpty(id, "id may not be empty");
        this.accessToken = notNull(accessToken, "accessToken may not be empty");
        this.username = notEmpty(username, "username may mnot be empty");
    }

    public String getId() {
        return id;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public String getUsername() {
        return username;
    }
}
