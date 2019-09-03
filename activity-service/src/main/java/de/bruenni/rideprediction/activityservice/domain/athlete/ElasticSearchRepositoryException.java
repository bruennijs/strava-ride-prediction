package de.bruenni.rideprediction.activityservice.domain.athlete;

import java.io.IOException;

/**
 * thrown in Elastic search repositories on Elastic client errors.
 *
 * @author Oliver Brüntje
 */
public class ElasticSearchRepositoryException extends RuntimeException {
    public ElasticSearchRepositoryException(String message, IOException e) {
        super(message, e);
    }
}
