package de.bruenni.rideprediction.activityservice.infrastructure.persistence.elasticsearch;

import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Non-generic base repo for elastic search.
 *
 * @author Oliver Brüntje
 */
public abstract class ElasticSearchRepositoryBase {

    private static Logger LOG = LoggerFactory.getLogger(ElasticSearchRepositoryBase.class);

    private RestHighLevelClient client;

    private String indexName;

    public ElasticSearchRepositoryBase(RestHighLevelClient client, String indexName) {
        this.client = notNull(client, "client cannot be null");
        this.indexName = notEmpty(indexName, "indexName cannot be null");
    }

    /**
     * Index document.
     * @param id if of document
     * @param json json document
     */
    protected void indexDocument(String id, String json) {

        IndexRequest request = new IndexRequest(this.indexName)
                .id(id)
                .source(json, XContentType.JSON)
                .opType(DocWriteRequest.OpType.CREATE);
        try {
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            throwOnOpTypeCreateOrUpdatedFailed(response);
            LOG.debug("Document indexed [id=" + id + "]");
        } catch (IOException e) {
            throw new ElasticSearchRepositoryException("create aggregate failed" ,e);
        }
    }

    /**
     * Index document.
     * @param request index request to execute
     */
    protected void index(IndexRequest request) {
        try {
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            throwOnOpTypeCreateOrUpdatedFailed(response);
            LOG.debug("Document indexed [id=" + request.id() + "]");
        } catch (IOException e) {
            throw new ElasticSearchRepositoryException("create aggregate failed" ,e);
        }
    }

    /**
     * Gets aggregate by id.
     * @param id unique id
     * @return Aggregate if id exists; else empty optional.
     */
    public Optional<String> getById(String id) {

        GetRequest request = new GetRequest()
                .index(getIndexName())
                .id(id);

        try {
            GetResponse response = getClient().get(request, RequestOptions.DEFAULT);
            if (response.isExists()) {
                return Optional.of(response.getSourceAsString());
            } else {
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new ElasticSearchRepositoryException("get document failed [id=" + id + "]",e);
        }
    }

    /**
     * Starts term query on specified field.
     * @param fieldName name of field
     * @param value value to match
     * @return
     */
    protected Stream<String> queryTerm(String fieldName, String value) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        TermQueryBuilder termQuery = QueryBuilders.termQuery(fieldName, value);
        sourceBuilder = sourceBuilder.query(termQuery);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(this.getIndexName());
        searchRequest.source(sourceBuilder);

        try {
            SearchResponse response = getClient().search(searchRequest, RequestOptions.DEFAULT);
            throwOnSearchResponseFailed(response);

            // iterate over hits
            return StreamSupport.stream(response.getHits().spliterator(), false)
                    .map(hit -> hit.getSourceAsString());
        } catch (IOException e) {
            throw new ElasticSearchRepositoryException("term query failed [fieldName=" + fieldName + ", value=" + value + "]", e);
        }
    }

    protected RestHighLevelClient getClient() {
        return client;
    }

    protected String getIndexName() {
        return indexName;
    }

    private void throwOnOpTypeCreateOrUpdatedFailed(IndexResponse response) {
        if ((response.status().getStatus() != 201 && response.status().getStatus() != 200) ||
                (response.getResult() != DocWriteResponse.Result.CREATED && response.getResult() != DocWriteResponse.Result.UPDATED)) {
            throw new ElasticSearchDocumentCreationFailed(response.status(), response.getResult());
        }
    }

    protected void throwOnSearchResponseFailed(SearchResponse response) {
        if (response.status() != RestStatus.OK) {
            throw new ElasticSearchDocumentSearchFailed(response.status());
        }
    }
}
