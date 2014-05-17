package com.sleazyweasel.s3db;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.sleazyweasel.s3db.storage.S3Store;
import spark.Request;
import spark.Response;
import spark.Route;

class PostWithKey implements Route {
    private final S3Store s3Store;

    @Inject
    PostWithKey(S3Store s3Store) {
        this.s3Store = s3Store;
    }

    @Override
    public Object handle(Request request, Response response) {
        String collection = request.params(":collection");
        String id = request.params(":id");
        String body = request.body();

        s3Store.putObject(collection, id, request.contentType(), body);

        response.type("application/json");
        return new Gson().toJson(CreationResponse.build(request, collection, id));
    }

}
