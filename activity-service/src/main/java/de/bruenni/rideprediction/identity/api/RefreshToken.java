package de.bruenni.rideprediction.identity.api;

import de.bruenni.rideprediction.activityservice.infrastructure.domain.SingleValueObject;

import static org.apache.commons.lang3.Validate.notEmpty;

public class RefreshToken extends SingleValueObject<String> {
    public RefreshToken(String initialValue) {
        super(notEmpty(initialValue, "AccessToken may not be null or empty"));
    }
}
