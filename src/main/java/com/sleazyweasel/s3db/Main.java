package com.sleazyweasel.s3db;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.common.io.Resources;
import com.sleazyweasel.s3db.storage.S3Store;
import spark.Spark;

import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {
        AmazonS3 amazonS3 = new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider());
        Properties properties = new Properties();
        properties.load(Resources.asByteSource(Resources.getResource("s3db.properties")).openStream());
        String bucketName = properties.getProperty("bucket_name");
        boolean enableEncryption = Boolean.getBoolean(properties.getProperty("enable_encryption"));
        S3Store s3Store = new S3Store(amazonS3, bucketName, enableEncryption);

        Spark.get("/:collection/:id", new GetByKey(s3Store));
        Spark.post("/:collection/:id", "application/json", new PostWithKey(s3Store));
        Spark.post("/:collection", "application/json", new PostWithoutKey(s3Store));
    }

}
