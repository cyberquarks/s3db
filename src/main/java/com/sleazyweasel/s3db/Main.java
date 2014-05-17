package com.sleazyweasel.s3db;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.sleazyweasel.s3db.storage.S3Store;
import spark.Spark;

import java.io.IOException;
import java.util.Properties;

public class Main {
    public static final String json = "application/json";

    public static void main(String[] args) throws IOException {
        S3Store s3Store = provideS3Store();

        Spark.get("/:collection/:id", new GetByKey(s3Store));
        Spark.post("/:collection/:id", json, new PostWithKey(s3Store));
        Spark.post("/:collection", json, new PostWithoutKey(s3Store));
        Spark.put("/:collection/indexes/:field", json, (request, response) -> {
            s3Store.ensureIndex(request.params(":collection"), request.params("field"));
            response.type(json);
            return "OK";
        });
    }

    private static S3Store provideS3Store() throws IOException {
        AmazonS3 amazonS3 = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider());
        Properties properties = new Properties();
        properties.load(Resources.asByteSource(Resources.getResource("s3db.properties")).openStream());
        String bucketName = properties.getProperty("bucket_name");
        boolean enableEncryption = Boolean.getBoolean(properties.getProperty("enable_encryption"));

        return new S3Store(amazonS3, bucketName, enableEncryption, new Gson());
    }

}
