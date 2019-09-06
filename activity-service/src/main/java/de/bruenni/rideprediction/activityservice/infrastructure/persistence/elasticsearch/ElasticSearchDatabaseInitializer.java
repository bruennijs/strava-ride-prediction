package de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch;

import org.apache.commons.io.IOUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * Initializes elastic search database by executing scripts
 *
 * @author Oliver Brüntje
 */
@ApplicationScoped
public class ElasticSearchDatabaseInitializer {

    private RestHighLevelClient client;

    @Inject
    public ElasticSearchDatabaseInitializer(RestHighLevelClient client) {
        // CDI
        this.client = notNull(client, "client cannot be null");
    }

    /**
     * Run es rest client commands
     * @param init
     */
    public void onContainerStartup(@Observes @Initialized(value = ApplicationScoped.class) Object init) throws IOException {
        String indexMapping = IOUtils.toString(getClass().getResourceAsStream("/elasticsearch/create_index_athlete.json"));

        Request request = new Request("PUT", "athlete");
        request.setJsonEntity(indexMapping);

        this.client.getLowLevelClient().performRequest(request);
    }
}
