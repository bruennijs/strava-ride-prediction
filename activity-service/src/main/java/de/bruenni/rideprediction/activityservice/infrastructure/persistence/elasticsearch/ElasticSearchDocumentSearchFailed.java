package de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.rest.RestStatus;

/**
 * Search failed.
 *
 * @author Oliver Brüntje
 */
public class ElasticSearchDocumentSearchFailed extends RuntimeException {
    public ElasticSearchDocumentSearchFailed(RestStatus status) {
        super("status=" + status.getStatus());
    }
}
