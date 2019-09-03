package de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.rest.RestStatus;

/**
 * 
 *
 * @author Oliver Brüntje
 */
public class ElasticSearchDocumentCreationFailed extends RuntimeException {
    public ElasticSearchDocumentCreationFailed(RestStatus status, DocWriteResponse.Result result) {
        super("status=" + status.getStatus() + ", result=" + result.getLowercase());
    }
}
