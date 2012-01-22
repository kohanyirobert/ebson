package com.github.kohanyirobert.ebson;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class DefaultObjectIdReaderWriterTest extends AbstractReaderWriterTest {

  private static final int OBJECT_ID_LENGTH = 12;
  private static final ThreadLocal<ByteBuffer> RANDOM_OBJECT_ID = new ThreadLocal<ByteBuffer>() {

    @Override
    protected ByteBuffer initialValue() {
      byte[] bytes = new byte[OBJECT_ID_LENGTH];
      BsonRandom.nextBytes(bytes);
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
