package de.bruenni.rideprediction.infrastructure.oauth2;

import de.bruenni.rideprediction.infrastructure.domain.SingleValueObject;

import static org.apache.commons.lang3.Validate.notEmpty;

public class AccessToken extends SingleValueObject<String> {
    public AccessToken(String initialValue) {
        super(notEmpty(initialValue, "AccessToken may not be null or empty"));
    }
}
