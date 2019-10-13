package de.bruenni.rideprediction.activityservice.infrastructure.rest.client.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Logs HTTP headers to debug slf4j.
 *
 * @author Oliver Brüntje
 */
// @Provider
// @Priority(Integer.MAX_VALUE)
public class LogHeaderClientRequestFilter implements ClientRequestFilter, ClientResponseFilter {

  private static Logger LOG = LoggerFactory.getLogger(LogHeaderClientRequestFilter.class);

  private boolean logRequestLine;

  private boolean logHeader;

  public LogHeaderClientRequestFilter(boolean logRequestLine, boolean logHeader) {
    this.logRequestLine = logRequestLine;
    this.logHeader = logHeader;
  }

  protected LogHeaderClientRequestFilter() {
    this(true, true);
    // CDI
  }

  @Override
  public void filter(ClientRequestContext requestContext) throws IOException {
    if (logRequestLine) {
      LOG.debug("==== REQUEST: {} {} ====", requestContext.getMethod(), requestContext.getUri());
    }
    logHeaders(requestContext.getStringHeaders());
  }

  @Override
  public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
    if (logRequestLine) {
      LOG.debug("==== RESPONSE {} ====", responseContext.getStatus());
    }
    logHeaders(responseContext.getHeaders());
  }

  private void logHeaders(MultivaluedMap<String, String> map) {
    if (logHeader) {
      map.forEach((key, value) -> {
        LOG.debug("{}: {}", key, value.stream().reduce("", (item1, item2) -> item1 + "=" + item2, (s, s2) -> s + s2));
      });
    }
  }
}
