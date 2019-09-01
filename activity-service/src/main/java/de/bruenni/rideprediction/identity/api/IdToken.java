package de.bruenni.rideprediction.identity.api;

import de.bruenni.rideprediction.activityservice.infrastructure.domain.SingleValueObject;

import static org.apache.commons.lang3.Validate.notEmpty;

public class IdToken extends SingleValueObject<String> {
    public IdToken(String initialValue) {
        super(notEmpty(initialValue, "IdToken may not be null or empty"));
    }
}
