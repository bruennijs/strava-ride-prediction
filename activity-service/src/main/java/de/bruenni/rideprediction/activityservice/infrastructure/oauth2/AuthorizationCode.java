package de.bruenni.rideprediction.activityservice.infrastructure.oauth2;

import de.bruenni.rideprediction.activityservice.infrastructure.domain.SingleValueObject;

import static org.apache.commons.lang3.Validate.notEmpty;

/**
 *
 * @author Oliver Brüntje
 */
public class AuthorizationCode extends SingleValueObject<String> {
    public AuthorizationCode(String code) {
        super(notEmpty(code, "code may not be empty"));
    }

    @Override public String toString() {
        return "AuthorizationCode{} " + super.toString();
    }
}
