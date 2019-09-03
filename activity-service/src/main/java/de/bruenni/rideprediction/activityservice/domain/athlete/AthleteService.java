package de.bruenni.rideprediction.activityservice.domain.athlete;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

/**
 * Athlete domain service.
 *
 * @author Oliver Brüntje
 */
@ApplicationScoped
public class AthleteService {

    @Inject
    private JsonWebToken jwt;

    @Inject
    private AthleteRepository repository;

    /**
     * Checks whether athlete entity already exists.
     * If not one is created using data form JWT token of current user.
     * @return New or existing athlete.
     */
    public Athlete getOrCreateAthlete() {
        String athleteId = this.jwt.getSubject();

        Optional<Athlete> athlete = repository.get(athleteId);

        if (athlete.isPresent()) {
            return athlete.get();
        } else {
            return createNewAthlete(athleteId);
        }

    }

    private Athlete createNewAthlete(String id) {
        Athlete newAthlete = new Athlete(id, this.jwt.getSubject(), Collections.emptyList());

        repository.create(newAthlete);

        return newAthlete;
    }
}
