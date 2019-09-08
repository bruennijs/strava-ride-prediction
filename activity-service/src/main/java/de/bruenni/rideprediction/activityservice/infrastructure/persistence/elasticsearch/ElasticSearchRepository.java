package de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch;

import de.bruenni.rideprediction.activityservice.infrastructure.domain.Identifiable;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;

import javax.json.bind.Jsonb;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * BAse CRUD repo for elastic search.
 *
 * @author Oliver Brüntje
 */
public abstract class ElasticSearchRepository<TAggregate extends Identifiable<String>> extends ElasticSearchRepositoryBase {

    private Class<TAggregate> className;

    protected Jsonb json;

    /**
     * CTor
     * @param json
     * @param client
     * @param indexName
     * @param className class of aggregate to serialize/deserializer from/to json.
     */
    public ElasticSearchRepository(Jsonb json, RestHighLevelClient client, String indexName, Class<TAggregate> className) {
        super(client, indexName);
        this.json = notNull(json, "json cannot be null");
        this.className = notNull(className, "className cannot be null");
    }

    /**
     * Creates new aggregate.
     * @param aggregate instance to serialize
     */
    public void create(TAggregate aggregate) {

        String json = this.json.toJson(aggregate);

        indexDocument(aggregate.getId(), json);
    }

    /**
     * Gets aggregate by id.
     * @param id unique id
     * @return Aggregate if id exists; else empty optional.
     */
    public Optional<TAggregate> get(String id) {
        return this.getById(id)
                .map(document -> json.fromJson(document, this.className));
    }

    /**
     * Starts term query on specified field.
     * @param fieldName name of field
     * @param value value to match
     * @return
     */
    public List<TAggregate> queryTermAggregate(String fieldName, String value) {
        return super.queryTerm(fieldName, value)
                .map(str -> this.json.fromJson(str, this.className))
                .collect(Collectors.toList());
    }

    protected void throwOnUpdateFailed(UpdateResponse response) {
        if (response.getResult() != DocWriteResponse.Result.UPDATED) {
            throw new ElasticSearchDocumentUpdateFailed(response.status(), response.getResult());
        }
    }

}
