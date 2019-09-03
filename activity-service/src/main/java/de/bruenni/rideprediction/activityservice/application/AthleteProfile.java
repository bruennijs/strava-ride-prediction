package de.bruenni.rideprediction.activityservice.application;

import de.bruenni.rideprediction.activityservice.domain.athlete.Athlete;

import java.time.LocalDateTime;

/**
 * DTO for user's profile.
 *
 * @author Oliver Brüntje
 */
public class AthleteProfile {
    private Athlete athlete;
    private int activityCount;

    public AthleteProfile(Athlete athlete, int activityCount) {
        this.athlete = athlete;
        this.activityCount = activityCount;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public int getActivityCount() {
        return activityCount;
    }
}
