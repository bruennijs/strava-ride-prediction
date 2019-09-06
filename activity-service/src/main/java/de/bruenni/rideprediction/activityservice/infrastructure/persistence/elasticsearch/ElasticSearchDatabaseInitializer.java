package de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch;

import org.apache.commons.io.IOUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static Logger LOG = LoggerFactory.getLogger(ElasticSearchDatabaseInitializer.class);

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

        LOG.info("Initializing elastic search db [/elasticsearch/create_index_athlete.json]");

        String indexMapping = IOUtils.toString(getClass().getResourceAsStream("/elasticsearch/create_index_athlete.json"));

        Request request = new Request("PUT", "athlete");
        request.setJsonEntity(indexMapping);

        try {
            this.client.getLowLevelClient().performRequest(request);
        } catch (Exception e) {
            LOG.error("elastic search: perform request failed [" + request.toString() + "]");
        }
    }
}
