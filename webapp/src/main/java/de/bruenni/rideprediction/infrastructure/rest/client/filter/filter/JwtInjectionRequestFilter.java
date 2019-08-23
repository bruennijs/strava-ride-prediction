/*
 * Copyright (C) open knowledge GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */

package de.bruenni.rideprediction.infrastructure.rest.client.filter.filter;

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
