package de.bruenni.rideprediction.activityservice.application;

import de.bruenni.rideprediction.activityservice.domain.athlete.Athlete;
import de.bruenni.rideprediction.activityservice.domain.athlete.AthleteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST APPI to control athletes
 *
 * @author Oliver Brüntje
 */
@ApplicationScoped
@Path("/activity-service/athletes")
@RolesAllowed({"strava:athlete"})
@Produces(value = { MediaType.APPLICATION_JSON })
@Consumes(value = { MediaType.APPLICATION_JSON })
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
