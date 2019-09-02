package de.bruenni.rideprediction.activityservice.infrastructure.rest.client.filter;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;

/**
 * Uses the JWT token of the current request and adds authorization header
 * of the current request.
 *
 * @author Oliver Brüntje
 */
@RequestScoped
public class JwtInjectionRequestFilter implements ClientRequestFilter {

  private static Logger LOGGER = LoggerFactory.getLogger(JwtInjectionRequestFilter.class);

  @Inject
  protected JsonWebToken jsonWebToken;

  /**
   * Needed by CDI
   */
  protected JwtInjectionRequestFilter() {
  }

  /**
   * Adds Authorization header to the current request.
   * @param requestContext
   */
  @Override
  public void filter(ClientRequestContext requestContext)  {
    LOGGER.debug("Add Authentication header to request [uri=" + requestContext.getUri() + "]");
    requestContext.getHeaders().add(HttpHeaders.AUTHORIZATION, this.jsonWebToken.getRawToken());
  }
}
