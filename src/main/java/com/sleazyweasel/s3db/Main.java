package com.sleazyweasel.s3db;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.common.io.Resources;
import spark.Spark;

import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        AmazonS3 amazonS3 = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider());
        Properties properties = new Properties();
        properties.load(Resources.asByteSource(Resources.getResource("s3db.properties")).openStream());
        String bucketName = properties.getProperty("bucket_name");

        Spark.get("/:collection/:id", new GetByKey(amazonS3, bucketName));
        Spark.post("/:collection/:id", new PostWithKey(amazonS3, bucketName));
        Spark.post("/:collection", new PostWithoutKey(amazonS3, bucketName));
    }

}
