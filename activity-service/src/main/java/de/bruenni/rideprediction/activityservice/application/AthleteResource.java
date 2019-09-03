package de.bruenni.rideprediction.activityservice.application;

import de.bruenni.rideprediction.activityservice.domain.athlete.Athlete;
import de.bruenni.rideprediction.activityservice.domain.athlete.AthleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * REST APPI to control athletes
 *
 * @author Oliver Brüntje
 */
@ApplicationScoped
public class AthleteResource {

    private static Logger LOG = LoggerFactory.getLogger(ActivityResource.class);

    @Inject
    private AthleteService athleteService;

    /**
     * Gets the athlete profile
     * @return
     */
    @GET
    @Path("/profile")
    public AthleteProfile getProfile() {

        Athlete athlete = this.athleteService.getOrCreateAthlete();

        return new AthleteProfile(athlete, 0);
    }
}
