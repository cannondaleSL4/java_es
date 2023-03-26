package com.dmba.controller;

import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * IndexController is a Spring REST controller that provides an API for managing Elasticsearch indices.
 */
@RestController
public class IndexController {

    @Autowired
    private RestClient restClient;

    /**
     * Creates a new Elasticsearch index with the specified name.
     *
     * @param indexName The name of the index to create.
     * @return A ResponseEntity with the result of the index creation. If successful, the response will
     * contain a message with the index name. If unsuccessful, the response will contain an error message
     * and the appropriate HTTP status code.
     */
    @PostMapping("/index")
    public ResponseEntity<String> createIndex(@RequestParam String indexName) {
        Request request = new Request("PUT", "/" + indexName);
        try {
            Response response = restClient.performRequest(request);
            return ResponseEntity.ok("Index created with name " + indexName);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create index");
        }
    }
}