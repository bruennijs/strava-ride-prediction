package de.bruenni.rideprediction.activityservice.infrastructure.rest.client.filter;

import de.bruenni.rideprediction.identity.infrastructure.JwtLogger;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Optional;

/**
 * Logs JWT.
 *
 * @author Oliver Brüntje
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class LogJwtContainerRequestFilter implements ContainerRequestFilter {

  private static final Logger LOG = LoggerFactory.getLogger(LogJwtContainerRequestFilter.class);

  @Inject
  private Instance<JsonWebToken> jwt;

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    Optional<JsonWebToken> jsonWebToken = getJsonWebToken();

    jsonWebToken.ifPresent(jwt -> {
        new JwtLogger().log(jwt.getRawToken());
    });
  }

  private Optional<JsonWebToken> getJsonWebToken() {
    if (jwt.isResolvable()) {
      return Optional.ofNullable(jwt.get());
    } else {
      return Optional.empty();
    }
  }
}
