package de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch;

import org.apache.http.HttpHost;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.yasson.internal.JsonBindingBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Scope;
import javax.inject.Singleton;
import javax.json.bind.Jsonb;
import java.net.UnknownHostException;

/**
 * Provides elastic search db stuff.
 *
 * @author Oliver Brüntje
 */
@ApplicationScoped
public class ElasticSearchProvider {

    @ConfigProperty(name = "persistence.elasticsearch.port")
    private int port;

    @ConfigProperty(name = "persistence.elasticsearch.host")
    private String host;

    @Singleton  // not a NormalScope bean so no no-args constructor for weld proxy is available
    @Produces
    public RestHighLevelClient elasticSearchClient() throws UnknownHostException {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(this.host, this.port, "http")));
    }

    @Produces
    @Persistence
    public Jsonb jsonb() {
        return new JsonBindingBuilder().build();
    }
}
