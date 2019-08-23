package de.bruenni.rideprediction.application.auth.strava;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/oauth/token")
//@RegisterRestClient
//@RegisterProvider(SomeClass.class)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface StravaAuthClient {
    /**
     * OAuth2 token exchange.
     * @param clientId
     * @param clientSecret
     * @param authorizationCode
     * @param grantType
     * @return
     */
    @POST
    @Path("")
    StravaTokenExchangeResponse getAuthToken(@QueryParam(value = "client_id") String clientId,
            @QueryParam(value = "client_secret") String clientSecret,
            @QueryParam(value = "code") String authorizationCode,
            @QueryParam(value = "grant_type") String grantType);
}
