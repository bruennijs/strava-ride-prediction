package de.bruenni.rideprediction.activityservice.domain.activity;

import de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch.ElasticSearchRepositoryBase;
import org.apache.commons.lang3.tuple.Pair;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import javax.inject.Inject;
import javax.inject.Singleton;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Repo for storing and retrieving athletes data
 *
 * @author Oliver Brüntje
 */
@Singleton
public class ActivityRepository extends ElasticSearchRepositoryBase {

    private final static String INDEX = "activity";

    private final static String PIPELINE = "activity-pipeline";

    @Inject
    public ActivityRepository(RestHighLevelClient client) {
        super(client, INDEX);
    }

    /**
     * Creates activity by raw json.
     * @param document activity document to create in index
     */
    public void create(String id, String document) {
        IndexRequest request = new IndexRequest(INDEX)
                .id(id)
                .source(document, XContentType.JSON)
                .setPipeline(PIPELINE)
                .opType(DocWriteRequest.OpType.INDEX);

        index(request);
    }

    public Pair<Integer, Integer> aggregateActivity(String athleteId) {
        return null;
    }
}
