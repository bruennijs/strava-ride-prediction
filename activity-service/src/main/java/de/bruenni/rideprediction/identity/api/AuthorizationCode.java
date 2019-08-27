package de.bruenni.rideprediction.identity.api;

import de.bruenni.rideprediction.activityservice.infrastructure.domain.SingleValueObject;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static org.apache.commons.lang3.Validate.notEmpty;

/**
 *
 * @author Oliver Brüntje
 */
public class AuthorizationCode extends SingleValueObject<String> {
    public AuthorizationCode(@NotEmpty String code) {
        super(notEmpty(code, "code may not be empty"));
    }

    @Override public String toString() {
        return "AuthorizationCode{} " + super.toString();
    }
}
