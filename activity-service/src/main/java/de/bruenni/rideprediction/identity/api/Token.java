package de.bruenni.rideprediction.identity.api;

import de.bruenni.rideprediction.activityservice.infrastructure.domain.SingleValueObject;

/**
 * Base class of all tokens
 *
 * @author Oliver Brüntje
 */
public class Token extends SingleValueObject<String> {
    public Token(String initialValue) {super(initialValue);}
}
