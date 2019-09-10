package de.bruenni.rideprediction.activityservice.infrastructure;

import de.bruenni.rideprediction.identity.api.Token;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang3.Validate.notEmpty;

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
    public <T extends Token> void log(T jwtToken) {
        try {
            JwtClaims claims = parseJwt(jwtToken.getValue());

            LOG.info(jwtToken.getClass().getSimpleName() + " = [json=" + claims.toJson() + "]");
            LOG.info(jwtToken.getClass().getSimpleName() + " = [raw=" + jwtToken + "]");
        } catch (InvalidJwtException e) {
            LOG.error("Cannot parse " + jwtToken.getClass().getSimpleName() + " = [raw=" + jwtToken + "]");
        }
    }

    /**
     * Logs JWT.
     * @param jwtToken
     */
    public void log(String jwtToken) {
        try {
            JwtClaims claims = parseJwt(jwtToken);

            LOG.info("jwt = [" + claims.toJson() + "]");
        } catch (InvalidJwtException e) {
            LOG.error("Cannot parse jwt [jwt = [" + jwtToken + "]]");
        }
    }

    private JwtClaims parseJwt(String jwtToken) throws InvalidJwtException {
        JwtConsumer consumer = new JwtConsumerBuilder()
                .setSkipAllValidators()
                .setSkipSignatureVerification().build();
        return consumer.processToClaims(jwtToken);
    }
}
