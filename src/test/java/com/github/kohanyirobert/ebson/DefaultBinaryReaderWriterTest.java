package com.github.kohanyirobert.ebson;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public final class DefaultBinaryReaderWriterTest extends AbstractReaderWriterTest {

  private static final int BINARY_LENGTH = 1024;
  private static final ThreadLocal<byte[]> RANDOM_BINARY = new ThreadLocal<byte[]>() {

    @Override
    protected byte[] initialValue() {
      byte[] bytes = new byte[BINARY_LENGTH];
      BsonRandom.nextBytes(bytes);
      return bytes;
    }
  };

  public DefaultBinaryReaderWriterTest() {
    super(DefaultReader.BINARY, DefaultWriter.BINARY);
  }

  @Test
  public void primitiveBytes() {
    assertArrayEquals((byte[]) writeTo(RANDOM_BINARY.get()), (byte[]) readFrom());
  }
}
