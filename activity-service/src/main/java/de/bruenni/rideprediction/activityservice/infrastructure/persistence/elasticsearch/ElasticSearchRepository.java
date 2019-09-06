package de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch;

import de.bruenni.rideprediction.activityservice.infrastructure.domain.Identifiable;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import javax.json.bind.Jsonb;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * BAse CRUD repo for elastic search.
 *
 * @author Oliver Brüntje
 */
public abstract class ElasticSearchRepository<TId, TAggregate extends Identifiable<String>> {
    protected RestHighLevelClient client;

    private String indexName;

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
        this.json = notNull(json, "json cannot be null");
        this.client = notNull(client, "client cannot be null");
        this.indexName = notEmpty(indexName, "indexName cannot be null");
        this.className = notNull(className, "className cannot be null");
    }

    /**
     * Creates new aggregate.
     * @param aggregate instance to serialize
     */
    public void create(TAggregate aggregate) {

        String json = this.json.toJson(aggregate);

        IndexRequest request = new IndexRequest(this.indexName)
                .id(aggregate.getId())
                .source(json, XContentType.JSON)
                .opType(DocWriteRequest.OpType.CREATE);

        try {
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            throwOnOpTypeCreateFailed(response);
        } catch (IOException e) {
            throw new ElasticSearchRepositoryException("create aggregate failed" ,e);
        }
    }

    /**
     * Gets aggregate by id.
     * @param id unique id
     * @return Aggregate if id exists; else empty optional.
     */
    public Optional<TAggregate> get(String id) {

        GetRequest request = new GetRequest()
                .index(indexName)
                .id(id);

        try {
            GetResponse response = client.get(request, RequestOptions.DEFAULT);
            if (response.isExists()) {
                TAggregate object = json.fromJson(response.getSourceAsString(), this.className);
                return Optional.of(object);
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new ElasticSearchRepositoryException("get aggregate failed [id=" + id + "]",e);
        }
    }

    /**
     * Starts term query on specified field.
     * @param fieldName name of field
     * @param value value to match
     * @return
     */
    public List<TAggregate> queryTerm(String fieldName, String value) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQuery = QueryBuilders.termQuery(fieldName, value);
        sourceBuilder = sourceBuilder.query(termQuery);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(this.indexName);
        searchRequest.source(sourceBuilder);

        try {
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            throwOnSearchResponseFailed(response);

            // oterate over hits
            return StreamSupport.stream(response.getHits().spliterator(), false)
                    .map(hit -> hit.getSourceAsString())
                    .map(str -> this.json.fromJson(str, this.className))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ElasticSearchRepositoryException("term query failed [fieldName=" + fieldName + ", value=" + value + "]", e);
        }
    }

    protected void throwOnSearchResponseFailed(SearchResponse response) {
        if (response.status() != RestStatus.OK) {
            throw new ElasticSearchDocumentSearchFailed(response.status());
        }
    }

    protected void throwOnUpdateFailed(UpdateResponse response) {
        if (response.getResult() != DocWriteResponse.Result.UPDATED) {
            throw new ElasticSearchDocumentUpdateFailed(response.status(), response.getResult());
        }
    }

    private void throwOnOpTypeCreateFailed(IndexResponse response) {
        if ((response.status().getStatus() != 201 && response.status().getStatus() != 200) ||
                (response.getResult() != DocWriteResponse.Result.CREATED)) {
            throw new ElasticSearchDocumentCreationFailed(response.status(), response.getResult());
        }
    }
}
