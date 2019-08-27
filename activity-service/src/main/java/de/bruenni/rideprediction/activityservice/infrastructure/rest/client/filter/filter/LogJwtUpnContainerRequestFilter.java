package de.bruenni.rideprediction.activityservice.infrastructure.rest.client.filter.filter;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Logs JWT upn header claim
 *
 * @author Oliver Brüntje
 */
@Provider
public class LogJwtUpnContainerRequestFilter implements ContainerRequestFilter {

  private static final Logger LOG = LoggerFactory.getLogger(LogJwtUpnContainerRequestFilter.class);

  @Inject
  @Claim(standard = Claims.upn)
  private Instance<String> upn;

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    if (this.upn.isResolvable()) {
      LOG.info("JWT upn claim value: " + this.upn.get());
    } else {
      LOG.info("Could not find any mp-jwt");
    }
  }
}
