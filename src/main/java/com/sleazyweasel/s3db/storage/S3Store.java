package com.sleazyweasel.s3db.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.io.ByteArrayInputStream;

public class S3Store {
    private final AmazonS3 amazonS3;
    private final String bucketName;

    public S3Store(AmazonS3 amazonS3, String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    public S3Object getObject(String collection, String id) {
        return amazonS3.getObject(new GetObjectRequest(bucketName, collection + "/" + id));
    }

    public void putObject(String collection, String id, byte[] bytes, String contentType, boolean enableServerSideEncryption) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(bytes.length);
        metadata.setContentType(contentType);
        if (enableServerSideEncryption) {
            metadata.setServerSideEncryption(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
        }
        amazonS3.putObject(new PutObjectRequest(bucketName, collection + "/" + id, new ByteArrayInputStream(bytes), metadata));
    }
}
