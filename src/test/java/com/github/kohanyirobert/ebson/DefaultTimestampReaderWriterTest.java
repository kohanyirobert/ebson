package com.github.kohanyirobert.ebson;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class DefaultTimestampReaderWriterTest extends AbstractReaderWriterTest {

  private static final int TIMESTAMP_LENGTH = 8;
  private static final ThreadLocal<ByteBuffer> RANDOM_TIMESTAMP = new ThreadLocal<ByteBuffer>() {

    @Override
    protected ByteBuffer initialValue() {
      byte[] bytes = new byte[TIMESTAMP_LENGTH];
      BsonRandom.nextBytes(bytes);
      return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
    }
  };

  public DefaultTimestampReaderWriterTest() {
    super(DefaultReader.TIMESTAMP, DefaultWriter.TIMESTAMP);
  }

  @Test
  public void randomTimestamp() {
    assertEquals(writeTo(new BasicTimestamp(RANDOM_TIMESTAMP.get())), readFrom());
  }
}
