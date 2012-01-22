package com.github.kohanyirobert.ebson;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class DefaultInt32ReaderWriterTest extends AbstractReaderWriterTest {

  public DefaultInt32ReaderWriterTest() {
    super(DefaultReader.INT32, DefaultWriter.INT32);
  }

  @Test
  public void minInt() {
    assertEquals(writeTo(Integer.valueOf(Integer.MIN_VALUE)), readFrom());
  }

  @Test
  public void maxInt() {
    assertEquals(writeTo(Integer.valueOf(Integer.MAX_VALUE)), readFrom());
  }
}
