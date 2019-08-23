package de.bruenni.rideprediction.infrastructure.rest.client.filter.filter;

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
@Provider
@Priority(Integer.MAX_VALUE)
public class LogHeaderClientRequestFilter implements ClientRequestFilter, ClientResponseFilter {

  private static Logger LOG = LoggerFactory.getLogger(LogHeaderClientRequestFilter.class);

  @Override
  public void filter(ClientRequestContext requestContext) throws IOException {
    LOG.info("==== REQUEST: " + requestContext.getMethod() + " " + requestContext.getUri() + " ====");
    logHeaders(requestContext.getStringHeaders());
  }

  @Override
  public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
    LOG.info("==== RESPONSE " + responseContext.getStatus() + " ====");
    logHeaders(responseContext.getHeaders());
  }

  private void logHeaders(MultivaluedMap<String, String> map) {
    map.forEach((key, value) -> {
      LOG.info(key + ":" + value.stream().reduce("", (item1, item2) -> item1 + "=" + item2, (s, s2) -> s + s2));
    });
  }
}
