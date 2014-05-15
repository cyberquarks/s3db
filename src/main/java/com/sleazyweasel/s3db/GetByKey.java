package com.sleazyweasel.s3db;

import com.amazonaws.services.s3.model.S3Object;
import com.google.common.io.CharStreams;
import com.sleazyweasel.s3db.storage.S3Store;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.InputStreamReader;

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

            S3Object object = s3Store.getObject(collection, id);

            String contentType = object.getObjectMetadata().getContentType();
            response.type(contentType);
            return CharStreams.toString(new InputStreamReader(object.getObjectContent()));
        } catch (Throwable e) {
            e.printStackTrace();
            response.status(500);
            return "FAIL";
        }
    }

}
