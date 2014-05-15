package com.sleazyweasel.s3db;

import lombok.Value;
import spark.Request;

@Value
public class CreationResponse {
    String uri;

    static String buildUri(Request request, String collection, String id) {
        return request.scheme() + "://" + request.host() + "/" + collection + "/" + id;
    }

    static CreationResponse build(Request request, String collection, String id) {
        return new CreationResponse(buildUri(request, collection, id));
    }
}
