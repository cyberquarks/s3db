package com.sleazyweasel.s3db;

import com.sleazyweasel.s3db.storage.S3Store;
import spark.Request;
import spark.Response;
import spark.Route;

class GetByKey implements Route {
    private final S3Store s3Store;

    public GetByKey(S3Store s3Store) {
        this.s3Store = s3Store;
    }

    @Override
    public Object handle(Request request, Response response) {
        String collection = request.params(":collection");
        String id = request.params(":id");
        try {
            response.type("application/json");
            return s3Store.getObject(collection, id);
        } catch (Exception e) {
            e.printStackTrace();
            response.status(500);
            return "FAILED";
        }
    }

}
