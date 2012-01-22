package com.github.kohanyirobert.ebson;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

public final class DefaultTimestampReaderWriterTest extends AbstractReaderWriterTest {

  private static final ThreadLocal<ByteBuffer> RANDOM_TIMESTAMP = new ThreadLocal<ByteBuffer>() {

    @Override
    protected ByteBuffer initialValue() {
      // @checkstyle:off MagicNumber
      byte[] bytes = new byte[8];
      new Random().nextBytes(bytes);
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
