package com.sleazyweasel.s3db;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.inject.Inject;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.ByteArrayInputStream;
import java.util.UUID;

class PostWithoutKey implements Route {
    private final AmazonS3 amazonS3;
    private final String bucketName;

    @Inject
    PostWithoutKey(AmazonS3 amazonS3, String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    @Override
    public Object handle(Request request, Response response) {
        String collection = request.params(":collection");
        String id = UUID.randomUUID().toString();
        String body = request.body();
        byte[] bytes = body.getBytes(Charsets.UTF_8);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(bytes.length);
        metadata.setContentType(request.contentType());

        amazonS3.putObject(new PutObjectRequest(bucketName, collection + "/" + id, new ByteArrayInputStream(bytes), metadata));

        response.type("application/json");
        return new Gson().toJson(CreationResponse.build(request, collection, id));
    }

}
