package de.bruenni.rideprediction.activityservice.domain.activity;

import de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch.ElasticSearchRepository;
import de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch.ElasticSearchRepositoryBase;
import de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch.Persistence;
import org.apache.commons.lang3.tuple.Pair;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.bind.Jsonb;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Repo for storing and retrieving athletes data
 *
 * @author Oliver Brüntje
 */
@Singleton
public class ActivityRepository extends ElasticSearchRepositoryBase {

    private final static String INDEX = "activity";

    @Inject
    public ActivityRepository(RestHighLevelClient client) {
        super(client, INDEX);
    }

    /**
     * Creates activity by raw json.
     * @param document activity document to create in index
     */
    public void create(String id, String document) {
        createRaw(id, document);
    }

    public Pair<Integer, Integer> aggregateActivity(String athleteId) {
        return null;
    }
}
