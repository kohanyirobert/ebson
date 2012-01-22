package com.github.kohanyirobert.ebson;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

public final class DefaultObjectIdReaderWriterTest extends AbstractReaderWriterTest {

  private static final ThreadLocal<ByteBuffer> RANDOM_OBJECT_ID = new ThreadLocal<ByteBuffer>() {

    @Override
    protected ByteBuffer initialValue() {
      // @checkstyle:off MagicNumber
      byte[] bytes = new byte[12];
      new Random().nextBytes(bytes);
      return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
    }
  };

  public DefaultObjectIdReaderWriterTest() {
    super(DefaultReader.OBJECT_ID, DefaultWriter.OBJECT_ID);
  }

  @Test
  public void randomObjectId() {
    assertEquals(writeTo(new BasicObjectId(RANDOM_OBJECT_ID.get())), readFrom());
  }
}
