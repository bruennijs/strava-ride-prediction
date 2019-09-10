package de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch.cdi;

import de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch.Persistence;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.yasson.internal.JsonBindingBuilder;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Scope;
import javax.inject.Singleton;
import javax.json.bind.Jsonb;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides elastic search db stuff.
 *
 * @author Oliver Brüntje
 */
@ApplicationScoped
public class ElasticSearchProvider {

    @Inject
    @ConfigProperty(name = "persistence.elasticsearch.port")
    private int port;

    @Inject
    @ConfigProperty(name = "persistence.elasticsearch.host")
    private String host;

    @Singleton  // not a NormalScope bean so no no-args constructor for weld proxy is available
    @Produces
    public RestHighLevelClient elasticSearchClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(this.host, this.port, "http")));
    }

    @Produces
    @Persistence
    public Jsonb jsonb() {
        return new JsonBindingBuilder().build();
    }

    @Produces
    public List<Request> getInitializerRequests() throws IOException {
        ArrayList<Request> requestList = new ArrayList<>();
        requestList.add(createAthleteIndexRequest());
        requestList.add(createActivityIndexRequest());
        requestList.add(createActivityPipelineRequest());

        return requestList;
    }

    private Request createActivityPipelineRequest() throws IOException {
        String body = IOUtils.toString(getClass().getResourceAsStream("/elasticsearch/create_activity_pipeline.json"));

        Request request = new Request("PUT", "_ingest/pipeline/activity-pipeline");
        request.setJsonEntity(body);
        return request;
    }

    private Request createActivityIndexRequest() throws IOException {
        String body = IOUtils.toString(getClass().getResourceAsStream("/elasticsearch/create_index_activity.json"));

        Request request = new Request("PUT", "activity");
        request.setJsonEntity(body);
        return request;
    }

    private Request createAthleteIndexRequest() throws IOException {
        String body = IOUtils.toString(getClass().getResourceAsStream("/elasticsearch/create_index_athlete.json"));

        Request request = new Request("PUT", "athlete");
        request.setJsonEntity(body);
        return request;
    }
}
