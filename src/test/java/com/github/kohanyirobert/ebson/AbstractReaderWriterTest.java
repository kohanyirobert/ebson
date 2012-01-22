package com.github.kohanyirobert.ebson;

import java.nio.ByteBuffer;

import javax.annotation.Nullable;

public abstract class AbstractReaderWriterTest extends AbstractBsonTest {

  protected final BsonReader reader;
  protected final BsonWriter writer;

  protected AbstractReaderWriterTest(BsonReader reader, BsonWriter writer) {
    this.reader = reader;
    this.writer = writer;
  }

  protected final Object writeTo(@Nullable Object object) {
    ByteBuffer buffer = BUFFER.get();
    buffer.clear();
    writer.writeTo(buffer, object);
    buffer.flip();
    return object;
  }

  protected final Object readFrom() {
    return reader.readFrom(BUFFER.get());
  }
}
