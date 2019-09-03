package de.bruenni.rideprediction.activityservice.domain.athlete;

import org.apache.http.HttpHost;
import org.eclipse.yasson.internal.JsonBindingBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import javax.json.bind.Jsonb;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

public class AthleteRepositoryTestIT {

    private static ElasticsearchContainer container;

    private static RestHighLevelClient client;

    private static Jsonb json;

    private AthleteRepository repository;

    @BeforeClass
    public static void setUp() {
        container = new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch-oss:7.3.1");
        container.start();

        client = buildClient(container);

        json = new JsonBindingBuilder().build();
    }

    @Before
    public void before() throws Exception {
        repository = new AthleteRepository(json, client);
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
    public void create() throws IOException {

        Athlete athlete = new Athlete("1", "nickname a");

        // when
        repository.create(athlete);
        Optional<Athlete> readAthlete = repository.get(athlete.getId());

        // then
        Assert.assertTrue(readAthlete.isPresent());
        Assert.assertEquals(athlete.getNickName(), readAthlete.get().getNickName());
    }

    @Test
    public void get() {
    }
}
