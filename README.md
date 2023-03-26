# Elasticsearch REST API Services.

This project contains 3 REST API services that allow you to interact with an Elasticsearch server running on a remote machine.

## API Services

### Prerequisites

```
docker run -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e "xpack.security.enabled=false" docker.elastic.co/elasticsearch/elasticsearch:8.0.0
```

1. Create an index
   Create an index in Elasticsearch server.

Request Body:

```
curl -X POST "http://localhost:8080/index?indexName=my_new_index" -H "Content-Type: application/json"
```

Logs:
```
2023-03-26 20:35:14.356 DEBUG 188179 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : POST "/index?indexName=my_new_index", parameters={masked}
2023-03-26 20:35:14.415 DEBUG 188179 --- [nio-8080-exec-1] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.dmba.controller.IndexController#createIndex(String)
2023-03-26 20:35:32.955 DEBUG 188179 --- [nio-8080-exec-1] o.s.w.s.m.m.a.HttpEntityMethodProcessor  : Using 'text/plain', given [*/*] and supported [text/plain, */*, text/plain, */*, application/json, application/*+json, application/json, application/*+json, application/x-jackson-smile, application/cbor]
2023-03-26 20:35:32.957 DEBUG 188179 --- [nio-8080-exec-1] o.s.w.s.m.m.a.HttpEntityMethodProcessor  : Writing ["Index created with name my_new_index"]
2023-03-26 20:35:33.007 DEBUG 188179 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed 200 OK
```

2. Create a document in the index:

```
 curl -X POST "http://localhost:8080/document?indexName=my_new_index" -H "Content-Type: application/json" -d '{"field1": "value1", "field2": "value2"}'

```

Logs:
```
2023-03-26 20:37:58.373 DEBUG 188179 --- [nio-8080-exec-2] o.s.web.servlet.DispatcherServlet        : POST "/document?indexName=my_new_index", parameters={masked}
2023-03-26 20:37:58.374 DEBUG 188179 --- [nio-8080-exec-2] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.dmba.controller.DocumentController#createDocument(String, String)
2023-03-26 20:37:58.385 DEBUG 188179 --- [nio-8080-exec-2] m.m.a.RequestResponseBodyMethodProcessor : Read "application/json;charset=UTF-8" to ["{"field1": "value1", "field2": "value2"}"]
2023-03-26 20:37:58.531 DEBUG 188179 --- [nio-8080-exec-2] o.s.w.s.m.m.a.HttpEntityMethodProcessor  : Using 'text/plain', given [*/*] and supported [text/plain, */*, text/plain, */*, application/json, application/*+json, application/json, application/*+json, application/x-jackson-smile, application/cbor]
2023-03-26 20:37:58.532 DEBUG 188179 --- [nio-8080-exec-2] o.s.w.s.m.m.a.HttpEntityMethodProcessor  : Writing ["Document created with ID: Oan_HocBJRZN0YXMkjGJ"]
2023-03-26 20:37:58.533 DEBUG 188179 --- [nio-8080-exec-2] o.s.web.servlet.DispatcherServlet        : Completed 200 OK
```

3. Get a document by id:
```
curl -X GET "http://localhost:8080/my_new_index/document/Oan_HocBJRZN0YXMkjGJ" -H "Content-Type: application/json"
```

Logs:
```
2023-03-26 20:38:51.166 DEBUG 188179 --- [nio-8080-exec-3] o.s.web.servlet.DispatcherServlet        : GET "/my_new_index/document/Oan_HocBJRZN0YXMkjGJ", parameters={}
2023-03-26 20:38:51.167 DEBUG 188179 --- [nio-8080-exec-3] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped to com.dmba.controller.DocumentController#getDocumentById(String, String)
2023-03-26 20:38:53.993 DEBUG 188179 --- [nio-8080-exec-3] o.s.w.s.m.m.a.HttpEntityMethodProcessor  : Using 'text/plain', given [*/*] and supported [text/plain, */*, text/plain, */*, application/json, application/*+json, application/json, application/*+json, application/x-jackson-smile, application/cbor]
2023-03-26 20:38:53.993 DEBUG 188179 --- [nio-8080-exec-3] o.s.w.s.m.m.a.HttpEntityMethodProcessor  : Writing ["{"_index":"my_new_index","_id":"Oan_HocBJRZN0YXMkjGJ","_version":1,"_seq_no":0,"_primary_term":1,"fo (truncated)..."]
2023-03-26 20:38:53.994 DEBUG 188179 --- [nio-8080-exec-3] o.s.web.servlet.DispatcherServlet        : Completed 200 OK
```

**Note**: pay attention `Oan_HocBJRZN0YXMkjGJ` this value you got from the second request.