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
        String jwtSubject = this.jwt.getSubject();

        Optional<Athlete> athlete = repository.get(jwtSubject);

        if (athlete.isPresent()) {
            return athlete.get();
        } else {
            return createNewAthlete(jwtSubject);
        }

    }

    private Athlete createNewAthlete(String jwtSubject) {
        String nickName = getNickName();

        Athlete newAthlete = new Athlete(jwtSubject, nickName, Collections.singletonList(jwtSubject));

        repository.create(newAthlete);

        return newAthlete;
    }

    private String getNickName() {
        return jwt.claim("nickname").orElse("unknown").toString();
    }
}
