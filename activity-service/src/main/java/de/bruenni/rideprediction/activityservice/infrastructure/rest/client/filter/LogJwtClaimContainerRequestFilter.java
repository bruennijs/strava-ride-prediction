package de.bruenni.rideprediction.activityservice.infrastructure.rest.client.filter;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
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
public class LogJwtClaimContainerRequestFilter implements ContainerRequestFilter {

  private static final Logger LOG = LoggerFactory.getLogger(LogJwtClaimContainerRequestFilter.class);

  private final String claimName;

  public LogJwtClaimContainerRequestFilter() {
    this.claimName = Claims.sub.name();
  }

  @Inject
  private Instance<JsonWebToken> jwt;

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    if (this.jwt.isResolvable()) {
      LOG.info("JWT.'" + claimName + "'=" + this.jwt.get().claim(this.claimName));
    }
  }
}
