package de.bruenni.rideprediction.activityservice.domain.athlete;

import de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch.ElasticSearchRepository;
import de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch.ElasticSearchRepositoryException;
import de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch.Persistence;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.bind.Jsonb;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Repo for storing and retrieving athletes data
 *
 * @author Oliver Brüntje
 */
@Singleton
public class AthleteRepository extends ElasticSearchRepository {

    private final static String INDEX = "athlete";

    @Inject
    public AthleteRepository(@Persistence Jsonb json, RestHighLevelClient client) {
        super(json, client, INDEX, Athlete.class);
        this.json = json;
    }

/*    *//**
     * Adds activity to document.
     * @param id unique id of athlete
     * @param activities activities to add
     * @return Updated athlete.
     *//*
    public Athlete addActivity(String id, List<String> activities) {

        Map<String, Object> map = new HashMap<>();
        map.put(Athlete.FIELD_AUTH_IDS, activities);

        UpdateRequest request = new UpdateRequest()
                .index(INDEX)
                .id(id)
                .fetchSource(true) // fetches updated document
                .doc(map);

        try {
            UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
            throwOnUpdateFailed(response);
            String athleteJson = response.getGetResult().sourceAsString();
            return json.fromJson(athleteJson, Athlete.class);
        } catch (IOException e) {
            throw new ElasticSearchRepositoryException("add athlete's activitiy failed [id=" + id + "]",e);
        }
    }*/

    public LocalDateTime getLatestActivityStartDate() {
        return null;
    }
}
