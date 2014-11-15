s3db
====

HTTP KV database with indexing, with S3 as the backing store.

You will need to provide an AwsCredentials.properties, and an s3db.properties, both on the classpath for this to work.

AwsCredentials.properties is the standard AWS setup with fields:
```
accessKey=<access key>
secretKey=<secret key>
```

s3db.properties needs to look like:

```
bucket_name=<s3 bucket name>
enable_encryption=true
```

API:

### POST /:collectionName

create a new item, the db assigns the id; body must be a json object.

response:
```
{
  "uri":"the uri you should go to to retrieve the item"
}
```

### POST /:collectionName/:id

create/update an item, with the given :id; body must be a json object.

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


### PUT /:collectionName/indexes/:fieldName

Ensure an index exists for the field name in the collection. Note: currently only supports top level fields in json objects.
The index is created in the background, after the response is returned. At some point in the future, it will be complete.

response:
200 if all is well (todo: should probably be 201 if the index needed to be created).


### TODO
- [ ] Indexing
  - [ ] Provide a route to query results by field value.
  - [ ] Test Performance
- [ ] Deployability
- [ ] Profit!
- 


---
s3db is profiled and optimized with YourKit!

YourKit supports open source projects with its full-featured Java Profiler.
YourKit, LLC is the creator of <a href="http://www.yourkit.com/java/profiler/index.jsp">YourKit Java Profiler</a>
and <a href="http://www.yourkit.com/.net/profiler/index.jsp">YourKit .NET Profiler</a>,
innovative and intelligent tools for profiling Java and .NET applications.

