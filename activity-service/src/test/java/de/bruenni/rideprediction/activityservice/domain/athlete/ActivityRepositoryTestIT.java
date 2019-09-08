package de.bruenni.rideprediction.activityservice.domain.athlete;

import de.bruenni.rideprediction.activityservice.domain.activity.ActivityRepository;
import de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch.ElasticSearchDatabaseInitializer;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.json.JSONException;
import org.junit.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ActivityRepositoryTestIT {

    private static ElasticsearchContainer container;

    private static RestHighLevelClient client;

    private ActivityRepository repository;

    @BeforeClass
    public static void setUp() throws IOException {
        container = new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch-oss:7.3.1");
        container.start();

        client = buildClient(container);

        // init db
        new ElasticSearchDatabaseInitializer(client).onContainerStartup(null);
    }

    @Before
    public void before() throws Exception {
        repository = new ActivityRepository(client);
    }

    private static RestHighLevelClient buildClient(ElasticsearchContainer container) {
        String hostAddress = container.getContainerIpAddress();
        Integer mappedPort = container.getMappedPort(9200);
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostAddress, mappedPort, "http")));
    }

    @AfterClass
    public static void destroy() {
        container.stop();
        container.close();
    }

    @Test
    public void create() throws IOException, JSONException {

        // given
        String id = "4711";
        String activityDocument = IOUtils.toString(getClass().getResourceAsStream("/persistence/es/activity/activity_4711.json"));

        // when
        repository.create(id, activityDocument);
        Optional<String> readActivity = repository.getById(id);

        // then
        Assert.assertTrue(readActivity.isPresent());
        JSONAssert.assertEquals(readActivity.get(), activityDocument, JSONCompareMode.NON_EXTENSIBLE);
    }
}
