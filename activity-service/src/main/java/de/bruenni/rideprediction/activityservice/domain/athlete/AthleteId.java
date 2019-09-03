package de.bruenni.rideprediction.activityservice.domain.athlete;

import de.bruenni.rideprediction.activityservice.infrastructure.domain.SingleValueObject;

/**
 * Athlete unique natural id.
 *
 * @author Oliver Brüntje
 */
public class AthleteId extends SingleValueObject<String> {
    public AthleteId(String initialValue) {
        super(initialValue);
    }
}
