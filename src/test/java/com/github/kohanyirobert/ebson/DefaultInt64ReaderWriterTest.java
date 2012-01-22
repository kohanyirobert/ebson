package com.github.kohanyirobert.ebson;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class DefaultInt64ReaderWriterTest extends AbstractReaderWriterTest {

  public DefaultInt64ReaderWriterTest() {
    super(DefaultReader.INT64, DefaultWriter.INT64);
  }

  @Test
  public void minLong() {
    assertEquals(writeTo(Long.valueOf(Long.MIN_VALUE)), readFrom());
  }

  @Test
  public void maxLong() {
    assertEquals(writeTo(Long.valueOf(Long.MAX_VALUE)), readFrom());
  }
}
