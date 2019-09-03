package de.bruenni.rideprediction.activityservice.domain.athlete;

import org.apache.http.HttpHost;
import org.eclipse.yasson.internal.JsonBindingBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import static org.assertj.core.api.Assertions.assertThat;  // main one
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import javax.json.bind.Jsonb;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

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

        Athlete athlete = new Athlete("1", "nickname a", Collections.emptyList());

        // when
        repository.create(athlete);
        Optional<Athlete> readAthlete = repository.get(athlete.getId());

        // then
        Assert.assertTrue(readAthlete.isPresent());
        Assert.assertEquals(athlete.getNickName(), readAthlete.get().getNickName());
    }

    @Test
    public void getNotExistingExpectEmpty() {

        // when
        Optional<Athlete> readAthlete = repository.get("4662");

        // then
        Assert.assertFalse(readAthlete.isPresent());
    }

    @Test
    public void updateActivity() {

        Athlete athlete = new Athlete(UUID.randomUUID().toString(), "nickname a", Collections.emptyList());

        repository.create(athlete);

        Athlete updatedAthlete = repository.addActivity(athlete.getId(), Collections.singletonList("first"));

        // then
        assertThat(updatedAthlete.getActivities())
                .hasSize(1)
                .contains("first");
    }

    @Test
    public void updateActivtyTwiceExpectActivitiesMerged() {
        Athlete athlete = new Athlete(UUID.randomUUID().toString(), "nickname a", Collections.emptyList());

        repository.create(athlete);

        Athlete updatedAthlete = repository.addActivity(athlete.getId(), Collections.singletonList("first"));
        Athlete updated2Athlete = repository.addActivity(athlete.getId(), Collections.singletonList("second"));

        // then
        assertThat(updated2Athlete.getActivities())
                .hasSize(1)
                .contains("first")
                .contains("second");
    }
}
