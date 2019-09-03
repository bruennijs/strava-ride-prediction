package de.bruenni.rideprediction.identity.api;

import de.bruenni.rideprediction.activityservice.infrastructure.domain.SingleValueObject;

import static org.apache.commons.lang3.Validate.notEmpty;

/**
 * Id Token JWT of OIDC id specification.
 *
 * @author Oliver Brüntje
 */
public class IdToken extends Token {
    public IdToken(String initialValue) {
        super(notEmpty(initialValue, "IdToken may not be null or empty"));
    }
}
