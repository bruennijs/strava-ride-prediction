package de.bruenni.rideprediction.activityservice.domain.activity;

import de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch.ElasticSearchRepository;
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
public class ActivityRepository {

    private final static String INDEX = "activity";

    private final RestHighLevelClient client;

    @Inject
    public ActivityRepository(RestHighLevelClient client) {
        this.client = notNull(client, "client cannot be null");
    }

    public Pair<Integer, Integer> aggregateActivity(String athleteId) {
        return null;
    }
}
