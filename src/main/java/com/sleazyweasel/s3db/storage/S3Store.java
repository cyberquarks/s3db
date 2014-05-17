package com.sleazyweasel.s3db.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.google.common.base.Charsets;

import java.io.ByteArrayInputStream;

public class S3Store {
    private final AmazonS3 amazonS3;
    private final String bucketName;
    private final boolean enableEncryption;

    public S3Store(AmazonS3 amazonS3, String bucketName, boolean enableEncryption) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
        this.enableEncryption = enableEncryption;
    }

    public S3Object getObject(String collection, String id) {
        return amazonS3.getObject(new GetObjectRequest(bucketName, collection + "/" + id));
    }

    public void putObject(String collection, String id, String contentType, String content) {
        byte[] bytes = content.getBytes(Charsets.UTF_8);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(bytes.length);
        metadata.setContentType(contentType);
        if (enableEncryption) {
            metadata.setServerSideEncryption(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
        }
        amazonS3.putObject(new PutObjectRequest(bucketName, collection + "/" + id, new ByteArrayInputStream(bytes), metadata));
        //todo: consider fetching & validating the content is the same, to make sure the eventual consistency has resolved
    }

    public void ensureIndex(String collection, String field) {

    }
}
