package com.github.kohanyirobert.ebson;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import java.util.Random;

public final class DefaultBinaryReaderWriterTest extends AbstractReaderWriterTest {

  private static final int BUFFER_SIZE = 1024;
  private static final Random RANDOM = new Random();
  private static final ThreadLocal<byte[]> RANDOM_BYTES = new ThreadLocal<byte[]>();

  public DefaultBinaryReaderWriterTest() {
    super(DefaultReader.BINARY, DefaultWriter.BINARY);
    byte[] bytes = new byte[BUFFER_SIZE];
    RANDOM.nextBytes(bytes);
    RANDOM_BYTES.set(bytes);
  }

  @Test
  public void primitiveBytes() {
    assertArrayEquals((byte[]) writeTo(RANDOM_BYTES.get()), (byte[]) readFrom());
  }
}
