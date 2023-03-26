package com.dmba.controller;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * DocumentController is a Spring REST controller that provides an API for managing Elasticsearch documents.
 */
@RestController
public class DocumentController {

    @Autowired
    private RestClient restClient;

    /**
     * Creates a new document in the specified Elasticsearch index.
     *
     * @param indexName The name of the index to create the document in.
     * @param document  The JSON document to be indexed.
     * @return A ResponseEntity with the result of the document creation. If successful, the response will
     * contain a message with the document ID. If unsuccessful, the response will contain an error message
     * and the appropriate HTTP status code.
     */
    @PostMapping("/document")
    public ResponseEntity<String> createDocument(@RequestParam String indexName, @RequestBody String document) {
        Request request = new Request("POST", "/" + indexName + "/_doc");
        request.setJsonEntity(document);
        try {
            Response response = restClient.performRequest(request);
            String responseBody = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseBody);
            String documentId = jsonObject.getString("_id");
            return ResponseEntity.ok("Document created with ID: " + documentId);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create document");
        }
    }

    /**
     * Retrieves a document from the specified Elasticsearch index by its ID.
     *
     * @param indexName The name of the index to retrieve the document from.
     * @param id        The ID of the document to be retrieved.
     * @return A ResponseEntity with the result of the document retrieval. If successful, the response will
     * contain the JSON document. If unsuccessful, the response will contain an error message and the
     * appropriate HTTP status code.
     */
    @GetMapping("/{indexName}/document/{id}")
    public ResponseEntity<String> getDocumentById(@PathVariable String indexName, @PathVariable String id) {
        Request request = new Request("GET", "/" + indexName + "/_doc/" + id);
        try {
            Response response = restClient.performRequest(request);
            String document = EntityUtils.toString(response.getEntity());
            return ResponseEntity.ok(document);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve document");
        }
    }
}