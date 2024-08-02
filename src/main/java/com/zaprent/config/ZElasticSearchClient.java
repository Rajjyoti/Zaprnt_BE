package com.zaprent.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.DeleteOperation;
import co.elastic.clients.elasticsearch.core.bulk.IndexOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.zaprnt.beans.common.es.ZESUtil;
import com.zaprnt.beans.common.es.request.ZESRequest;
import com.zaprnt.beans.common.es.response.ZESResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Component
public class ZElasticSearchClient {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    /**
     * Executes a search query and returns a ZESResponse.
     *
     * @param request the search request
     * @param clazz the class of the documents
     * @param <T> the type of the documents
     * @return a ZESResponse containing the search results
     * @throws IOException if an I/O error occurs
     */
    public <T> ZESResponse<T> search(ZESRequest request, String index, Class<T> clazz) throws IOException {
        try {
            SearchRequest searchRequest = ZESUtil.buildSearchRequest(request, index);
            SearchResponse<T> searchResponse = elasticsearchClient.search(searchRequest, clazz);
            List<T> hits = searchResponse.hits().hits().stream()
                    .map(Hit::source)
                    .collect(Collectors.toList());

            long totalHits = nonNull(searchResponse.hits().total()) ? searchResponse.hits().total().value() : 0;

            return new ZESResponse<>(totalHits, hits, request.getPage());
        } catch (ElasticsearchException e) {
            throw new RuntimeException("Error executing search query", e);
        } catch (IOException e) {
            throw new IOException("I/O error occurred during search", e);
        }
    }

    /**
     * Index a single document.
     *
     * @param index the index name
     * @param id the document ID
     * @param document the document to be indexed
     * @param <T> the type of the document
     * @return the index response
     * @throws IOException if an I/O error occurs
     */
    public <T> IndexResponse save(String index, String id, T document) throws IOException {
        IndexRequest<T> indexRequest = new IndexRequest.Builder<T>()
                .index(index)
                .id(id)
                .document(document)
                .build();

        return elasticsearchClient.index(indexRequest);
    }

    /**
     * Index multiple documents in bulk.
     *
     * @param index the index name
     * @param documents the list of documents to be indexed
     * @param <T> the type of the documents
     * @return the bulk response
     * @throws IOException if an I/O error occurs
     */
    public <T> BulkResponse bulkSave(String index, List<T> documents) throws IOException {
        List<BulkOperation> operations = documents.stream()
                .map(document -> {
                    IndexOperation<T> indexOperation = new IndexOperation.Builder<T>()
                            .index(index)
                            .document(document)
                            .build();

                    return new BulkOperation.Builder()
                            .index(indexOperation)
                            .build();
                })
                .collect(Collectors.toList());

        BulkRequest bulkRequest = new BulkRequest.Builder()
                .operations(operations)
                .build();

        return elasticsearchClient.bulk(bulkRequest);
    }

    /**
     * Delete a single document by its ID.
     *
     * @param index the index name
     * @param id the ID of the document to delete
     * @return the delete response
     * @throws IOException if an I/O error occurs
     */
    public DeleteResponse delete(String index, String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest.Builder()
                .index(index)
                .id(id)
                .build();

        return elasticsearchClient.delete(deleteRequest);
    }

    /**
     * Delete multiple documents in bulk.
     *
     * @param index the index name
     * @param ids the list of IDs of the documents to delete
     * @return the bulk response
     * @throws IOException if an I/O error occurs
     */
    public BulkResponse deleteBulk(String index, List<String> ids) throws IOException {
        List<BulkOperation> operations = ids.stream()
                .map(id -> {
                    DeleteOperation deleteOperation = new DeleteOperation.Builder()
                            .index(index)
                            .id(id)
                            .build();

                    return new BulkOperation.Builder()
                            .delete(deleteOperation)
                            .build();
                })
                .collect(Collectors.toList());

        // Build the BulkRequest with the list of operations
        BulkRequest bulkRequest = new BulkRequest.Builder()
                .operations(operations)
                .build();

        // Perform the bulk request
        return elasticsearchClient.bulk(bulkRequest);
    }

    /**
     * Delete all documents in an index.
     *
     * @param index the index name
     * @throws IOException if an I/O error occurs
     */
    public void deleteAll(String index) throws IOException {
        // Build a delete by query request to delete all documents
        // Note: Make sure the index exists and is correct before performing this operation
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest.Builder()
                .index(index)
                .query(q -> q.matchAll(m -> m)) // Match all documents
                .build();

        elasticsearchClient.deleteByQuery(deleteByQueryRequest);
    }
}

