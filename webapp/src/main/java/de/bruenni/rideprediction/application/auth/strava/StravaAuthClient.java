package de.bruenni.rideprediction.application.auth.strava;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/todo-service/api/todos")
//@RegisterRestClient
//@RegisterProvider(TodoServiceResponseExceptionMapper.class)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface StravaAuthClient {
}
