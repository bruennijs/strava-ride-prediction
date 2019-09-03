package de.bruenni.rideprediction.activityservice.domain.athlete;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.rest.RestStatus;

public class ElasticSearchDocumentUpdateFailed extends RuntimeException {
    public ElasticSearchDocumentUpdateFailed(RestStatus status, DocWriteResponse.Result result) {
        super("status=" + status.getStatus() + ", result=" + result.getLowercase());
    }
}
