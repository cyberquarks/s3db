s3db
====

HTTP KV database with indexing (eventually), with S3 as the backing store.

You will need to provide an AwsCredentials.properties, and an s3db.properties, both on the classpath for this to work.

AwsCredentials.properties is the standard AWS setup with fields:
```
accessKey=<access key>
secretKey=<secret key>
```

s3db.properties needs to look like:

```
bucket_name=<s3 bucket name>
```

To enable server side encryption, add the following property:
```
enable_encryption=true
```

API:

### POST /:collectionName

create a new item, the db assigns the id;

response:
```
{
  "uri":"the uri you should go to to retrieve the item"
}
```

### POST /:collectionName/:id

create/update an item, with the given :id

response:
```
{
  "uri":"the uri you should go to to retrieve the item"
}
```

### GET /:collectionName/:id

retrieve the item by id.

response:
whatever you put in initially


