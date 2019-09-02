package de.bruenni.rideprediction.identity.infrastructure;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs JWTs to slf4j logger.
 *
 * @author Oliver Brüntje
 */
public class JwtLogger {
    private static Logger LOG = LoggerFactory.getLogger(JwtLogger.class);

    /**
     * Logs JWT.
     * @param jwtToken
     */
    public void log(String jwtToken) {
        try {
            JwtConsumer consumer = new JwtConsumerBuilder()
                    .setSkipAllValidators()
                    .setSkipSignatureVerification().build();
            JwtClaims claims = consumer.processToClaims(jwtToken);

            LOG.info("jwt = [" + claims.toJson() + "]");
        } catch (InvalidJwtException e) {
            LOG.error("Cannot parse jwt [jwt = [" + jwtToken + "]]");
        }
    }
}
