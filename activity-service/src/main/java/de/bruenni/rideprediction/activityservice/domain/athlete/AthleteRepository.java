package de.bruenni.rideprediction.activityservice.domain.athlete;

import de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch.ElasticSearchDocumentCreationFailed;
import de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch.Persistence;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
@ApplicationScoped
public class AthleteRepository {

    private final static String INDEX = "athlete";

    @Inject
    private RestHighLevelClient client;

    @Inject
    @Persistence
    private Jsonb json;

    @Inject
    public AthleteRepository(@Persistence Jsonb json, RestHighLevelClient client) {
        this.json = json;
        this.client = client;
    }

    protected AthleteRepository() {
        // CDI
    }

    /**
     * Creates new athlete.
     * @param newAthlete instance to serialize
     */
    public void create(Athlete newAthlete) {

        String json = this.json.toJson(newAthlete);

        IndexRequest request = new IndexRequest(INDEX)
                .id(newAthlete.getId())
                .source(json, XContentType.JSON)
                .opType(DocWriteRequest.OpType.CREATE);

        try {
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            throwOnOpTypeCreateFailed(response);
        } catch (IOException e) {
            throw new ElasticSearchRepositoryException("create athlete failed" ,e);
        }
    }

    /**
     * Gets athlete by id.
     * @param id unique id
     * @return Athlete if id exists; else empty optional.
     */
    public Optional<Athlete> get(String id) {

        GetRequest request = new GetRequest()
                .index(INDEX)
                .id(id);

        try {
            GetResponse response = client.get(request, RequestOptions.DEFAULT);
            if (response.isExists()) {
                Athlete athlete = json.fromJson(response.getSourceAsString(), Athlete.class);
                return Optional.of(athlete);
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new ElasticSearchRepositoryException("get athlete failed [id=" + id + "]",e);
        }
    }

    /**
     * Adds activity to document.
     * @param id unique id of athlete
     * @param activities activities to add
     * @return Updated athlete.
     */
    public Athlete addActivity(String id, List<String> activities) {

        Map<String, Object> map = new HashMap<>();
        map.put(Athlete.FIELD_ACTIVITIES, activities);

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
    }

    private void throwOnUpdateFailed(UpdateResponse response) {
        if (response.getResult() != DocWriteResponse.Result.UPDATED) {
            throw new ElasticSearchDocumentUpdateFailed(response.status(), response.getResult());
        }
    }

    public LocalDateTime getLatestActivityStartDate() {
        return null;
    }

    private void throwOnOpTypeCreateFailed(IndexResponse response) {
        if ((response.status().getStatus() != 201 && response.status().getStatus() != 200) ||
                (response.getResult() != DocWriteResponse.Result.CREATED)) {
            throw new ElasticSearchDocumentCreationFailed(response.status(), response.getResult());
        }
    }
}
