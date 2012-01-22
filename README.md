# ebson
*ebson* is an extensible [BSON][] encoder/decoder library written in Java. The
library is extensible in the sense that the mappings between Java and BSON types
are configurable and the logic to serialize custom Java types is pluggable. Its
single dependency is the [Guava libraries][] by Google.

## License
Released under the permissive [MIT License][].

## Author
[Kohányi Róbert][].

## Download
Add the library as a dependency in your project's *pom.xml* like this.

```xml
<dependency>
  <groupId>com.github.kohanyirobert</groupId>
  <artifactId>ebson</artifactId>
  <version>...</version>
</dependency>
```

Releases and snapshots are deployed to [Sonatype's][] [OSS repository][] (and
synced to the [Central Maven Repository][] from there). To download JARs from
Sonatype's repository include the following repository tag inside your Maven
installation's *settings.xml* or your project's *pom.xml*.

```xml
<repository>
  <id>sonatype-oss<id>
  <url>https://oss.sonatype.org/content/groups/public</url>
</repository>
```

## Build
As the project is managed with [Maven][] you simply clone it and issue *mvn
install* or *mvn package* inside the clone's directory.

```
git clone git://github.com/kohanyirobert/ebson.git
cd ebson/
mvn package
# and/or
mvn install
```

## Usage
### Serialization
```java
// create documents to serialize
BsonDocument document = BsonDocuments.of("key", new Date());

// grab a little-endian byte buffer
ByteBuffer buffer = ByteBuffer.allocate(32).order(ByteOrder.LITTLE_ENDIAN);

// use the documents utility class to write the document into the buffer
BsonDocuments.writeTo(buffer, document);

// use the serialized data
buffer.flip();
```

### Deserialization
```java
// given the previous buffer
BsonDocument newDocument = BsonDocuments.readFrom(buffer);

// prints true
System.out.println(document.equals(newDocument));
```

### Extensibility
```java
// to use joda-time's date-time instead of java's date supply
// a predicate (to test whether an input class is compatible with
// date-time or not) for the appropriate bson type
BsonObject.UTC_DATE_TIME.predicate(new Predicate<Class<?>>() {
  @Override public boolean apply(Class<?> input) {
    return input == null ? false : DateTime.class.isAssignableFrom(input);
  }
});

// register a writer with the same bson type which is
// able to serialize date-times into byte buffers
BsonObject.UTC_DATE_TIME.writer(new BsonWriter() {
  @Override public void writeTo(ByteBuffer buffer, Object reference) {
    buffer.putLong(((DateTime) reference).getMillis());
  }
});

// finally register a reader to do all this ass backwards
BsonObject.UTC_DATE_TIME.reader(new BsonReader() {
  @Override public Object readFrom(ByteBuffer buffer) {
    return new DateTime(buffer.getLong());
  }
});
```

[BSON]: http://bsonspec.org
[Guava libraries]: http://code.google.com/p/guava-libraries
[Kohányi Róbert]: http://kohanyirobert.github.com
[MIT License]: https://raw.github.com/kohanyirobert/ebson/master/LICENSE.txt
[Sonatype's]: http://sonatype.com
[OSS repository]: https://oss.sonatype.org
[Central Maven Repository]: http://search.maven.org
[Maven]: http://maven.apache.org
