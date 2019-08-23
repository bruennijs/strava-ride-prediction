package de.bruenni.rideprediction.infrastructure.oauth2;

import static org.apache.commons.lang3.Validate.notEmpty;

public class UserProfile {
    private String id;

    private String accessToken;

    private String username;

    public UserProfile(String id, String accessToken, String username) {
        this.id = notEmpty(id, "id may not be empty");
        this.accessToken = notEmpty(accessToken, "accessToken may not be empty");
        this.username = notEmpty(username, "username may mnot be empty");
    }

    public String getId() {
        return id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUsername() {
        return username;
    }
}
