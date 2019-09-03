package de.bruenni.rideprediction.identity.api;

import static org.apache.commons.lang3.Validate.notEmpty;

public class AccessToken extends Token {
    public AccessToken(String initialValue) {
        super(notEmpty(initialValue, "AccessToken may not be null or empty"));
    }

    protected AccessToken() {
        super("");
        // CDI
    }
}
