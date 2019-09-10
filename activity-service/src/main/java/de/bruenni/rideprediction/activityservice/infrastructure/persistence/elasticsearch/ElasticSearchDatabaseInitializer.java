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
import java.util.List;

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

    private List<Request> requests;

    @Inject
    public ElasticSearchDatabaseInitializer(RestHighLevelClient client, List<Request> requests) {
        // CDI
        this.client = notNull(client, "client cannot be null");
        this.requests = notNull(requests, " cannot be null");
    }

    /**
     * Run es rest client commands
     * @param init
     */
    public void onContainerStartup(@Observes @Initialized(value = ApplicationScoped.class) Object init) throws IOException {


        for (Request request : this.requests) {

            LOG.info("Executing elasticsearch initializing request [" + request.toString() + "]");

            try {
                this.client.getLowLevelClient().performRequest(request);
            } catch (Exception e) {
                LOG.error("elastic search: perform request failed [" + request.toString() + "]");
            }
        }
    }
}
