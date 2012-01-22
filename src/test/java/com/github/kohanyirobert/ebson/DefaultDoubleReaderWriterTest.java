package com.github.kohanyirobert.ebson;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class DefaultDoubleReaderWriterTest extends AbstractReaderWriterTest {

  public DefaultDoubleReaderWriterTest() {
    super(DefaultReader.DOUBLE, DefaultWriter.DOUBLE);
  }

  @Test
  public void minDouble() {
    assertEquals(writeTo(Double.valueOf(Double.MIN_VALUE)), readFrom());
  }

  @Test
  public void maxDouble() {
    assertEquals(writeTo(Double.valueOf(Double.MAX_VALUE)), readFrom());
  }
}
