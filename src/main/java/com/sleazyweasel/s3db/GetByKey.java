package com.sleazyweasel.s3db;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.google.common.io.CharStreams;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.InputStreamReader;

class GetByKey implements Route {
    private final AmazonS3 amazonS3;
    private final String bucketName;

    public GetByKey(AmazonS3 amazonS3, String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    @Override
    public Object handle(Request request, Response response) {
        String collection = request.params(":collection");
        String id = request.params(":id");
        try {
            S3Object object = amazonS3.getObject(new GetObjectRequest(bucketName, collection + "/" + id));
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
